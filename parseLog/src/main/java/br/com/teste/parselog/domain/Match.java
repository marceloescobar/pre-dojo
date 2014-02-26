package br.com.teste.parselog.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Match {

	private long id;

	private boolean finished = false;

	private Set<Player> players = new HashSet<Player>();

	private Player winner;

	public Match(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {

		if (!this.finished) {
			players.add(player);
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Player getPlayer(Player entry) {

		for (Player p : players) {
			if (p.equals(entry))
				return p;
		}
		return null;
	}

	public void printRanking() {
		List<Player> rankedPlayerList = generateRanking();

		Integer position = 1;
		for (Player player : rankedPlayerList) {
			if (player != null) {
				System.out.println(position + ". -" + player.getName()
						+ " kills :" + player.getMatchKillCount() + " deaths :"
						+ player.getMatchDeathCount());
				position++;
			}
		}
	}

	private List<Player> generateRanking() {

		List<Player> rankedPlayerList = new ArrayList<Player>();
		rankedPlayerList.addAll(players);

		Collections.sort(rankedPlayerList, new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				if (o1 == null && o2 == null) {
					return 0;
				} else if (o1 == null) {
					return -1;
				} else if (o2 == null) {
					return 1;
				} else {
					if (o1.getMatchKillCount() < o2.getMatchKillCount()) {
						return 1;
					}
					if (o1.getMatchKillCount() > o2.getMatchKillCount()) {
						return -1;
					}
				}
				return 0;
			}
		});

		// remove player WORLD
		Player world = new Player("<WORLD>");
		rankedPlayerList.remove(world);

		return rankedPlayerList;

	}

	public void endMatch() {
		this.finished = true;

		Player winner = this.generateRanking().get(0);

		if (winner != null)
			setWinner(winner);

		for (Player player : players) {

			if (player.getMatchDeathCount() == 0) {
				Award award = new Award("Trofeu Survivor", this.id);
				player.addAward(award);
			}
		}
	}

	public void printPlayerAwards() {
		List<Player> rankedPlayerList = generateRanking();

		System.out.println("Awards Per Player");

		Integer position = 1;

		for (Player player : rankedPlayerList) {

			if (player != null) {
				System.out.println(position + ". " + player.getName());

				if (player.getAwards() != null && !player.getAwards().isEmpty()) {
					for (Award award : player.getAwards()) {
						if (award.getMatchId() == this.id)
							System.out.println(award.getName());
					}
				}

				position++;
			}
		}
	}

	public void printMaxKillStreaksByPlayer() {
		List<Player> rankedPlayerList = generateRanking();

		System.out.println("MAX KillStreaks Per Player");

		Integer position = 1;

		for (Player player : rankedPlayerList) {
			if (player != null) {
				System.out.println(position + ". " + player.getName()
						+ " killStreak :" + player.getMatchHighestKillStreak());
				position++;
			}
		}
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
