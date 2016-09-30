package map;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public enum ResourceID {

	HOME(65, "Tile_Home.png", "Tile_Home.png", "Tile_Home.png", "Tile_Home.png"),
	TOWN(66, "Tile_City.png", "Tile_City.png", "Tile_City.png", "Tile_City.png");

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
		String assetpath = "assets" + File.separator;
		this.upgrades = new Texture[upgrades.length];
		for (int i = 0; i < upgrades.length; i++) {
			try {
				this.upgrades[i] = new Texture(assetpath + upgrades[i]);
			} catch (GdxRuntimeException e) {
				Gdx.app.error(this.name(), "Could not load texture: "
						+ Gdx.files.internal(assetpath + upgrades[i]).file().getAbsolutePath());
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
