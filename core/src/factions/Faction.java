package factions;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

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
 * 
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
	
	/**
	 * The number of city
	 */
	private int cityCount;
	
	Score score;
	RayHandler rayHandler;
	
	private Color cTerritory;
	
	public Color getcTerritory() {
		return cTerritory;
	}

	protected Map mBoard;
	
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
	private int resFoodCost;
	private int resWoodCost;
	private int resGoldCost;
	
	/**
	 * The cost to promote a unit
	 */
	
	private boolean turnOver;
	private int unitFoodCost;
	private int unitWoodCost;
	private int unitGoldCost;
	
	// represents the faction that last attacked
	private Faction lastAttacker;

	
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
		lastAttacker = null;
		
		this.cityCount = this.getCityCount();
		
		//initialize resources variables
		this.totalFood = 0;
		this.totalWood = 0;
		this.totalGold = 0;
		this.foodPerTurn= 0;
		this.woodPerTurn = 0;
		this.goldPerTurn = 0;
		this.resFoodCost = 0;
		this.resWoodCost = 0;
		this.resGoldCost = 0;
		this.unitFoodCost = 0;
		this.unitWoodCost = 0;
		this.unitGoldCost = 0;
		
		cTerritory = c;
		this.setTurnOver(false);

	}

	/**
	 * returns the list of claimed tiles
	 * @return
	 */
	public ArrayList<Tile> getClaimedTiles() {
		return ClaimedTiles;
	}
	
	/**
	 * returns a random claimed tile of this faction
	 * @return
	 */
	public Tile getRandomClaimedTile() {
		Random rand = new Random();
		return ClaimedTiles.get(rand.nextInt(ClaimedTiles.size()));
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
	 * Counts the number of city tiles this faction has.
	 * Then, return the city count.
	 * @return An int specifying the city count
	 */
	public int getCityCount() {
		int tempCityCount = 0;
		for(int i = 0; i < this.ClaimedTiles.size(); i++) {
			if(this.ClaimedTiles.get(i).getResourceID() == ResourceID.HOME) {
				tempCityCount++;
			}
		}
		this.cityCount = tempCityCount;
		
		return this.cityCount;
	}
	
	/**
	 * Adds the unit to the faction
	 * @param type
	 * @param location
	 */
	private void addUnit(UnitID type, Tile location){
		this.Units.add(new Unit(type, location, mBoard));
	}
	
	/**
	 * Adds the unit to the faction. This version assigns the 
	 * faction to the unit.
	 * @param type The units ID
	 * @param location The location of the unit
	 * @param faction The faction who created this unit
	 */
	public void addUnit(UnitID type, Tile location, Faction faction) {
		Unit unit = new Unit(type, location, mBoard);
		unit.setUnitFaction(this);
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
			this.resGoldCost = 50;
			break;
		case MEAT:
			this.resGoldCost = 50;
			break;
		case TEMP:
			this.resGoldCost = 50;
			break;
		case WHEAT:
			this.resGoldCost = 50;
			break;
		case COAL:
			this.resGoldCost = 50;
			break;
		case WOOD:
			this.resGoldCost = 50;
			break;
		case GOLD:
			this.resGoldCost = 100;
			break;
		//case HOME:
			//break;
		case TOWN:
			this.resGoldCost = 100;
			break;
		default:
			break;
		}
		
		if((this.resGoldCost <= this.totalGold) && 
		   (this.resWoodCost <= this.totalWood) &&
		   (this.resFoodCost <= this.totalFood)) {
			canUpgrade = true;
		}
		
		return canUpgrade;
	}
	
	/**
	 * Apply the current cost of the resources that is created
	 * or upgraded.
	 */
	public void applyUpgradeCost() {
		this.totalFood -= this.resFoodCost;
		this.totalWood -= this.resWoodCost;
		this.totalGold -= this.resGoldCost;
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
	 * Returns the faction's total gold.
	 * @return An int value
	 */
	public int setTotalGold(int value) {
		return this.totalGold = value;
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
	 * Return the food cost to upgrade resource.
	 * @return An int value
	 */
	public int getResFoodCost() {
		return this.resFoodCost;
	}
	
	/**
	 * Return the wood cost to upgrade resource.
	 * @return An int value
	 */
	public int getResWoodCost() {
		return this.resWoodCost;
	}
	
	/**
	 * Return the gold cost to upgrade resource.
	 * @return An int value
	 */
	public int getResGoldCost() {
		return this.resGoldCost;
	}
	
	/**
	 * Return the food cost to upgrade unit.
	 * @return An int value
	 */
	public int getUnitFoodCost() {
		return this.unitFoodCost;
	}
	
	/**
	 * Return the wood cost to upgrade unit.
	 * @return An int value
	 */
	public int getUnitWoodCost() {
		return this.unitWoodCost;
	}

	/**
	 * Return the gold cost to upgrade unit.
	 * @return An int value
	 */
	public int getUnitGoldCost() {
		return this.unitGoldCost;
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
			this.unitGoldCost = 50;
			//this.unitCost += unit.getUpgradeLevel() * 50;
		case UNIT_1:
			this.unitGoldCost = 50;
			//this.unitCost += unit.getUpgradeLevel() * 50;
		case UNDEAD_1:
			this.unitGoldCost = 50;
			//this.unitCost += unit.getUpgradeLevel() * 50;
		default:
			break;
		}
		
		
		if(this.unitGoldCost <= this.totalGold) {
			checkResult = true;
		}
		
		return checkResult;
	}
	
	/**
	 * Applies the cost to promote the unit.
	 */
	public void applyPromoteCost() {
		this.totalGold -= this.unitGoldCost;
	}
	
	/**
	 * updates the factions score based on
	 * resources, size of territory, and num of units
	 */
	public void updateTotalScore() {
		int points;
		//points for resources
		points = this.totalFood * 1;
		points += this.totalWood * 1;
		points += this.totalGold * 3;
		
		//territory bonus
		points += (ClaimedTiles.size());
		
		//unit bonus
		points += (Units.size());
		
		//set the score value
		this.score.setScoreVal(points);	
	}
	
	public boolean isTurnOver() {
		return turnOver;
	}

	public void setTurnOver(boolean turnOver) {
		this.turnOver = turnOver;
	}
	
	public void AI(ArrayList<Faction> factions){
		// Loop through this factions unit's looking for enemies
		for(Unit u : Units){

//			//UNCOMMENT TO TEST TARGETING
			u.setTarget(factions.get(0).getHomeTile());
						
			// Get each units attack range and movement range
			//ArrayList<Tile> uAttackRange = u.getAttackRange();
			ArrayList<Tile> uMovementRange = u.getMovementRange();
			// Variable for if unit has attacked or moved
			boolean attacked = AIAttackLogic(u, factions);
			// Move in a random direction if unit did not attack
			if(attacked == false){
				if(u.hasTarget()) {
					moveTowardTarget(u);
				} else {
					Random rand = new Random();
					u.setLocation(uMovementRange.get(rand.nextInt(uMovementRange.size())), mBoard);
				}
//				Random rand = new Random();
//				u.setLocation(uMovementRange.get(rand.nextInt(uMovementRange.size())), mBoard);
			}
		}
		// Randomly build a unit or a new building
		Random rand = new Random();
		if (rand.nextBoolean() == true) {
			AIBuildLogic();
		} else {
			AIBuildUnitLogic(factions);
		}
	}
	
	public boolean hasLastAttacker() {
		if (this.lastAttacker != null)
			return true;
		else
			return false;
	}
	public Faction getLastAttacker() {
		
		return lastAttacker;
	}

	public void setLastAttacker(Faction lastAttacker) {
		this.lastAttacker = lastAttacker;
	}
	
	/**
	 * This function moves a unit toward it's target
	 * @param unit
	 * @author Zach Floyd
	 */
	private void moveTowardTarget(Unit unit) {

		// get the movement range of unit
		ArrayList<Tile> uMovementRange = unit.getMovementRange();
		// get coordinates of the target
		Vector2 targetCoord = new Vector2(unit.getTarget().getLocation());
		
		float thisDist;
		int ind = -1;
		
		//pick a first minimum distance
		float minDist = uMovementRange.get(0).getLocation().dst(targetCoord);
		for(Tile t : uMovementRange) {
			//dist between this tile and target
			thisDist = t.getLocation().dst(targetCoord);
			//check if it's the new minimum
			if(thisDist <= minDist) {
				minDist = thisDist;
				// get the index of this object
				ind = uMovementRange.indexOf(t);
			}
		}
		// don't do anything if we didn't find a min
		if (ind == -1)
			return;
		else
			// move the unit
			unit.setLocation(uMovementRange.get(ind), mBoard);	
	}
	
	/**
	 * Logic for AI attacking
	 * @param u
	 * @param factions
	 * @return
	 */
	private boolean AIAttackLogic(Unit u, ArrayList<Faction> factions){
		// Get unit attack range and movement range
		ArrayList<Tile> uAttackRange = u.getAttackRange();
		boolean attacked = false;
		// Loop through all other factions
		for (Faction f : factions) {
			// Make sure we are not checking our faction
			if (f == this) {
				continue;
			}
			// Check if any enemies are in range
			ArrayList<Unit> attackableUnits = new ArrayList<Unit>();
			for (Unit u1 : f.getUnits()) {
				// Attack first enemy seen in range
				if (uAttackRange.contains(u1.getLocation()) == true) {
					// Add to attackable units
					attackableUnits.add(u1);
				}
			}
			// If no units are in range we do not attack
			if(attackableUnits.size() != 0){
				// Compare all unit's the AI can attack
				Unit unitToAttack = attackableUnits.get(0);
				for (Unit u1 : attackableUnits) {
					for (Unit u2 : attackableUnits) {
						if (u1 != u2) {
							// Look for unit with lowest health in range
							if(u1.getHealth() < u2.getHealth()){
								unitToAttack = u1;
							}
						}
					}
				}
				// Attack the given unit
				 u.attack(unitToAttack);
			     // this faction is unitToAttack's faction's lastAttacker
				 //unitToAttack.getUnitsFaction().setLastAttacker(this);
				 System.out.println(unitToAttack.getUnitsFaction().getLastAttacker().toString());
				 attacked = true;
				 break;
			}
			// AI attacks buildings if they are in range
			for(Tile tile : f.getClaimedTiles()){
				// Check if tile contains a resource
				if(uAttackRange.contains(tile) && tile.getResource() != null){
					// Check that resource is not owned by current faction
					if(tile.getClaim() != this.Id){
						u.attack(tile.getResource());
						attacked = true;
						break;
					}
				}
			}
			// If we have attacked, no point in looking through other factions
			if(attacked == true){
				break;
			}
		}
		// No one to attack :)
		return attacked;
	}
	/**
	 * Logic for AI building buildings
	 * @return
	 */
	private boolean AIBuildLogic(){
		// Update where the AI can build
		this.calculateBuildRange();
		// Variable for if we build
		boolean Built = false;
		Random rand = new Random();
		ResourceID rsrcIDs[] = {
				ResourceID.TOWN, ResourceID.COAL, ResourceID.FISH, ResourceID.GOLD
				,ResourceID.MEAT, ResourceID.WHEAT, ResourceID.WOOD
		};
		
		// get a random resource
		int ind = rand.nextInt(rsrcIDs.length);
		ResourceID randRsrc = rsrcIDs[ind];

		// Currently only check if we can build wheat
		if(this.checkCanUpgrade(randRsrc) == true){
			// Get the actual build range of unclaimed tiles
			ArrayList<Tile> actualBuildRange = new ArrayList<Tile>();
			for(Tile tile : this.BuildRange){
				if(tile.getClaim() == 0){
					actualBuildRange.add(tile);
				}
			}
			// Pick a random tile in build range to build wheat
			
			Tile buildTile = actualBuildRange.get(rand.nextInt(actualBuildRange.size()));
			buildTile.setResourceID(randRsrc);
			this.claimTile(buildTile);
			this.applyUpgradeCost();
			Built = true;
		}
		return Built;
	}
	/**
	 * Logic for AI creating units
	 * @param factions
	 * @return
	 */
	private boolean AIBuildUnitLogic(ArrayList<Faction> factions){
		// Find all home tiles for faction
		ArrayList<Tile> homeList = new ArrayList<Tile>();
		for(Tile tile : this.getClaimedTiles()){
			if(tile.getResourceID() != null && tile.getResourceID().equals(ResourceID.HOME)){
				homeList.add(tile);
			}
		}
		if(homeList.size() == 0){
			return false;
		}
		// Pick a random home tile to build on
		Random rand = new Random();
		Tile tile = homeList.get(rand.nextInt(homeList.size()));
		// attempt to create a unit
		try {
			//Makes sure there are no units on the tile before creating units
			for(Faction f : factions){
				for(Unit u : f.getUnits()){
					if(u.getLocation() == tile){
						return false;
					}
				}
			}
			// Create a new unit
			if(this.checkUnitCost(UnitID.Basic) == true) {
				this.applyPromoteCost();
				this.addUnit(UnitID.Basic, tile, this);
				return true;
			}
		} catch (NullPointerException ne) {
			Gdx.app.error(null, "Unit Promoye error.");
		}
		return false;
	}
}
