package factions;
import java.util.ArrayList;

public class ScoreBoard {
	
	//list of scores
	private ArrayList<Score> scoreList;
	
	public ScoreBoard() {
		scoreList = new ArrayList<Score>();
	}
	
	public void addScore(Score score) {
		scoreList.add(score);
	}
	
	public void printScoreBoard() {
		for (Score score : scoreList) {
			System.out.println(score.toString());
		}
	}
}
