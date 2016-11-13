package factions;

import org.json.JSONArray;

import web.LoginApache;
import web.QueryApache;

public class Score {
	
	private int scoreVal;
	private int	factionId;
	private String name;
		
	public Score() {
		name = "";
	}
	
	public Score(int id) {
		name = "";
		this.factionId = id;
	}
	
	public Score(String name, int scoreVal) {
		this.name = name;
		this.scoreVal = scoreVal;
	}
	
	/**
	 *  get value of score
	 * @return
	 */
	public int getScoreVal() {
		return scoreVal;
	}

	/**
	 * modify the value of score
	 * @param scoreVal
	 */
	public void setScoreVal(int scoreVal) {
		this.scoreVal = scoreVal;
	}

	/**
	 * get faction id associated with score
	 * @return
	 */
	public int getFactionId() {
		return factionId;
	}

	/**
	 * set faction to be associated with score
	 * @param factionId
	 */
	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}
	
	/**
	 * Sets name associated with score
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String s;
		s = String.format("%-15s %10d", name, scoreVal);
		return s;
	}
	
	/**
	 * pushes this score to the database
	 */
	public void pushToLeaderBoard() {
		JSONArray response = new JSONArray();
		String strQuery = "INSERT INTO `bifrost_4x`.`leaderboard` "
				+ "(`id`, `name`, `score`) VALUES (NULL, '" 
				+ this.name + "', '" 
				+ this.scoreVal + "');";
		LoginApache login = new LoginApache();
		String session = login.loginToWebservice();
		QueryApache query = new QueryApache(session, strQuery);
		response = query.execute();
		System.out.println(response.toString());
	}



}
