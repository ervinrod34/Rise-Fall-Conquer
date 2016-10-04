package map;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.MyGdxGame;

public enum ResourceID {

	HOME(65, "Tile_Home.png", "Tile_Home.png", "Tile_Home.png", "Tile_Home.png"),
	TOWN(66, "Tile_City.png", "Tile_City.png", "Tile_City.png", "Tile_City.png"),
	COAL(67, "Tile_Coal.png", "Tile_Coal1.png", "Tile_Coal2.png"),
	FISH(68, "Tile_Fish.png", "Tile_Fish1.png", "Tile_Fish2.png"),
	GOLD(69, "Tile_Gold.png", "Tile_Gold1.png", "Tile_Gold2.png", "Tile_Gold3.png", "Tile_Gold4.png", "Tile_Gold5.png"),
	MEAT(70, "Tile_Meat.png", "Tile_Meat1.png", "Tile_Meat2.png"),
	TEMP(71, "Tile_Temp.png", "Tile_Temp1.png", "Tile_Temp2.png", "Tile_Temp3.png"),
	WHEAT(72, "Tile_Wheat.png", "Tile_Wheat1.png", "Tile_Wheat2.png"),
	WOOD(73, "Tile_Wood.png", "Tile_Wood1.png", "Tile_Wood2.png");

	private Texture Img;
	private int Id;
	private int upgradeCount;
	private Texture[] upgrades;

	/**
	 * Constructor for the ResourceID enums
	 * 
	 * @param filename
	 *            The path to the img file
	 */
	ResourceID(int id, String... upgrades) {
		String path = MyGdxGame.ASSET_PATH + "images" + File.separator + "resources" + File.separator;
		this.upgrades = new Texture[upgrades.length];
		for (int i = 0; i < upgrades.length; i++) {
			try {
				this.upgrades[i] = new Texture(Gdx.files.internal(path + upgrades[i]));
			} catch (GdxRuntimeException e) {
				Gdx.app.error(this.name(), "Could not load texture: "
						+ Gdx.files.internal(path + upgrades[i]).file().getAbsolutePath());
			}
		}
		this.upgradeCount = 0;
		this.Img = this.upgrades[upgradeCount];
		this.Id = id;
	}

	/**
	 * Gives the texture for the given TileID
	 * 
	 * @return
	 */
	public Texture getImg() {
		return Img;
	}

	/**
	 * Gives the MapId for the given TileID
	 * 
	 * @return
	 */
	public int getId() {
		return Id;
	}

	/**
	 * Gives a random TileID
	 * 
	 * @return
	 */
	public static TileID getRandomTileID() {
		return TileID.values()[(int) (Math.random() * TileID.values().length)];
	}
}
