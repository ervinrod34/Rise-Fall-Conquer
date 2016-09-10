package map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	TileID Id;
	private Texture Img;
	private Vector2 Location;
	
	/**
	 * Creates a new tile
	 * 
	 * @param id	The type of tile creating
	 * @param x		The x location
	 * @param y		The y location
	 */
	public Tile(TileID id, float x, float y) {
		this.Id = id;
		this.setTexture();
		this.Location = new Vector2(x,y);
	}

	/**
	 * Sets the texture of the tile based on its id
	 */
	private void setTexture(){
		switch(this.Id){
			case GRASS:
				this.Img = new Texture("Grass.png");
				break;
			case CASTLE:
				this.Img = new Texture("Castle.png");
				break;
			default:
				throw new IllegalArgumentException("ID: " + this.Id + " does not have an assigned textured.");
		}
	}

	public Texture getImg() {
		return Img;
	}

	public Vector2 getLocation() {
		return Location;
	}

	/**
	 * Sets the location of the tile
	 * @param x
	 * @param y
	 */
	public void setLocation(float x, float y) {
		Location.set(x, y);
	}

}
