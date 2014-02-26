package br.com.teste.parselog;

import java.util.List;

import br.com.teste.parselog.domain.Match;
import br.com.teste.parselog.parser.LogParser;


public class App {
	public static void main(String[] args) {

		LogParser logParser = new LogParser();
		logParser.processLog(args[0]);

		List<Match> matches = logParser.getMatches();

		for (Match match : matches) {

			// Ranking
			match.printRanking();

			// max streak by player
			match.printMaxKillStreaksByPlayer();

			// Awards
			match.printPlayerAwards();

			match.getWinner().printPreferredWeapons();
		}

	}
}
