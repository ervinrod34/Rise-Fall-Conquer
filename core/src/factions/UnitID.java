package factions;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.MyGdxGame;

import map.TileID;

/**
 * Used to load unit textures
 * 
 * @author Porter
 *
 */
public enum UnitID {
	Basic(65, "BasicUnit0.png", "BasicUnit1.png", "BasicUnit2.png"),
	UNIT_1(66, "Unit0.png", "Unit1.png", "Unit2.png", "Unit3.png", "Unit4.png"),
	UNDEAD_1(66, "UndeadUnit1.png", "UndeadUnit2.png", "UndeadUnit3.png");

	private int Id;
	//private Texture[] textures;
	private TextureRegion[][] textures;
			
	/**
	 * Constructor for the UnitID enums
	 * 
	 * @param filename The path to the img file
	 * 
	 */
	UnitID(int id, String... upgrades) {
		String path = MyGdxGame.ASSET_PATH + "images" + File.separator + "units" + File.separator;
		//this.textures = new Texture[upgrades.length];
		this.textures = new TextureRegion[upgrades.length][1];
		for (int i = 0; i < upgrades.length; i++) {
			try {
				//this.textures[i] = new Texture(Gdx.files.internal(path + upgrades[i]));
				this.textures[i] = load55bitAnimation(new Texture(Gdx.files.internal(path + upgrades[i])));
			} catch (GdxRuntimeException e) {
				Gdx.app.error(this.name(), "Could not load texture: "
						+ Gdx.files.internal(path + upgrades[i]).file().getAbsolutePath());
			}
		}
		this.Id = id;
	}

	/**
	 * Gives the MapId for the given TileID
	 * 
	 * @return
	 */
	public int getId() {
		return this.Id;
	}

	/**
	 * Gives a random TileID
	 * 
	 * @return
	 */
	public static TileID getRandomTileID() {
		return TileID.values()[(int) (Math.random() * TileID.values().length)];
	}
	
//	public Texture[] getTextures(){
//		return textures;
//	}
	
	public TextureRegion[][] getTextures(){
		return textures;
	}
	
	private static TextureRegion[] load55bitAnimation(Texture animatedSheet){
	    TextureRegion[] animatedFrames;
		int frame_columns = animatedSheet.getWidth()/55;
	    int frame_rows = 1;
        TextureRegion[][] tmp = TextureRegion.split(animatedSheet, animatedSheet.getWidth()/frame_columns, animatedSheet.getHeight()/frame_rows);
        animatedFrames = new TextureRegion[frame_columns * frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_columns; j++) {
                animatedFrames[index++] = tmp[i][j];
            }
        }
        return animatedFrames;
	}
}
