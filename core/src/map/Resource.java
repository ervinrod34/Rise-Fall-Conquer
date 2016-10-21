package map;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

public class Resource {

	private ResourceID id;
	private Texture Img;
	
	private int upgradeCount;
	private int bonus;
	
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

	public Texture getImg() {
		return Img;
	}

	public void setImg(Texture img) {
		Img = img;
	}
	
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
		this.bonus = 5 * (rand.nextInt(5) + 1);
	}
}
