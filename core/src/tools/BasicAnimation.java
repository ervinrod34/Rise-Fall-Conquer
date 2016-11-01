package tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic animations for tiles
 * 
 * @author Porter
 *
 */
public class BasicAnimation{
	
    private Animation animation;
    private ParticleEffect pEffect;
    private TextureRegion currentFrame;
    private float stateTime; 
    private Vector2 Location;
    
	public BasicAnimation(BasicAnimationID id, float x, float y){
		if(id.getAnimatedSheet() != null){
			this.load32bitAnimation(id);
		}else{
			pEffect = new ParticleEffect();
			pEffect.load(id.getEffectFile(), id.getImgFile());
			pEffect.start();
		}
		Location = new Vector2(x, y);
	}
	
	/**
	 * Makes a 32 bit animated texture from given tile id
	 * @param id
	 */
	private void load32bitAnimation(BasicAnimationID id){
		Texture animatedSheet = id.getAnimatedSheet();
	    TextureRegion[] animatedFrames;
		int frame_columns = animatedSheet.getWidth()/32;
	    int frame_rows = 1;
        TextureRegion[][] tmp = TextureRegion.split(animatedSheet, animatedSheet.getWidth()/frame_columns, animatedSheet.getHeight()/frame_rows);
        animatedFrames = new TextureRegion[frame_columns * frame_rows];
        int index = 0;
        for (int i = 0; i < frame_rows; i++) {
            for (int j = 0; j < frame_columns; j++) {
                animatedFrames[index++] = tmp[i][j];
            }
        }
        //animation = new Animation(0.075f, animatedFrames);
        animation = new Animation(0.075f, animatedFrames);
        stateTime = 0f;
	}
	/**
	 * Draws an animation
	 * @param batch
	 */
	public void draw(SpriteBatch batch){
		if (pEffect == null) {
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = animation.getKeyFrame(stateTime, true);
			batch.draw(currentFrame, Location.x, Location.y);
		}else{
			pEffect.setPosition(Location.x, Location.y);
			pEffect.update(Gdx.graphics.getDeltaTime());
			pEffect.draw(batch);
			if(pEffect.isComplete()){
				pEffect.reset();
			}
		}
	}
}
