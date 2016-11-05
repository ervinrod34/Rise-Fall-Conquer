package factions;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import web.Login;
import web.Query;

public class ScoreBoard {
	
	//list of scores
	private ArrayList<Score> scoreList;
	
	//session id for pulling from database
	String session;
	
	/**
	 * ScoreBoard Constructor
	 */
	public ScoreBoard() {
		scoreList = new ArrayList<Score>();
	}
	
	/**
	 * Adds score to the scoreboard
	 * @param score
	 */
	public void addScore(Score score) {
		scoreList.add(score);
	}
	
	/**
	 * Prints scoreboard for debugging
	 */
	public void printScoreBoard() {
		for (Score score : scoreList) {
			System.out.println(score.toString());
		}
	}
	
	/**
	 * fills the scoreboard by pulling in from database
	 */
	public void fillFromDatabase() {
		String query = "SELECT * FROM `leaderboard` WHERE 1";
		JsonArray jsonLeaderBoard = new JsonArray();
		JsonParser parser = new JsonParser();
		
		//login and get session id to webservice
		Login login = new Login();
		session = login.loginToWebservice();
		
		//execute the query on webserivce
		//executed query returns JsonArray
		if(session != null) {
			Query q = new Query(session, query);
			jsonLeaderBoard = q.execute();
			//System.out.println(jsonLeaderBoard.toString());
		}
	}
	
}
