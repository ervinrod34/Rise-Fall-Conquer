package MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Navigator;

public class GameScreen implements Screen{
	private Game game;
	SpriteBatch batch;
	private map.Map mBoard;
	private OrthographicCamera oGameCam;
	private Navigator nav;
	private Stage stage;
	private Skin skin;
	private Table gameTable;
	
	public GameScreen(Game g)
	{
		game=g;
		skin = new Skin(Gdx.files.internal("TableAssets/uiskin.json"));
		//create menu button
		TextButton menu = new TextButton("MENU", skin);
		menu.setWidth(100);
		menu.setHeight(100);
		
		//set up stage and table
		stage = new Stage(new ScreenViewport());
	    gameTable = new Table(skin);
		gameTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameTable.setFillParent(true);
		gameTable.top();
		menu.setPosition(0,0);
		gameTable.add(menu);
		gameTable.row();
		stage.addActor(gameTable);

		batch = new SpriteBatch();
		mBoard = new map.Map();
		
		//set camera position
		oGameCam = new OrthographicCamera(1280, 720);
		oGameCam.position.set(oGameCam.viewportWidth /2,oGameCam.viewportHeight /2,0);
		oGameCam.update();
		
		//set up input processors, arrow keys, mouse click
		nav = new Navigator(oGameCam, batch);
		InputMultiplexer ipm = new InputMultiplexer();
		ipm.addProcessor(nav);
		ipm.addProcessor(stage);
		Gdx.input.setInputProcessor(ipm);
		
		//click listener for menu button, more to come
		menu.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenu(game));
			}
		});
		
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