package map;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

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
		String assetpath = "assets" + File.separator;
		try{
			this.Img = new Texture(assetpath + filename);//Gdx.files.internal(assetpath + filename));
		}catch(GdxRuntimeException e){
			Gdx.app.error(this.name(), "Could not load texture: " + Gdx.files.internal(assetpath + filename).file().getAbsolutePath());
		}
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
