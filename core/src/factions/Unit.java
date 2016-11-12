package factions;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import map.Map;
import map.Tile;
import map.TileID;

public class Unit {

	private Animation animation;
	private float stateTime;
	private UnitID type;
	private Tile location;
	
	private int upgrade;
	private boolean upgradable;
	
	private PointLight pLight;
	
	// Movement
	private boolean displayMovementRange;
	private ArrayList<Tile> MovementRangeTiles;
	private int MaxMovementRange = 5;
	private int MovementRange = MaxMovementRange;
	
	// Attack
	private boolean displayAttackRange;
	private boolean attacked;
	private ArrayList<Tile> AttackRangeTiles;
	private int MaxAttackRange = 5;
	private int AttackRange = MaxMovementRange;
	
	// Units stats
	private Bar barHealth;
	private float attack;
	private float defense;
	
	public Unit(UnitID type, Tile location, Map m, RayHandler rayHandler){
		
		// Set health bar
		this.barHealth = new Bar(100, 100);
		
		this.setupPointLight(rayHandler);
		this.setLocation(location, m);
		this.type = type;
		this.upgradable = true;
		
		// Set unit to first animation
		this.upgrade = -1;
		this.upgrade();
		
		this.displayMovementRange = false;
		this.displayAttackRange = false;
		this.attacked = false;
		
		this.attack = 25;
		this.defense = 10;
		
	}
	
	/**
	 * Draw the unit
	 * @param batch
	 */
	public void draw(SpriteBatch batch){
		if (displayMovementRange == true && MovementRangeTiles != null) {
			for (Tile tile : MovementRangeTiles) {
				Color c = batch.getColor();
				batch.setColor(Color.CYAN);
				batch.draw(TileID.TERRITORY.getImg(), tile.getLocation().x, tile.getLocation().y);
				batch.setColor(c);
			}
		}
		if (displayAttackRange == true && AttackRangeTiles != null) {
			for (Tile tile : AttackRangeTiles) {
				Color c = batch.getColor();
				batch.setColor(Color.RED);
				batch.draw(TileID.TERRITORY.getImg(), tile.getLocation().x, tile.getLocation().y);
				batch.setColor(c);
			}
		}
		stateTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(stateTime, true), location.getLocation().x, location.getLocation().y);
	}
	
	/**
	 * Draws units shape (health-bar)
	 * @param rend
	 */
	public void draw(ShapeRenderer rend){
		barHealth.draw(rend);
	}
	/**
	 * Upgrades the unit
	 */
	public void upgrade(){
		if(this.upgrade < type.getTextures().length - 1){
			this.upgrade++;
			animation = new Animation(0.5f, type.getTextures()[this.upgrade]);
		}else{
			upgradable = false;
		}
	}
	/**
	 * Attacks the given unit
	 * @param u
	 */
	public void attack(Unit u){
		this.attacked = true;
		if(this.attack > u.defense){
			u.damage(this.attack - u.defense);
		}
	}
	/**
	 * Display the movement range for the given tile on the map
	 * 
	 * @param m
	 */
	public void displayMovementRange(){
		if(this.displayMovementRange == false){
			this.displayMovementRange = true;
		}else{
			this.displayMovementRange = false;
		}
	}
	/**
	 * Display the attack range for the given tile on the map
	 * 
	 * @param m
	 */
	public void displayAttackRange(){
		if(this.displayAttackRange == false){
			this.displayAttackRange = true;
		}else{
			this.displayAttackRange = false;
		}
	}
	/**
	 * Sets up the point light for the tile
	 * @param rayHandler
	 */
	private void setupPointLight(RayHandler rayHandler){
		pLight = new PointLight(rayHandler,10, new Color((1/(float)255)*255f, (1/(float)255)*241f, (1/(float)255)*224f, 1),50,0,0);
		pLight.setSoft(true);
		pLight.setActive(true);
		this.setLightRadius(this.MovementRange);
	}
	/**
	 * Sets the lights location
	 */
	private void setLightLocation(){ 
		Vector2 LocationCenter = new Vector2();
		LocationCenter.set(location.getLocation().x + this.getLocation().getTileImg().getWidth()/2, location.getLocation().y + this.getLocation().getTileImg().getHeight()/2);
		pLight.setPosition(LocationCenter);
	}
	/**
	 * Sets the tile light to on or off
	 * @param value
	 */
	public void setLightRadius(float value){
		pLight.setDistance(value * 50);
	}
	/**
	 * Set the units location
	 * @param x
	 * @param y
	 */
	public void setLocation(Tile location, Map m){
		int DistancedMoved = m.getTileDistance(this.location, location, MaxMovementRange);
		if(DistancedMoved != -1){
			this.MovementRange -= DistancedMoved;
		}
		this.location = location;
		this.setLightLocation();
		this.barHealth.setLocation(this.location.getLocation().x + this.location.getTileImg().getWidth()/2, this.location.getLocation().y 
				+ this.location.getTileImg().getHeight()/2 + 22);
		MovementRangeTiles = m.getTilesInRange(location, this.MovementRange, null);
		AttackRangeTiles = m.getTilesInRange(location, this.AttackRange, null);
	}
	/**
	 * Updates the unit's fields, call at end of turn
	 */
	public void update(Map m){
		// Update movement range
		this.MovementRange = this.MaxMovementRange;
		MovementRangeTiles = m.getTilesInRange(location, this.MovementRange, null);
		// Update attack range
		this.AttackRange = this.MaxAttackRange;
		AttackRangeTiles = m.getTilesInRange(location, this.AttackRange, null);
		// Set has attacked to false
		this.attacked = false;
	}
	/**
	 * Get the units location
	 * @return
	 */
	public Tile getLocation(){
		return this.location;
	}
	/**
	 * Get the units type
	 * @return
	 */
	public UnitID getType() {
		return type;
	}
	/**
	 * Get if the unit is upgradable
	 * @return
	 */
	public boolean isUpgradable() {
		return upgradable;
	}
	/**
	 * Get the tiles within range
	 * @return
	 */
	public ArrayList<Tile> getMovementRange(){
		return MovementRangeTiles;
	}

	/**
	 * @return the attackRangeTiles
	 */
	public ArrayList<Tile> getAttackRange() {
		return AttackRangeTiles;
	}

	/**
	 * @return the health
	 */
	public double getHealth() {
		return barHealth.getValue();
	}

	/**
	 * @return the attack
	 */
	public double getAttack() {
		return attack;
	}

	/**
	 * @return the defense
	 */
	public double getDefense() {
		return defense;
	}
	/**
	 * Damages the unit by the amount given
	 * @param amount
	 */
	public void damage(float amount){
		if(barHealth.getValue() - amount < 0){
			barHealth.setValue(0);
			return;
		}
		barHealth.setValue(barHealth.getValue() - amount);
	}

	public boolean hasAttacked() {
		return attacked;
	}
}
