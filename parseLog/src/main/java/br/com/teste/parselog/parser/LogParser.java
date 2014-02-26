package br.com.teste.parselog.parser;

import java.util.ArrayList;
import java.util.List;

import br.com.teste.parselog.domain.Match;
import br.com.teste.parselog.domain.Player;
import br.com.teste.parselog.util.LogFile;

public class LogParser {

	private List<String> logLines = new ArrayList<String>();

	private List<Match> matches = new ArrayList<Match>();

	private Match currentMatch = null;

	public void processLog(String location) {
		this.logLines = LogFile.readLogLines(location);

		for (String line : logLines) {
			this.parse(line);
		}
	}

	private LogType getLogType(String line) {
		LogType type = null;

		for (LogType t : LogType.values()) {
			if (line.matches(t.getRegex())) {
				type = t;
				break;
			}
		}
		return type;
	}

	private void parse(String line) {
		LogType type = this.getLogType(line);

		if (type == null)
			throw new RuntimeException(
					"Erro ao processar o arquivo. Padrao nao reconhecido. [ "
							+ line + " ]");

		if (type.equals(LogType.MATCH_START)) {
			this.parseMatchStart(line);

		} else if (type.equals(LogType.PLAYER_KILL)) {
			this.parsePlayerKill(line);

		} else if (type.equals(LogType.MATCH_END)) {
			this.parseMatchEnd(line);
			
		}else if(type.equals(LogType.WORLD_KILL)){
			this.parseWorldKill(line);
		}
	}

	private void parseMatchStart(String line) {
		if (currentMatch != null)
			throw new RuntimeException(
					"UNEXPECTED Start of Match inside another match !");

		try {
			Long id = new Long(line.split("New match ")[1].split(" ")[0].trim());

			Match match = new Match(id);
			this.currentMatch = match;

		} catch (Exception e) {
			throw new RuntimeException(
					"Erro ao processar log, start match foi encontrado, porem nao foi possivel processa-lo. Verifique o padrao do log.");
		}
	}

	private void parsePlayerKill(String line) {
		try {

			String player1 = line.split("killed")[0].split(" \\- ")[1].trim();
			String player2 = line.split("killed")[1].split("using")[0].trim();
			String weapon = line.split("killed")[1].split("using")[1].trim();

			Player p1 = new Player(player1);
			Player p2 = new Player(player2);

			this.playerKill(p1, p2, weapon);
			

		} catch (Exception e) {
			throw new RuntimeException(
					"This log file is corrupted, found an Player Kill that had a UNEXPECTED pattern , could not process the file.");
		}
	}

	private void parseMatchEnd(String line) {
		try {
			Long matchId = new Long(
					line.split("Match ")[1].split(" ")[0].trim());

			if (currentMatch.getId() == matchId.longValue()) {
				currentMatch.endMatch();
				matches.add(currentMatch);

				currentMatch = null;

			} else {
				throw new RuntimeException("");
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"This log file is corrupted, found an end match that had a UNEXPECTED pattern , could not process the file.");
		}
	}
	
	private void parseWorldKill(String line){
		String player1Name = "<WORLD>";
		String player2Name = line.split("<WORLD> killed")[1].split("by")[0].trim();
		String weapon = line.split("<WORLD> killed")[1].split("by")[1].trim();
		
		Player p1 = new  Player(player1Name);
		Player p2 = new Player(player2Name);
		
		this.playerKill(p1, p2, weapon);
	}
	
	
	private void playerKill(Player p1, Player p2, String weapon){
		if (currentMatch.getPlayers().contains(p1)) {
			p1 = currentMatch.getPlayer(p1);
		} else {
			currentMatch.addPlayer(p1);
		}

		if (currentMatch.getPlayers().contains(p2)) {
			p2 = currentMatch.getPlayer(p2);
		} else {
			currentMatch.addPlayer(p2);
		}

		p1.addKill(weapon);
		p2.addDeath();
	}

	public List<Match> getMatches() {
		return matches;
	}
	
	

}
