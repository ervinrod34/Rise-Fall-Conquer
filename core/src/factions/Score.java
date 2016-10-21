package factions;

public class Score {
	
	private int scoreVal;
	private int	factionId;
		
	public Score(int id) {
		this.factionId = id;
	}
	
	/*
	 * get the value of this score
	 */
	public int getScoreVal() {
		return scoreVal;
	}

	/*
	 * modify the value of this score
	 */
	public void setScoreVal(int scoreVal) {
		this.scoreVal += scoreVal;
	}

	/*
	 * get faction associated with this score
	 */
	public int getFactionId() {
		return factionId;
	}

	/*
	 * set faction to be associated with this score
	 */
	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}
}
