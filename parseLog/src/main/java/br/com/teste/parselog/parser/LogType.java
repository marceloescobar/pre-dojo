package br.com.teste.parselog.parser;

public enum LogType {

	MATCH_START (".*New match .* has started"),
	MATCH_END (".*ended.*"),
	PLAYER_KILL(".*killed.*using.*"),
	WORLD_KILL(".*<WORLD>.*killed.*by.*");

	private String regex;

	LogType (String regex){
		this.regex=regex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
