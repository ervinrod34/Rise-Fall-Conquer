package tools;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.MyGdxGame;
/**
 * ID's used to select an animation
 * 
 * @author Porter
 *
 */
public enum BasicAnimationID {
	INFO_POINT("info" + File.separator + "!.png"),
	PARTICLE_SPARKS("sparks" + File.separator + "Spark1", "sparks" + File.separator),
	PARTICLE_SLEEP("sleep" + File.separator + "Sleep", "sleep" + File.separator),
	PARTICLE_MIST("clouds" + File.separator + "Cloud", "clouds" + File.separator);

	private Texture animatedSheet;
	private FileHandle effectFile, imgFile;
	
	/**
	 * Used for basic animations
	 * @param filepath
	 */
	BasicAnimationID(String filepath) {
		String path = MyGdxGame.ASSET_PATH + "images" + File.separator + "tiles" + File.separator + "animated"
				+ File.separator + filepath;
		try {
			animatedSheet = new Texture(Gdx.files.internal(path));
		} catch (GdxRuntimeException e) {
			Gdx.app.error(this.getClass().getName(),
					"Could not load texture: " + Gdx.files.internal(path).file().getAbsolutePath());
		}
	}

	/**
	 * Used for particle animations
	 * @param effectname
	 * @param imagename
	 */
	BasicAnimationID(String effectname, String imagename) {
		String effectpath = MyGdxGame.ASSET_PATH + "images" + File.separator + "tiles" + File.separator + "animated"
				+ File.separator + effectname;
		String imagepath = MyGdxGame.ASSET_PATH + "images" + File.separator + "tiles" + File.separator + "animated"
				+ File.separator + imagename;
		try {
			effectFile = Gdx.files.internal(effectpath);
		} catch (GdxRuntimeException e) {
			Gdx.app.error(this.getClass().getName(),
					"Could not load file: " + Gdx.files.internal(effectpath).file().getAbsolutePath());
		}
		try {
			imgFile = Gdx.files.internal(imagepath);
		} catch (GdxRuntimeException e) {
			Gdx.app.error(this.getClass().getName(),
					"Could not load file: " + Gdx.files.internal(imagepath).file().getAbsolutePath());
		}
	}
	
	public Texture getAnimatedSheet() {
		return animatedSheet;
	}

	public FileHandle getEffectFile() {
		return effectFile;
	}

	public FileHandle getImgFile() {
		return imgFile;
	}
	
}
