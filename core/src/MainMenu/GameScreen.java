package MainMenu;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Navigator;

import CustomWidgets.GameBar;
import CustomWidgets.ProgressOption;
import factions.Bar;
import factions.Faction;
import factions.PlayerFaction;
import factions.ScoreBoard;
import factions.Unit;
import factions.UnitID;
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
    private ProgressOption progressOpt;
    //private String winDisplay;
    
	private GameBar bar;
	private DayNightCycle dnCycle;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 12;
	private ShapeRenderer rend;
	
	public GameScreen(String mapName){
	

		//set up stage and table
		stage = new Stage(new ScreenViewport());
		batch = new SpriteBatch();
		rend = new ShapeRenderer();
		
		//set camera position, resolution
		if(MyGdxGame.RESOLUTION == 0.0) {
			oGameCam = new OrthographicCamera(1024, 576); //low
		} else if(MyGdxGame.RESOLUTION == 1.0) {
			oGameCam = new OrthographicCamera(1280, 720); //normal
		} else {
			oGameCam = new OrthographicCamera(1600, 900); //high
		}
		
		mBoard = new map.Map(oGameCam, mapName);

		this.generateFactions();
		oGameCam.position.set(factions.get(0).getHomeTile().getLocation().x,factions.get(0).getHomeTile().getLocation().y,0);
		oGameCam.update();
		
		
		//scoreBoard.printScoreBoard();
		
		//initialize minimap, passing the map
		miniMap = new MiniMap(mBoard, factions);
	
		//set up input processors, arrow keys, mouse click
		nav = new Navigator(oGameCam, batch, stage, mBoard, factions);
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
		
		bar.setProgressClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				progressOpt = new ProgressOption(factions);
				progressOpt.getMainTable().setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				progressOpt.getMainTable().setFillParent(true);
				progressOpt.getMainTable().center();
				stage.addActor(progressOpt.getMainTable());
				progressOpt.setStage(stage);
				progressOpt.setIsOpen(true);
			}
		});
		
		//Setup all click listeners for top bar
		bar.setOptionsClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
		
		final GameScreen tempScreen = this;
		bar.setEndTurnClickListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				// Check if player's turn is over before proceeding
				if(factions.get(0).isTurnOver() == true){
					return;
				}
				// Set player's turn to being over
				factions.get(0).setTurnOver(true);
				for(Faction f : factions){
					// Move all AI
					if(f != factions.get(0)){
						f.AI(factions);
					}
					f.updateTotalResources();
					for(Unit u : f.getUnits()){
						u.update(mBoard);
					}
					f.updateTotalScore();
					//System.out.println(f.getScore().toString());
				}
				bar.setScore(factions.get(0).getScore().getScoreVal());
				//bar.setWin(tempScreen.calculateWin()); //Display the current city counts, will be changed to win%
				
				final Timer time = new Timer();
				time.scheduleTask(new Task(){
					@Override
					public void run(){
						// Stop transition after 4 hours
						if(dnCycle.getTime() % 40 == 0){
							// Make it so player can end there turn again
							factions.get(0).setTurnOver(false);
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
		
		//Check for unit deaths
		ArrayList<Unit> dead = new ArrayList<Unit>();
		for(Faction fac : factions){
			for(Unit u : fac.getUnits()){
				if(u.getHealth() == 0){
					dead.add(u);
				}
			}
		}
		// Remove dead units
		for(Unit u : dead){
			for(Faction fac : factions){
				if(fac.getUnits().contains(u) == true){
					fac.getUnits().remove(u);
				}
			}
		}
		// Remove dead resources
		for(Faction fac : factions){
			ArrayList<Tile> deadResources = new ArrayList<Tile>();
			for(Tile t : fac.getClaimedTiles()){
				if(t.getResource() != null){
					if(t.getResource().getHealth() <= 0){
						t.setResource(null);
						t.setbAnimation(null);
						deadResources.add(t);
					}
				}
			}
			for(Tile t : deadResources){
				fac.unclaimTile(t);
			}
		}
		
		//draw map on GameScreen
		Gdx.gl.glClearColor(36/255f, 97/255f, 123/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		//Draw Main Map
		batch.begin();
		mBoard.drawView(batch, oGameCam);
		for(Faction fac : factions){
			fac.drawTerritory(batch);
			fac.drawUnits(batch);
		}
		batch.end();
		
		rend.setProjectionMatrix(oGameCam.combined);
		// Draws all non textures
		rend.setAutoShapeType(true);
		rend.begin(ShapeType.Filled);
		// Draw tile health bars
		this.mBoard.drawHealthbars(rend);
		// Draw unit shapes
		for (Faction fac : factions) {
			fac.drawUnits(rend);
		}
		rend.end();
				
		if(MyGdxGame.LIGHTING == true){
			mBoard.drawMapLighting(oGameCam);
		}
		
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
		scoreBoard = new ScoreBoard();
		factions = new ArrayList<Faction>();
		Tile home = mBoard.getRandomHomeTile();
		int FactId = 1;
		
		while(home != null){
			Faction faction = null;
			if(FactId == 1){
				//Set the first faction to a player owned one
				faction = new PlayerFaction(FactId, home, mBoard, Color.BLUE, mBoard.getrayHandler());
				//add a score entry for the player faction
				scoreBoard.addScore(faction.getScore());
				// Give player starting resources
				faction.setTotalGold(100);
			}else{
				//Set the other factions to normal
				faction = new Faction(FactId, home, mBoard, Color.RED, mBoard.getrayHandler());
				// Add an enemy at there home tiles
				faction.addUnit(UnitID.Basic, faction.getHomeTile());
				//add a score entry for a non-player faction
				scoreBoard.addScore(faction.getScore());
			}
			//Update all resources per turn
			faction.updateResourcesPerTurn();
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
	
	/**
	 * TO DO
	 */
	public String calculateWin() {
		int playerCityCount = 0;
		int otherCityCount = 0;
		String strCityCounts = "";
		
		playerCityCount = factions.get(0).getCityCount();
		for(int i = 1; i < factions.size(); i++) {
			otherCityCount += factions.get(i).getCityCount();
		}
		
		//System.out.println("Player City Count: " + playerCityCount + "\nOther: " + otherCityCount);
		strCityCounts = "Player: " + playerCityCount + "  Other: " + otherCityCount;
		
		return strCityCounts.toString();
	}
	
	public ArrayList<Faction> getFactions() {
		return this.factions;
	}
}