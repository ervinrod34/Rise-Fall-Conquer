package MainMenu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Navigator;

import factions.Faction;
import factions.PlayerFaction;
import map.DayNightCycle;
import map.Tile;
import tools.BasicAnimation;
import tools.BasicAnimationID;

public class GameScreen implements Screen{
	SpriteBatch batch;
	private map.Map mBoard;
	private OrthographicCamera oGameCam;
	private Navigator nav;
	private Stage stage;
	private ArrayList<Faction> factions;
    
	
	public GameScreen()
	{
		//set up stage and table
		stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		//set camera position
		oGameCam = new OrthographicCamera(1280, 720);
		mBoard = new map.Map(oGameCam);
		this.generateFactions();
		oGameCam.position.set(factions.get(0).getHomeTile().getLocation().x,factions.get(0).getHomeTile().getLocation().y,0);
		oGameCam.update();
		
		//set up input processors, arrow keys, mouse click
		nav = new Navigator(oGameCam, batch, stage);
		InputMultiplexer ipm = new InputMultiplexer();
		ipm.addProcessor(nav);
		ipm.addProcessor(stage);
		Gdx.input.setInputProcessor(ipm);
		
		//TODO remove timer and update after round over
		//Day night cycle that updates every .1 seconds
		final DayNightCycle dnCycle = new DayNightCycle(mBoard);
		Timer time = new Timer();
		time.scheduleTask(new Task(){
			@Override
			public void run(){
				dnCycle.update();
			}
			}
		,0f,.1f);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		nav.inputHandle(delta);
		//draw map on GameScreen
		Gdx.gl.glClearColor(36/255f, 97/255f, 123/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//mBoard.drawMap(batch);
		mBoard.drawView(batch, oGameCam);
		batch.end();
		stage.draw();
		mBoard.drawMapLighting(oGameCam);
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
		mBoard.disposeMap();
		batch.dispose();
	}
	/**
	 * Creates the factions for the game
	 */
	public void generateFactions(){
		factions = new ArrayList<Faction>();
		Tile home = mBoard.getRandomHomeTile();
		int FactId = 1;
		while(home != null){
			Faction faction = null;
			if(FactId == 1){
				//Set the first faction to a player owned one
				faction = new PlayerFaction(FactId, home);
			}else{
				//Set the other factions to normal
				faction = new Faction(FactId, home);
			}
			//Add a animation to the home tile
			home.setbAnimation(BasicAnimationID.PARTICLE_MIST);
			factions.add(faction);
			home = mBoard.getRandomHomeTile();
			FactId++;
		}
		//There must be at least one home city on the map
		if(factions.size() == 0){
			try {
				throw new Exception("Must have at least 2 home cities");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}