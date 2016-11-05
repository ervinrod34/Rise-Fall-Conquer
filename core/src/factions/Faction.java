package factions;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import box2dLight.RayHandler;
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
	// List of tiles the player can build on
	private ArrayList<Tile> BuildRange;
	// List of units by the faction
	private ArrayList<Unit> Units;
	// this faction's score
	Score score;
	RayHandler rayHandler;
	
	private Color cTerritory;
	
	public Color getcTerritory() {
		return cTerritory;
	}

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
	public Faction(int id, Tile homeTile, Map m, Color c, RayHandler rayHandler) {
		this.Id = id;
		this.mBoard = m;
		this.rayHandler = rayHandler;
		score = new Score(id);
		ClaimedTiles = new ArrayList<Tile>();
		BuildRange = new ArrayList<Tile>();
		Units = new ArrayList<Unit>();
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
	 * Calculates the range in which factions can build
	 */
	public void calculateBuildRange(){
		BuildRange.clear();
		for(Tile t : ClaimedTiles){
			// Must have a resource to increase build range
			if(t.getResource() == null){
				continue;
			}
			// Add the tile itself
			BuildRange.add(t);
			// Add all its neighbors
			for(Tile neighbor : mBoard.getNeighborTiles(t)){
				if(BuildRange.contains(neighbor) == false){
					BuildRange.add(neighbor);
				}
			}
		}
		// Add units into build range
		for(Unit u : this.getUnits()){
			if(BuildRange.contains(u.getLocation()) == false){
				BuildRange.add(u.getLocation());
				// Add all its neighbors
				for(Tile neighbor : mBoard.getNeighborTiles(u.getLocation())){
					if(BuildRange.contains(neighbor) == false){
						BuildRange.add(neighbor);
					}
				}
			}
		}
	}
	/**
	 * Claims the specified tile for the faction
	 * @param tile
	 */
	public void claimTile(Tile tile){
		tile.setClaim(Id);
		ClaimedTiles.add(tile);
		this.calculateBuildRange();
		this.updateResourcesPerTurn();
	}
	/**
	 * Unclaims the specified tile for the faction
	 * @param tile
	 */
	public void unclaimTile(Tile tile){
		tile.setClaim(0);
		ClaimedTiles.remove(tile);
		this.calculateBuildRange();
	}
	/**
	 * Adds the unit to the faction
	 * @param type
	 * @param location
	 */
	public void addUnit(UnitID type, Tile location){
		this.Units.add(new Unit(type, location, mBoard, rayHandler));
	}
	/**
	 * Removes the unit from the faction
	 * @param unit
	 */
	public void removeUnit(Unit unit){
		this.Units.remove(unit);
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
	 * Draws units owned by faction
	 * @param batch
	 */
	public void drawUnits(SpriteBatch batch){
		for(Unit u : Units){
			u.draw(batch);
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
	 * Gives the units the faction owns
	 * @return
	 */
	public ArrayList<Unit> getUnits(){
		return Units;
	}
	/**
	 * Get the range in which the player can build
	 * @return
	 */
	public ArrayList<Tile> getBuildRange(){
		return BuildRange;
	}
	/**
	 * get the factions Id (1 is the player)
	 * @return
	 */
	public int getId() {
		return Id;
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
