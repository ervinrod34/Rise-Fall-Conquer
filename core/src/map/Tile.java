package map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	TileID Id;
	private Vector2 Location;
	private Vector2 GridLocation;
	private ResourceID rId;
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(TileID id, float x, float y) {
		this.Id = id;
		this.Location = new Vector2(x,y);
		this.GridLocation = new Vector2(0,0);
	}
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(int id, float x, float y) {
		for(TileID tId : TileID.values()){
			if(id == tId.getId()){
				this.Id = tId;
				break;
			}
		}
		this.Location = new Vector2(x,y);
		this.GridLocation = new Vector2(0,0);
	}
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(int id, int resId, float x, float y) {
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

}
