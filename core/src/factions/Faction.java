package factions;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import box2dLight.RayHandler;
import map.Map;
import map.ResourceID;
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
	 * The cost to upgrade a resource
	 */
	private int foodCost;
	private int woodCost;
	private int goldCost;
	
	/**
	 * The cost to promote a unit
	 */
	private int unitCost;
	
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
		this.foodCost = 0;
		this.woodCost = 0;
		this.goldCost = 0;
		this.unitCost = 0;
		
		cTerritory = c;

	}

	/**
	 * returns the list of claimed tiles
	 * @return
	 */
	public ArrayList<Tile> getClaimedTiles() {
		return ClaimedTiles;
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
	 * Adds the unit to the faction. This version assigns the 
	 * faction to the unit.
	 * @param type The units ID
	 * @param location The location of the unit
	 * @param faction The faction who created this unit
	 */
	public void addUnit(UnitID type, Tile location, Faction faction) {
		Unit unit = new Unit(type, location, mBoard, rayHandler);
		unit.setUnitFaction(faction);
		this.Units.add(unit);
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
	 * Draws units shapes
	 * @param batch
	 */
	public void drawUnits(ShapeRenderer rend){
		for(Unit u : Units){
			u.draw(rend);
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
	 * Passes in the resources that is to be made or upgraded
	 * and calculate the cost. Then, returns true if player has enough
	 * resources, false if not.
	 * @param id The resource to be upgraded
	 * @return boolean A boolean whether it is possible to upgrade or not
	 */
	public boolean checkCanUpgrade(ResourceID id) {
		//this.foodCost = 0;
		//this.woodCost = 0;
		//this.goldCost = 0;
		
		boolean canUpgrade = false;

		switch(id) {
		case FISH:
			this.goldCost = 50;
			break;
		case MEAT:
			this.goldCost = 50;
			break;
		case TEMP:
			this.goldCost = 50;
			break;
		case WHEAT:
			this.goldCost = 50;
			break;
		case COAL:
			this.goldCost = 50;
			break;
		case WOOD:
			this.goldCost = 50;
			break;
		case GOLD:
			this.goldCost = 100;
			break;
		//case HOME:
			//break;
		case TOWN:
			this.goldCost = 100;
			break;
		default:
			break;
		}
		
		if((this.goldCost <= this.totalGold) && 
		   (this.woodCost <= this.totalWood) &&
		   (this.foodCost <= this.totalFood)) {
			canUpgrade = true;
		}
		
		return canUpgrade;
	}
	
	/**
	 * Apply the current cost of the resources that is created
	 * or upgraded.
	 */
	public void applyUpgradeCost() {
		this.totalFood -= this.foodCost;
		this.totalWood -= this.woodCost;
		this.totalGold -= this.goldCost;
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
				case HOME:
					this.foodPerTurn += 10;
					this.woodPerTurn += 10;
					this.goldPerTurn += 10;
					break;
				case TOWN:
					this.foodPerTurn += 10;
					this.woodPerTurn += 10;
					this.goldPerTurn += 10;
					break;
				default:
					break;
				}
			}
			
		}
	}
	
	/**
	 * updates the factions score based on
	 * resources, size of territory, and num of units
	 */
	public void updateTotalScore() {
		int points;
		//points for resources
		points = this.totalFood * 5;
		points += this.totalWood * 5;
		points += this.totalGold * 10;
		
		//territory bonus
		points += (ClaimedTiles.size());
		
		//unit bonus
		points += (Units.size());
		
		//set the score value
		this.score.setScoreVal(points);	
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

	
	/**
	 * Calculates the cost to create or promote a unit. 
	 * Returns true if the unit is promotable, and false otherwise.
	 * @return boolean A boolean whether unit is promotable
	 */
	public boolean checkUnitCost(UnitID unitID) {
		boolean checkResult = false;
		
		switch(unitID) {
		case Basic:
			this.unitCost = 50;
			//this.unitCost += unit.getUpgradeLevel() * 50;
		case UNIT_1:
			this.unitCost = 50;
			//this.unitCost += unit.getUpgradeLevel() * 50;
		case UNDEAD_1:
			this.unitCost = 50;
			//this.unitCost += unit.getUpgradeLevel() * 50;
		default:
			break;
		}
		
		
		if(this.unitCost <= this.totalGold) {
			checkResult = true;
		}
		
		return checkResult;
	}
	
	/**
	 * Applies the cost to promote the unit.
	 */
	public void applyPromoteCost() {
		this.totalGold -= this.unitCost;
	}
}
