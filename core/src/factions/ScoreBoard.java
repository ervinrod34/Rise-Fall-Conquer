package factions;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import web.LoginApache;
import web.QueryApache;

public class ScoreBoard {
	
	//list of scores
	private ArrayList<Score> scoreList;
	
	public ArrayList<Score> getScoreList() {
		return scoreList;
	}

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
		
		//SQL command to pull top 10 scores in database
		String query = "SELECT * FROM leaderboard ORDER by score DESC LIMIT 10";
		JSONArray response = new JSONArray();
		JSONArray ldrArr = new JSONArray();
		
		//login and get session id to webservice
		LoginApache login = new LoginApache();
		session = login.loginToWebservice();
		
		//execute the query on webserivce, only if session was made
		//executed query returns JsonArray
		if(session != null) {
			QueryApache q = new QueryApache(session, query);
			response = q.execute();
		}
		ldrArr = response.getJSONObject(1).getJSONArray("message");
		
		//loop through the JSONArray, add scores to the
		//score board
		for(int i = 0; i < ldrArr.length(); i++) {
			JSONArray arr = ldrArr.getJSONArray(i);
			JSONObject obj;
			Score score = new Score();
			score.setName(arr.getJSONObject(1).getString("name"));
			score.setScoreVal(arr.getJSONObject(2).getInt("score"));
			scoreList.add(score);
		}
	}
	
	/**
	 * checks if the given playerScore is less then or equal to
	 * any of the scores in this scoreBoard. If so, it's in the
	 * top 10.
	 * @param playerScore
	 * @return
	 */	
	public boolean isTopTen(Score playerScore) {
		if(scoreList.size() < 10)
			return true;
		for (int i=0; i < 10; i++ ) {
			Score other = scoreList.get(i);
			if(playerScore.getScoreVal() >= other.getScoreVal())
				return true;
		}
		return false;
	}
	
}
