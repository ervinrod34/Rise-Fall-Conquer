package MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Navigator;

import map.Tile;

public class GameScreen implements Screen{
	SpriteBatch batch;
	private map.Map mBoard;
	private OrthographicCamera oGameCam;
	private Navigator nav;
	private Stage stage;
	//private Table gameTable;
	
	public GameScreen()
	{
		//set up stage and table
		stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		mBoard = new map.Map();
		
		//set camera position
		oGameCam = new OrthographicCamera(1280, 720);
		Tile home = mBoard.getRandomHomeTile();
		oGameCam.position.set(home.getLocation().x,home.getLocation().y,0);
		oGameCam.update();
		
		//set up input processors, arrow keys, mouse click
		nav = new Navigator(oGameCam, batch, stage);
		InputMultiplexer ipm = new InputMultiplexer();
		ipm.addProcessor(nav);
		ipm.addProcessor(stage);
		Gdx.input.setInputProcessor(ipm);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		//draw map on GameScreen
		Gdx.gl.glClearColor(90/255f, 128/255f, 44/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		mBoard.drawMap(batch);
		batch.end();
		nav.inputHandle();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width,height,true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}