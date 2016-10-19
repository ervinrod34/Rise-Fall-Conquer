package MainMenu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Navigator;

import CustomWidgets.GameBar;
import factions.Faction;
import factions.PlayerFaction;
import factions.ScoreBoard;
import map.DayNightCycle;
import map.MiniMap;
import map.Tile;
import tools.BasicAnimationID;

public class GameScreen implements Screen{
	SpriteBatch batch;
	SpriteBatch batchMiniMap;
	private map.Map mBoard;
	private map.MiniMap miniMap;
	private OrthographicCamera oGameCam;
	private Navigator nav;
	private Stage stage;
	private ArrayList<Faction> factions;
    private int totalTurns;
    private ScoreBoard scoreBoard;
    
    
	private GameBar bar;
	private DayNightCycle dnCycle;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 12;
	
	public GameScreen(){
		//set up stage and table
		stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		//set camera position
		oGameCam = new OrthographicCamera(1280, 720);
		mBoard = new map.Map(oGameCam, "Map_2.json");
		this.generateFactions();
		oGameCam.position.set(factions.get(0).getHomeTile().getLocation().x,factions.get(0).getHomeTile().getLocation().y,0);
		oGameCam.update();
		
		//initialize minimap, passing the map
		miniMap = new MiniMap(mBoard);
	
		//set up input processors, arrow keys, mouse click
		nav = new Navigator(oGameCam, batch, stage, mBoard);
		InputMultiplexer ipm = new InputMultiplexer();
		ipm.addProcessor(nav);
		ipm.addProcessor(stage);
		Gdx.input.setInputProcessor(ipm);

		//Day night cycle that updates every .1 seconds
		dnCycle = new DayNightCycle(mBoard);
		while(dnCycle.getTime() % 81 != 0){
			dnCycle.update();
		}
		// Set total turns to 0
		totalTurns = 0;
		
		bar = new GameBar();
		this.stage.addActor(bar.getTopBar());
		
		//Setup all click listeners for top bar
		bar.setOptionsClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
		bar.setEndTurnClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				for(Faction f : factions){
					f.updateResources();
				}
				final Timer time = new Timer();
				time.scheduleTask(new Task(){
					@Override
					public void run(){
						// Stop transition after 4 hours
						if(dnCycle.getTime() % 40 == 0){
							time.stop();
						}
						// Update time
						dnCycle.update();
					}
					}
				,0f,.1f);
				// Increment total turns
				bar.setTurns(++totalTurns);
			}
		});
		bar.setFoodClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Food Button", "Clicked");
			}
		});
		bar.setWoodClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Wood Button", "Clicked");
			}
		});
		bar.setGoldClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Gold Button", "Clicked");
			}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		if(bar != null){
			bar.setTime12Format(dnCycle.getTime()/10);
			Faction player = this.factions.get(0);
			bar.setFood(player.getTotalFood(), player.getFoodPerTurn());
			bar.setWood(player.getTotalWood(), player.getWoodPerTurn());
			bar.setGold(player.getTotalGold(), player.getGoldPerTurn());
		}
		nav.inputHandle(delta);
		//draw map on GameScreen
		Gdx.gl.glClearColor(36/255f, 97/255f, 123/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		//Draw Main Map
		batch.begin();
		mBoard.drawView(batch, oGameCam);
		for(Faction fac : factions){
			fac.drawTerritory(batch);
		}
		batch.end();
		
		mBoard.drawMapLighting(oGameCam);
		
		//draw minimap on top of map
		miniMap.MiniMapRender();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width,height,true);
		bar.update();
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
	 * as well as the Score Board
	 */
	public void generateFactions(){
		ScoreBoard scoreBoard = new ScoreBoard();
		factions = new ArrayList<Faction>();
		Tile home = mBoard.getRandomHomeTile();
		int FactId = 1;
		
		while(home != null){
			Faction faction = null;
			if(FactId == 1){
				//Set the first faction to a player owned one
				faction = new PlayerFaction(FactId, home, mBoard, Color.BLUE);
				//add a score entry for the player faction
				scoreBoard.addScore(faction.getScore());
			}else{
				//Set the other factions to normal
				faction = new PlayerFaction(FactId, home, mBoard, Color.RED);
				//add a score entry for a nonplayer faction
				scoreBoard.addScore(faction.getScore());
			}
			//Add a animation to the home tile
			home.setbAnimation(BasicAnimationID.PARTICLE_SLEEP);
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