package br.com.teste.parselog.domain;

public class Award {

	private String name;
	
	private Long matchId;
	
	
	public Award(String name, Long matchId){
		this.name = name;
		this.matchId = matchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	
	
}
