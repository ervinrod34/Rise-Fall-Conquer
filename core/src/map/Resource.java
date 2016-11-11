package map;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

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
}
