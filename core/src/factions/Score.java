package factions;

public class Score {
	
	private int scoreVal;
	private int	factionId;
	private int ldrBoardPos;
	private String name;
		
	public Score() {
		name = "";
	}
	
	public Score(int id) {
		name = "";
		this.factionId = id;
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
		this.scoreVal += scoreVal;
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
	public int getLdrBoardPos() {
		return ldrBoardPos;
	}

	public void setLdrBoardPos(int ldrBoardPos) {
		this.ldrBoardPos = ldrBoardPos;
	}
	
	public String toString() {
		String s;
		s = String.format("%-5d%-15s%-10d", ldrBoardPos, name, scoreVal);
		return s;
	}

}
