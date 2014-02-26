package br.com.teste.parselog.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogFile {

	public static List<String> readLogLines(String location) {
		List<String> lines = new ArrayList<String>();

		File file = new File(location);

		if (!file.exists()) {
			return lines;
		}

		if (!file.canRead()) {
			return lines;
		}

		FileReader reader;

		try {
			reader = new FileReader(file);

			BufferedReader bReader = new BufferedReader(reader);

			String line = bReader.readLine();
			while (line != null) {
				lines.add(line);
				line = bReader.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
}
