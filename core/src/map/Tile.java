package map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Tile {

	TileID Id;
	private Vector2 Location;
	private Vector2 GridLocation;
	private ResourceID rId;
	private PointLight pLight;
	private int Claim;
	
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(TileID id, float x, float y, RayHandler rayHandler) {
		this.Id = id;
		this.Location = new Vector2(x,y);
		this.GridLocation = new Vector2(0,0);
		setupPointLight(rayHandler);
	}
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(int id, float x, float y, RayHandler rayHandler) {
		for(TileID tId : TileID.values()){
			if(id == tId.getId()){
				this.Id = tId;
				break;
			}
		}
		this.Location = new Vector2(x,y);
		this.GridLocation = new Vector2(0,0);
		setupPointLight(rayHandler);
	}
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(int id, int resId, float x, float y, RayHandler rayHandler) {
		for(TileID tId : TileID.values()){
			if(id == tId.getId()){
				this.Id = tId;
				break;
			}
		}
		for(ResourceID rId : ResourceID.values()){
			if(resId == rId.getId()){
				this.rId = rId;
				break;
			}
		}
		this.Location = new Vector2(x,y);
		this.GridLocation = new Vector2(0,0);
		setupPointLight(rayHandler);
	}
	/**
	 * Sets up the point light for the tile
	 * @param rayHandler
	 */
	private void setupPointLight(RayHandler rayHandler){
		pLight = new PointLight(rayHandler,10, new Color((1/(float)255)*255f, (1/(float)255)*241f, (1/(float)255)*224f, 1),50,0,0);
		Vector2 LocationCenter = new Vector2();
		LocationCenter.set(this.Location.x + this.getTileImg().getWidth()/2, this.Location.y + this.getTileImg().getHeight()/2);
		pLight.setPosition(LocationCenter);
		pLight.setSoft(true);
		pLight.setActive(false);
	}
	/**
	 * Sets the tile light to on or off
	 * @param value
	 */
	public void setLight(boolean value){
		pLight.setActive(value);
	}
	/**
	 * returns true if light is on
	 * 
	 * @return
	 */
	public boolean isLightOn(){
		return pLight.isActive();
	}
	/**
	 * Returns the img for the given tile
	 * 
	 * @return
	 */
	public Texture getTileImg() {
		return this.Id.getImg();
	}
	/**
	 * Returns the location for the given tile, not in grid(x,y)
	 * 
	 * @return
	 */
	public Vector2 getLocation() {
		return Location;
	}
	/**
	 * Sets the location of the tile, not in grid(x,y)
	 * @param x
	 * @param y
	 */
	public void setLocation(float x, float y) {
		Location.set(x, y);
	}
	/**
	 * Grid location is coordinates based on other map tiles
	 * 
	 * @return
	 */
	public Vector2 getGridLocation() {
		return GridLocation;
	}
	/**
	 * Grid location is coordinates based on other map tiles
	 * 
	 */
	public void setGridLocation(float x, float y) {
		GridLocation.set(x, y);
	}
	public Texture getResourceImg() {
		return rId.getImg();
	}
	public ResourceID getResource() {
		return rId;
	}
	public int getClaim() {
		return Claim;
	}
	public void setClaim(int claim) {
		Claim = claim;
	}

}
