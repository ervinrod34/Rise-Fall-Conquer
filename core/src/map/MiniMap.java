package map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MiniMap{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 12;
	
	
	private SpriteBatch batchMiniMap;
	private ShapeRenderer shr;
	private OrthographicCamera miniCam;
	private Map mBoard;
	private Color playerColor;
	
		public MiniMap(Map mBoard) {
		this.mBoard = mBoard;
		batchMiniMap = new SpriteBatch();
		shr = new ShapeRenderer();
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
		
		//Draw the Markers
		shr.setProjectionMatrix(miniCam.combined);
		shr.setColor(Color.RED);
		shr.begin(ShapeType.Filled);
		//shr.setColor(Color.RED);
		mBoard.drawMarkers(shr);
		shr.end();
	}
	
	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}
}
