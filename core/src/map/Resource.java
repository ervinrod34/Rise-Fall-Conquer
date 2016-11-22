package map;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import factions.Bar;

public class Resource {

	/**
	 * The ResourceID
	 */
	private ResourceID id;
	/**
	 * The image used by this Resource
	 */
	private Texture Img;
	/**
	 * The level of upgrade of this Resource
	 */
	private int upgradeCount;	
	/**
	 * The bonus this resource provides
	 */
	private int bonus;
	private Bar barHealth;
	private boolean displayBar;
	private float deltaTime;
	
	/**
	 * Constructs a Resource object with a ResourceID.
	 * @param id A ResourceID
	 */
	public Resource(ResourceID id) {
		this.id = id;
		this.upgradeCount = 0;
		this.Img = id.getUpgrades()[upgradeCount];
		Random rand = new Random();
		this.bonus = 5 * (rand.nextInt(5) + 1);
		
		barHealth = new Bar(100,100);
	}

	/**
	 * Returns the bonus of this resource
	 * @return
	 */
	public int getBonus() {
		return this.bonus;
	}
	
	/**
	 * Update the bonus given by this resource per turn.
	 */
	public void updateBonus() {
		if(this.upgradeCount > 0) {
			this.bonus = this.bonus + (this.upgradeCount * 10);
		}
	}
	
	/**
	 * Upgrades the upgradeCount, updates the bonus return by this resource
	 * and change the image based on the level of the upgrade
	 * 
	 */
	public void upgradeTile(){
		//System.out.println("Upgrade me!");
		try {
			if(this.upgradeCount < id.getUpgrades().length) {
				this.upgradeCount++;
				this.updateBonus();
				this.setImg(id.getUpgrades()[this.upgradeCount]);
			}
			else {
				System.out.println("Can't upgrade anymore.");
			}
		} catch (IndexOutOfBoundsException o) {
			
		}
	}

	/**
	 * Returns the image of this Resource
	 * @return A Texture object
	 */
	public Texture getImg() {
		return Img;
	}

	/**
	 * Change the image of this resource.
	 * @param img A reference to a Texture object
	 */
	public void setImg(Texture img) {
		Img = img;
	}
	
	/**
	 * Returns the ResourceID of this Resource.
	 * @return A ResourceID
	 */
	public ResourceID getID(){
		return id;
	}
	
	/**
	 * Create a new resource
	 * @param id
	 */
	public void setID(ResourceID id){
		this.id = id;
		this.upgradeCount = 0;
		this.Img = id.getUpgrades()[upgradeCount];
		Random rand = new Random();
		this.bonus = rand.nextInt(30) + 1;
	}
	/**
	 * Updates the health bar location
	 * @param t
	 */
	public void updateLocation(Vector2 loc){
		this.barHealth.setLocation(loc.x + Map.TILEWIDTH/2, loc.y 
				+ Map.TILEHEIGHT/2 + 22);
	}
	/**
	 * @return the health
	 */
	public double getHealth() {
		return barHealth.getValue();
	}
	
	/**
	 * Damages the unit by the amount given
	 * @param amount
	 */
	public void damage(float amount){
		// Show health bar
		this.deltaTime = 0;
		this.displayBar = true;
		
		if(barHealth.getValue() - amount < 0){
			barHealth.setValue(0);
			return;
		}
		barHealth.setValue(barHealth.getValue() - amount);
	}
	
	/**
	 * Draws units shape (health-bar)
	 * @param rend
	 */
	public void draw(ShapeRenderer rend){
		deltaTime += Gdx.graphics.getDeltaTime();
		// Show health bar for 5 seconds
		if(deltaTime >= 5){
			this.displayBar = false;
			deltaTime = 100;
		}
		if(displayBar == true){
			barHealth.draw(rend);
		}
	}
}
