package factions;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	// Testing path movement
	private boolean displayMovementRange;
	private ArrayList<Tile> MovementRangeTiles;
	private int MaxMovementRange = 5;
	private int MovementRange = MaxMovementRange;
		
	public Unit(UnitID type, Tile location, Map m, RayHandler rayHandler){
		this.setupPointLight(rayHandler);
		this.setLocation(location, m);
		this.type = type;
		this.upgradable = true;
		
		// Set unit to first animation
		this.upgrade = -1;
		this.upgrade();
		
		this.displayMovementRange = false;
	}
	
	/**
	 * Draw the unit
	 * @param batch
	 */
	public void draw(SpriteBatch batch){
		if(displayMovementRange == true && MovementRangeTiles != null){
		for(Tile tile : MovementRangeTiles){
			Color c = batch.getColor();
			batch.setColor(Color.CYAN);
			batch.draw(TileID.TERRITORY.getImg(), tile.getLocation().x, tile.getLocation().y);
			batch.setColor(c);
		}
		}
		stateTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(stateTime, true), location.getLocation().x, location.getLocation().y);
	}
	
	/**
	 * Upgrades the unit
	 */
	public void upgrade(){
		if(this.upgrade < type.getTextures()[0].length){
			this.upgrade++;
			animation = new Animation(0.5f, type.getTextures()[this.upgrade]);
		}else{
			upgradable = false;
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
		MovementRangeTiles = m.getTilesInRange(location, this.MovementRange, null);
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
}
