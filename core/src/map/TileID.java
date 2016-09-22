package map;

import com.badlogic.gdx.graphics.Texture;

/**
 * Stores all tile identifiers for the map grid
 * 
 * @author Porter
 *
 */
public enum TileID {
	
	GRASS ("Tile_Grass.png"),
	DESERT ("Tile_Desert.png"),
	ICE ("Tile_Ice.png"),
	MOUNTAIN ("Tile_Mountain.png"),
	MOUNTAIN_ICE ("Tile_Mountain_Ice.png"),
	WATER ("Tile_Water.png");
	
	private Texture Img;
	/**
	 * Constructor for the TileID enums
	 * 
	 * @param filename	The path to the img file
	 */
	TileID(String filename){
		//TODO Handle errors finding file
		this.Img = new Texture(filename);
	}
	/**
	 * Gives the texture for the given TileID
	 * @return
	 */
	public Texture getImg() {
		return Img;
	}
	/**
	 * Gives a random TileID
	 * @return
	 */
	public static TileID getRandomTileID(){
		return TileID.values()[(int)(Math.random() * TileID.values().length)];
	}
}
