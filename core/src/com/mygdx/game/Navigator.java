package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import CustomWidgets.HomeTileOption;
import CustomWidgets.TileOptions;
import CustomWidgets.UnitOption;
import MainMenu.MainMenu;
import factions.Faction;
import factions.PlayerFaction;
import factions.Unit;
import map.Map;
import map.Tile;

public class Navigator implements InputProcessor {
	//set the maximum zoom in and out value here
	private static double MAX_ZOOM = 0.20;
	private static double MAX_ZOOM_OUT = 1;
	
	private double zoomValue;	//how much or how quickly to zoom
	private Vector3 lastTouch;	//holds pos. of last button press

	private OrthographicCamera oGameCam;
	private SpriteBatch batch;

	private Stage stage;
	private Table gameTable;

	private Map m;
	
	private TileOptions op;
	private HomeTileOption hp;
	private UnitOption unitOptions;
	
	private boolean isOpen;
	private ArrayList<Faction> factions;
	
	//Testing path movement
	private Tile StartTile;
	private ArrayList<Tile> MovementList;
	private ArrayList<Integer> MovementListLights;
	private int MaxMovementRange = 5;
	
	public Navigator(OrthographicCamera oGameCam, SpriteBatch batch, Stage stage, Map map, ArrayList<Faction> factionList) {
		this.factions = factionList;
		isOpen = false;
		zoomValue = 0.06;
		lastTouch = new Vector3();
		this.batch = new SpriteBatch();
		this.batch = batch;

		this.oGameCam = new OrthographicCamera();
		this.oGameCam = oGameCam;

		this.stage = stage;
		this.m = map;
		
		MovementList = new ArrayList<Tile>();
		MovementListLights = new ArrayList<Integer>();
		
		//Create Main Menu button
		TextButton menu = new TextButton("Main Menu", MyGdxGame.MENUSKIN);

		//Set up stage and table
		gameTable = new Table(MyGdxGame.MENUSKIN);
		gameTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameTable.setFillParent(true);
		gameTable.center();
		gameTable.add(menu).width(150).height(50);
		
		//Click listener for Main Menu button, more to come
		menu.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				MyGdxGame.GAME.setScreen(new MainMenu());
			}
		});
	}

	public void inputHandle(float delta) {
		float iCamSpeed = 300 * delta;
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		Vector3 mousePos = new Vector3(mouseX, mouseY, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (mousePos.x >= (Gdx.graphics.getWidth() - 15))) {
			oGameCam.translate(iCamSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (mousePos.x <= 15)) {
			oGameCam.translate(-iCamSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)|| (mousePos.y <= 15)) {
			oGameCam.translate(0, iCamSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)|| (mousePos.y >= (Gdx.graphics.getHeight() - 15))) {
			oGameCam.translate(0, -iCamSpeed);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			boolean showTable = true;
			for (Actor actor : stage.getActors()) {
				if (actor.equals(gameTable)) {
					actor.remove();
					showTable = false;
				}
			}
			if (showTable == true) {
				stage.addActor(gameTable);
			}
		}
		
        oGameCam.position.x = MathUtils.clamp(oGameCam.position.x
        		, 0, m.getmWidth() );
        oGameCam.position.y = MathUtils.clamp(oGameCam.position.y
        		, 0, m.getmWidth()-200);
		
		//Testing path movement
		if(StartTile != null){
			Vector3 vect = oGameCam.unproject(new Vector3(mouseX,mouseY,0));
			Vector2 mPos = new Vector2(vect.x,vect.y);
			//Tile t = m.getClickedTile2(mPos);
			for(Tile t : m.getNeighborTiles(StartTile)){
				if(t.containsPoint(mPos) == true && MovementList.contains(t) == false && (MovementList.size()-1) < MaxMovementRange){
					if(t.isLightOn() == true){
						MovementListLights.add(1);
					}else{
						MovementListLights.add(0);
					}
					t.setLight(true);
					StartTile = t;
					MovementList.add(t);
					break;
				}
				if(t.containsPoint(mPos) == true && MovementList.contains(t) == true){
					//System.out.println("Size: " + Line.size() + " Loc: " + Line.indexOf(t));
					if (MovementList.size() > 1 && MovementList.get(MovementList.size() - 2) == t) {
						Integer light = null;
						for(int j = 0; j < MovementList.size(); j++){
							if(MovementList.get(j).equals(StartTile) == true){
								light = MovementListLights.get(j);
								break;
							}
						}
						if(light.intValue() == 0){
							StartTile.setLight(false);
						}
						MovementList.remove(StartTile);
						MovementListLights.remove(light);
						StartTile = t;
						break;
					}
				}
			}
//			for(Tile t : m.getTilesInRange(StartTile, 2, null)){
//				t.setLight(true);
//			}
		}
		//batch.setProjectionMatrix(oGameCam.combined);
		oGameCam.update();
		batch.setProjectionMatrix(oGameCam.combined);
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//last position button clicked down
		Vector3 vect = oGameCam.unproject(new Vector3(screenX,screenY,0));
		Vector2 mPos = new Vector2(vect.x,vect.y);
		
		//right click tiles, display options for tile
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			//Check to close unit dialog box
			if(unitOptions!=null && unitOptions.getIsOpen()==false){
				isOpen = false;
			}
			//Check for collision with player units
			for (Unit u : factions.get(0).getUnits()) {
				if(u.getLocation().containsPoint(mPos) == true){
					// Reset the last unit movement
					if(unitOptions != null && unitOptions.getIsMoving() == true){
						unitOptions.getUnit().displayMovementRange();
						unitOptions.setIsMoving(false);
					}
					unitOptions = new UnitOption(u);
					stage.addActor(unitOptions.gettOptions());
					unitOptions.setIsOpen(true);
					isOpen=true;
					return false;
				}
			}
			
			// Tile info pop ups
			if(op!=null && op.getIsOpen()==false){
				isOpen = false;
			}
			
			if(hp!=null && hp.getIsOpen()==false){
				isOpen = false;
			}
			if(isOpen == false){
				Tile t = m.getClickedTile2(mPos);
				if (factions.get(0).getBuildRange().contains(t) == true) {
					try {
						if (t.getResourceID().name().equals("HOME")) {
							hp = new HomeTileOption(t, this.factions);
							stage.addActor(hp.gettOptions());
							hp.setIsOpen(true);
							isOpen = true;
						} else {
							op = new TileOptions(t, (PlayerFaction) this.factions.get(0));
							stage.addActor(op.gettOptions());
							op.setIsOpen(true);
							isOpen = true;
						}
					} catch (NullPointerException e) {
						op = new TileOptions(t, (PlayerFaction) this.factions.get(0));
						stage.addActor(op.gettOptions());
						op.setIsOpen(true);
						isOpen = true;
					}
				}
			}
		}
		//Testing path movement
		if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)){
			if(StartTile != null){
				for(int i = 0; i < MovementList.size(); i++){
					if(MovementListLights.get(i) == 0){
						MovementList.get(i).setLight(false);
					}
				}
				MovementList.clear();
				MovementListLights.clear();
				StartTile = null;
			}else{
				StartTile = m.getClickedTile2(mPos);
				if(StartTile != null){
					if(StartTile.isLightOn() == true){
						MovementListLights.add(1);
					}else{
						MovementListLights.add(0);
					}
					StartTile.setLight(true);
					MovementList.add(StartTile);
				}
			}
		}
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			// If the unit is being moved
			if(unitOptions != null && unitOptions.getIsMoving() == true){
				Tile tile = m.getClickedTile2(mPos);
				if(unitOptions.getUnit().getMovementRange() != null && unitOptions.getUnit().getMovementRange().contains(tile) == true){
					//Check for collision with player units
					boolean collision = false;
					for (Unit u : factions.get(0).getUnits()) {
						if(u.getLocation().equals(tile) == true){
							collision = true;
							break;
						}
					}
					//Set location if no collision with other units
					if(collision == false){
						unitOptions.getUnit().setLocation(tile, m);
						factions.get(0).calculateBuildRange();
					}
					unitOptions.getUnit().displayMovementRange();
					unitOptions.setIsMoving(false);
				}else{
					unitOptions.getUnit().displayMovementRange();
					unitOptions.setIsMoving(false);
				}
			}
			lastTouch.set(screenX, screenY,0);
			//return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//check if middle mouse press (for navigation)
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
		    
			//get the latest mouse position
			Vector3 newTouch = new Vector3(screenX, screenY,0);
			
			//calculate difference between last touch and
			//most recent touch
		    Vector3 delta = newTouch.cpy().sub(lastTouch);
		    
		    //move camera to new position
		    oGameCam.translate(-delta.x, delta.y);
		    
		    //set new lastTouch
		    lastTouch = newTouch;
		    return true;
		}

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// 1 is scroll down
		// -1 is scroll up
		if (amount == 1) {
			zoomIn();
			return true;
		}

		if (amount == -1) {
			zoomOut();
			return true;
		}
		return false;
	}

	/*
	 * Zoom out the camera
	 */
	public void zoomOut() {
		if(oGameCam.zoom <= MAX_ZOOM_OUT)
			oGameCam.zoom += zoomValue;
	}

	/*
	 * Zoom in the camera
	 */
	public void zoomIn() {
		if (oGameCam.zoom >= MAX_ZOOM)
			oGameCam.zoom -= zoomValue;
	}

	/*
	 * returns the zoom value
	 */
	public double getZoomValue() {
		return zoomValue;
	}

	/*
	 * sets the zoom value
	 */
	public void setZoomValue(float zoomValue) {
		this.zoomValue = zoomValue;
	}
}
