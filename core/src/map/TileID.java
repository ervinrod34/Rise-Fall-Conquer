package map;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.MyGdxGame;

/**
 * Stores all tile identifiers for the map grid
 * 
 * @author Porter
 *
 */
public enum TileID {
	
	WATER (1, "Tile_Water.png"),
	GRASS (2, "Tile_Grass.png"),
	DESERT (3, "Tile_Desert.png"),
	ICE (4, "Tile_Ice.png"),
	MOUNTAIN (5, "Tile_Mountain.png"),
	MOUNTAIN_ICE (6, "Tile_Mountain_Ice.png"),
	TERRITORY (1000, "Tile_Territory.png");
	
	private Texture Img;
	private int Id;
	
	/**
	 * Constructor for the TileID enums
	 * 
	 * @param filename	The path to the img file
	 */
	TileID(int id, String filename){
		String path = MyGdxGame.ASSET_PATH + "images" + File.separator + "tiles" + File.separator + filename;
		try{
			this.Img = new Texture(Gdx.files.internal(path));
		}catch(GdxRuntimeException e){
			Gdx.app.error(this.name(), "Could not load texture: " + Gdx.files.internal(path).file().getAbsolutePath());
		}
		this.Id = id;
	}
	/**
	 * Gives the texture for the given TileID
	 * @return
	 */
	public Texture getImg() {
		return Img;
	}
	/**
	 * Gives the MapId for the given TileID
	 * @return
	 */
	public int getId() {
		return Id;
	}
	/**
	 * Gives a random TileID
	 * @return
	 */
	public static TileID getRandomTileID(){
		return TileID.values()[(int)(Math.random() * TileID.values().length)];
	}
}
