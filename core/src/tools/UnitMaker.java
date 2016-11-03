package tools;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.MyGdxGame;

/**
 * Generates Animated Sprites from a sprite sheet
 * 
 * @author Porter
 *
 */
public class UnitMaker extends Game{
	
	private ArrayList<TextureRegion> tList1;
	private ArrayList<TextureRegion> tList2;
	private SpriteBatch batch;
	private static int spriteCount = 0;
	@Override
	public void create() {
		batch = new SpriteBatch();
		// Load sprite sheet for first animation frame
		tList1 = loadSpriteSheet("Player0.png");
		// Load sprite sheet for second animation frame
		tList2 = loadSpriteSheet("Player1.png");
		// Generate the img files from the sprite sheet
		drawUnits("GenUnits", "Unit");
		tList1 = loadSpriteSheet("Reptile0.png");
		tList2 = loadSpriteSheet("Reptile1.png");
		drawUnits("GenReptiles", "Reptile");
		tList1 = loadSpriteSheet("Undead0.png");
		tList2 = loadSpriteSheet("Undead1.png");
		drawUnits("GenUndead", "Undead");
	}
	
	@Override
	public void render() {
		// draw map on GameScreen
		Gdx.gl.glClearColor(36 / 255f, 97 / 255f, 123 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		int x = 64;
		int y = 100;
		for(int i = 0; i < tList2.size(); i++){
			batch.draw(tList2.get(i), x, y);
			x += 16;
			if(i % 8 == 0){
				y += 16;
				x = 64;
			}
		}
		batch.end();
	}
	
	/**
	 * Draws textures and saves them to a file
	 * @param dirName
	 * @param baseName
	 */
	private void drawUnits(String dirName, String baseName){
		if(tList1.size() != tList2.size()){
			System.out.println("Not same amount of textures!!");
			return;
		}
		for (int i = 0; i < tList1.size(); i++) {
			Pixmap fP = new Pixmap(110, 64, Format.RGBA8888);
			Pixmap tex1 = regionToPixmap(tList1.get(i));
			fP.drawPixmap(tex1, 10, 15);
			fP.drawPixmap(tex1, 27, 12);
			fP.drawPixmap(tex1, 27, 31);
			fP.drawPixmap(tex1, 12, 36);
			tex1.dispose();
			Pixmap tex2 = regionToPixmap(tList2.get(i));
			fP.drawPixmap(tex2, 65, 16);
			fP.drawPixmap(tex2, 82, 13);
			fP.drawPixmap(tex2, 82, 32);
			fP.drawPixmap(tex2, 67, 37);
			tex2.dispose();
			PixmapIO.writePNG(Gdx.files.local(MyGdxGame.ASSET_PATH + "images" + File.separator + "units"
					+ File.separator + dirName + File.separator + baseName + spriteCount + ".png"), fP);
			spriteCount++;
			fP.dispose();
		}
	}
	/**
	 * Changes texture region to a pixmap
	 * @param t
	 * @return
	 */
	private Pixmap regionToPixmap(TextureRegion t){
		t.getTexture().getTextureData().prepare();
		Pixmap tex1 = t.getTexture().getTextureData().consumePixmap();
		Pixmap pixmap = new Pixmap(t.getRegionWidth(), t.getRegionHeight(), Format.RGBA8888);
		for(int x = 0; x < pixmap.getWidth(); x++){
			for(int y = 0; y < pixmap.getHeight(); y++){
				// Copy texture region pixel color to new pixmap
				pixmap.drawPixel(x, y, tex1.getPixel(x + t.getRegionX(), y + t.getRegionY()));
			}
		}
		tex1.dispose();
		return pixmap;
	}
	/**
	 * Loads the sprite sheet given
	 * @param filePath
	 * @return
	 */
	private static ArrayList<TextureRegion> loadSpriteSheet(String filePath){
		ArrayList<TextureRegion> tList = new ArrayList<TextureRegion>();
		Texture img = null;
		String path = MyGdxGame.ASSET_PATH + "images" + File.separator + "units" + File.separator + filePath;
		try{
			img = new Texture(path);
		}catch(GdxRuntimeException e){
			Gdx.app.error("UnitMaker", "Could not load texture: " + Gdx.files.internal(path).file().getAbsolutePath());
		}
		TextureRegion [] tR = load16bitTextures(img);
		for(TextureRegion t : tR){
			if(isTextureEmpty(t) == false){
				tList.add(t);
			}
		}
		return tList;
	}
	/**
	 * Loads 16 bit sprite sheet
	 * @param animatedSheet
	 * @return
	 */
	private static TextureRegion[] load16bitTextures(Texture animatedSheet){
	    TextureRegion[] animatedFrames;
		int frame_columns = animatedSheet.getWidth()/16;
	    int frame_rows = animatedSheet.getHeight()/16;
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

	/**
	 * Checks if texture is empty
	 * @param t
	 * @return
	 */
	private static boolean isTextureEmpty(TextureRegion t){
		t.getTexture().getTextureData().prepare();
		Pixmap p = t.getTexture().getTextureData().consumePixmap();
		int epx = 0;
		for(int x = t.getRegionX(); x < t.getRegionX() + t.getRegionWidth(); x++){
			for(int y = t.getRegionY(); y < t.getRegionY() + t.getRegionHeight(); y++){
				if(p.getPixel(x, y) == 0){
					epx++;
				}
				if(epx >= t.getRegionHeight() * t.getRegionWidth()){
					return true;
				}
			}
		}
		p.dispose();
		return false;
	}
}
