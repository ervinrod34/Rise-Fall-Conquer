package factions;
import java.util.ArrayList;
import org.json.JSONArray;
import web.LoginApache;
import web.QueryApache;

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
		JSONArray jsonLeaderBoard = new JSONArray();
		
		//login and get session id to webservice
		LoginApache login = new LoginApache();
		session = login.loginToWebservice();
		
		//execute the query on webserivce
		//executed query returns JsonArray
		if(session != null) {
			QueryApache q = new QueryApache(session, query);
			jsonLeaderBoard = q.execute();
			System.out.println(jsonLeaderBoard.toString());
		}
	}
	
}
