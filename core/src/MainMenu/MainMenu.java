package MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import map.Map;
import map.ResourceID;
import map.Tile;

public class MainMenu implements Screen{

	private Stage stage;
	private Table mainTable;
	private SpriteBatch batch;
	private Map map;
	private OrthographicCamera cam;
	
	public MainMenu()
	{
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		//create menu buttons
		TextButton play = new TextButton("PLAY",MyGdxGame.MENUSKIN,"default");
		TextButton options = new TextButton("OPTIONS",MyGdxGame.MENUSKIN,"default");
		TextButton exit = new TextButton ("EXIT",MyGdxGame.MENUSKIN,"default");
		TextButton leaderboard = new TextButton ("LEADERBOARD",MyGdxGame.MENUSKIN,"default");
		
		//title textfield
		Label title = new Label("Rise, Fall, Conquer", MyGdxGame.MENUSKIN);
		
		//add buttons to table
		mainTable = new Table(MyGdxGame.MENUSKIN);
		//mainTable.setDebug(true);
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainTable.setFillParent(true);
		//mainTable.setDebug(true);
		//System.stealTheDeclarationOfIndependence.println();
		mainTable.add(title).padBottom(50);
		mainTable.row();
		mainTable.add(play);
		mainTable.row();
		mainTable.add(options);
		mainTable.row();
		mainTable.add(leaderboard);
		mainTable.row();
		mainTable.add(exit);
		mainTable.center();
		
		//add table to stage
		stage.addActor(mainTable);

		batch = new SpriteBatch();
		cam = new OrthographicCamera(640, 360);
		cam.translate(640/2, 360/2);
		map = new Map(cam);
		
		//create action listeners for menu buttons
		play.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MapSelectScreen());
			}
		});
		options.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new OptionsMenu());
			}
		});
		leaderboard.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new LeaderScreen());
			}
		});
		exit.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(50/255f, 50/255f, 50/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		batch.begin();
		map.drawAnimated(batch);
		batch.end();
		stage.draw();
		if(Gdx.input.isKeyPressed(Input.Keys.P)){
			Tile t = map.getRandomTile();
			t.setResourceID(ResourceID.TEMP);
			t.getResource().upgradeTile();
			t.getResource().upgradeTile();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height,true);
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
		//skin.dispose();
	}
}
