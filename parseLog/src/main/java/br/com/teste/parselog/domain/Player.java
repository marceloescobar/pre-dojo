package br.com.teste.parselog.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Player {

	private String name;
	
	private Map<String, Long> killsByWeapon = new HashMap<String, Long>();	
	
	
	private long matchDeathCount =0;
	
	private long matchKillCount = 0;
	
	private long matchKillStreak = 0;
	
	private long matchHighestKillStreak= 0 ;
	
	
	private List<Award> awards;
	
	public Player(String name ) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addDeath(){
		this.matchDeathCount++;
		this.matchKillStreak=0;
	}
	
	 public void addKill(String weapon){
	    	//Date currentTime = new Date();
			Long killsWithWeapon = killsByWeapon.get(weapon);
	    	
	    	this.matchKillCount++;
			this.matchKillStreak++;


			/*if(killsWithWeapon == 1){
				killStreakStartTime = new Date();
			}

			if(currentMatchKillStreak%5 == 0l ){
				if((currentTime.getTime()-killStreakStartTime.getTime()) < 300000 ){
				  Award award= new Award("An Quick Hands Job", "5 kills in less than 5 minutes",this.currentMatchId);
				  this.awards.add(award);
				  killStreakStartTime = new Date();
				}
				else{
				  killStreakStartTime = new Date();
				}
			}*/

			if(killsWithWeapon == null){
				killsByWeapon.put(weapon, 1l);
			}else{
				killsByWeapon.put(weapon, ++killsWithWeapon);
			}

			/*if(currentMatchHighestKillStreak <currentMatchKillStreak){
				currentMatchHighestKillStreak=currentMatchKillStreak;
			}*/
			
			
			if(matchHighestKillStreak <matchKillStreak){
				matchHighestKillStreak=matchKillStreak;
			}
	 		
		}
	    
	 
	 
	
	
	public long getMatchHighestKillStreak() {
		return matchHighestKillStreak;
	}

	public Long getMatchDeathCount() {
		return matchDeathCount;
	}

	public Long getMatchKillCount() {
		return matchKillCount;
	}

	private Map<String, Long>  getPreferredWeapons(){
		Map<String, Long>  sortedMap =  SortMapByComparator.sortByComparator(killsByWeapon, false);
		return sortedMap;
	}
	
	public void printPreferredWeapons(){
		Map<String, Long>  sortedMap  = getPreferredWeapons();
		System.out.println("Preferred Weapons by "+this.getName());
		
		Set<String> keys = sortedMap.keySet();
		Integer position =1;
		for(String key :keys){
			Long kills = sortedMap.get(key);
			System.out.println(position+". "+key +": "+kills);
			position++;
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public List<Award> getAwards() {
		return awards;
	}

	public void setAwards(List<Award> awards) {
		this.awards = awards;
	}
	
	public void addAward(Award award){
		if (this.awards == null)
			this.awards = new ArrayList<Award>();
		
		this.awards.add(award);
	}
}

class SortMapByComparator {

	  public static Map<String, Long> sortByComparator(Map<String, Long> unsortMap, final boolean order)
	    {

	        List<Entry<String, Long>> list = new LinkedList<Entry<String, Long>>(unsortMap.entrySet());

	        Collections.sort(list, new Comparator<Entry<String, Long>>()
	        {
	            public int compare(Entry<String, Long> o1,
	                    Entry<String, Long> o2)
	            {
	                if (order)
	                {
	                    return o1.getValue().compareTo(o2.getValue());
	                }
	                else
	                {
	                    return o2.getValue().compareTo(o1.getValue());

	                }
	            }
	        });

	        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
	        for (Entry<String, Long> entry : list)
	        {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }

	        return sortedMap;
	    }
}

