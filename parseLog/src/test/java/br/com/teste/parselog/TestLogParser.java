package br.com.teste.parselog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.teste.parselog.domain.Match;
import br.com.teste.parselog.parser.LogParser;

@RunWith(JUnit4.class)
public class TestLogParser {

	@Test
	public void testCorretParser() throws IOException {

		File f = File.createTempFile("log", null);
		
		 BufferedWriter output = new BufferedWriter(new FileWriter(f));
         output.write("23/04/2013 15:34:22 - New match 11348965 has started\n");
         output.write("23/04/2013 15:36:04 - Roman killed Nick using M16\n");
         output.write("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN\n");
         output.write("23/04/2013 15:39:22 - Match 11348965 has ended\n");
         
         output.close();
         
		
	
		LogParser parser = new LogParser();
		parser.processLog(f.getAbsolutePath());
		
		List<Match> matches = parser.getMatches();
		
		Assert.assertEquals(1, matches.size());
		
		Assert.assertEquals(3, matches.get(0).getPlayers().size());
		
		Assert.assertEquals("Roman", matches.get(0).getWinner().getName());
	}
}
