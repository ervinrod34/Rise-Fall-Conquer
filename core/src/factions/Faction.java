package factions;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import map.Map;
import map.Tile;
import map.TileID;
/**
 * Used to manage the unit, resources, and tiles owned by a faction
 * 
 * @author Porter
 * @co-author Ervin
 */
public class Faction {

	// Unique Id for the faction
	private int Id;
	// Home city for the faction
	private Tile HomeTile;
	// List of claimed tiles by the faction
	private ArrayList<Tile> ClaimedTiles;
	// this faction's score
	Score score;

	private Color cTerritory;
	
	private Map mBoard;
	
	/**
	 * The total resources variables
	 */
	private int totalFood;
	private int totalWood;
	private int totalGold;
	
	/**
	 * The resources per turn variables
	 */
	private int foodPerTurn;
	private int woodPerTurn;
	private int goldPerTurn;
	
	/**
	 * Construct a new Faction
	 * @param id The faction's id
	 * @param homeTile The faction's home tile
	 * @param m The map where the faction is
	 */
	public Faction(int id, Tile homeTile, Map m, Color c) {
		this.Id = id;
		this.mBoard = m;
		score = new Score(id);
		ClaimedTiles = new ArrayList<Tile>();
		this.HomeTile = homeTile;
		this.claimTile(HomeTile);
		
		//initialize resources variables
		this.totalFood = 0;
		this.totalWood = 0;
		this.totalGold = 0;
		this.foodPerTurn= 0;
		this.woodPerTurn = 0;
		this.goldPerTurn = 0;
		cTerritory = c;

	}

	/**
	 * Gets the faction's score
	 * @return
	 */
	public Score getScore() {
		return score;
	}
	/**
	 * Claims the specified tile for the faction
	 * @param tile
	 */
	public void claimTile(Tile tile){
		tile.setClaim(Id);
		ClaimedTiles.add(tile);
		this.updateResourcesPerTurn();
	}
	/**
	 * Unclaims the specified tile for the faction
	 * @param tile
	 */
	public void unclaimTile(Tile tile){
		tile.setClaim(0);
		ClaimedTiles.remove(tile);
	}
	
	/**
	 * Draws the territory own by this faction
	 * 
	 * @param batch
	 */
	public void drawTerritory(SpriteBatch batch){
		for(Tile tile : ClaimedTiles){
			Color c = batch.getColor();
			batch.setColor(cTerritory);
			batch.draw(TileID.TERRITORY.getImg(), tile.getLocation().x, tile.getLocation().y);
			batch.setColor(c);
		}
	}
	
	/**
	 * Returns the faction's home tile.
	 * @return A Tile object
	 */
	public Tile getHomeTile() {
		return HomeTile;
	}
	
	/**
	 * Updates the values of the player's resources per turn.
	 * Called by:
	 */
	public void updateResourcesPerTurn() {
		//resets the value of the resources per turn to 0 whenever this method is called
		this.foodPerTurn = 0;
		this.woodPerTurn = 0;
		this.goldPerTurn = 0;
		
		//scans the ArrayList of claimed tiles for resources
		for(int i = 0; i < this.ClaimedTiles.size(); i++) {
			Tile currentTile = this.ClaimedTiles.get(i);
			if(currentTile.getResource() != null) {
			switch(currentTile.getResourceID()) {
				case FISH:
					this.foodPerTurn += currentTile.getResource().getBonus();
					break;
				case MEAT:
					this.foodPerTurn += currentTile.getResource().getBonus();
					break;
				case TEMP:
					this.foodPerTurn += currentTile.getResource().getBonus();
					break;
				case WHEAT:
					this.foodPerTurn += currentTile.getResource().getBonus();
					break;
				case COAL:
					this.woodPerTurn += currentTile.getResource().getBonus();
					break;
				case WOOD:
					this.woodPerTurn += currentTile.getResource().getBonus();
					break;
				case GOLD:
					this.goldPerTurn += currentTile.getResource().getBonus();
					break;
				default:
					break;
				}
			}
			
		}
	}
	
	/**
	 * Updates the values of the player's total resources.
	 * Called by: GameScreen.java, after a turn cycle.
	 */
	public void updateTotalResources() {
		//adds the resources per turn to the total
		this.totalFood += this.foodPerTurn;
		this.totalWood += this.woodPerTurn;
		this.totalGold += this.goldPerTurn;
	}
	
	/**
	 * Returns the faction's total wood.
	 * @return An int value
	 */
	public int getTotalWood() {
		return this.totalWood;
	}
	
	/**
	 * Returns the faction's total food.
	 * @return An int value
	 */
	public int getTotalFood() {
		return this.totalFood;
	}
	
	/**
	 * Returns the faction's total gold.
	 * @return An int value
	 */
	public int getTotalGold() {
		return this.totalGold;
	}
	
	/**
	 * Returns this faction's wood per turn.
	 * @return An int value
	 */
	public int getWoodPerTurn() {
		return this.woodPerTurn;
	}
	
	/**
	 * Returns this faction's food per turn.
	 * @return An int value
	 */
	public int getFoodPerTurn() {
		return this.foodPerTurn;
	}
	
	/**
	 * Returns this faction's gold per turn.
	 * @return An int value
	 */
	public int getGoldPerTurn() {
		return this.goldPerTurn;
	}

}
