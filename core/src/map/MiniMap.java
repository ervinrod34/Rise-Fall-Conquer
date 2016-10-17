package map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MiniMap{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 12;
	
	
	private SpriteBatch batchMiniMap;
	private OrthographicCamera miniCam;
	private Map mBoard;
	
	
	public MiniMap(Map mBoard) {
		this.mBoard = mBoard;
		batchMiniMap = new SpriteBatch();
		miniCam = new OrthographicCamera(WIDTH, HEIGHT);
		miniCam.zoom = SCALE;
		miniCam.update();
	}
	

	public void MiniMapRender() {
		//Draw the Mini Map
		batchMiniMap.setProjectionMatrix(miniCam.combined);
		batchMiniMap.begin();
		mBoard.drawMap(batchMiniMap);
		batchMiniMap.end();
		
		//mBoard.drawMapLighting(miniCam);
	}
}
