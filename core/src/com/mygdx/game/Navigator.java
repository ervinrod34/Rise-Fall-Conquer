package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import CustomWidgets.TileOptions;
import MainMenu.MainMenu;
import factions.PlayerFaction;
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
	
	private boolean isOpen;
	private PlayerFaction pf;
	
	//Testing path movement
	private Tile StartTile;
	private ArrayList<Tile> MovementList;
	private int MaxMovementRange = 5;
	
	public Navigator(OrthographicCamera oGameCam, SpriteBatch batch, Stage stage, Map map, PlayerFaction pf) {
		this.pf = pf;
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
					t.setLight(true);
					StartTile = t;
					MovementList.add(t);
					break;
				}
				if(t.containsPoint(mPos) == true && MovementList.contains(t) == true){
					//System.out.println("Size: " + Line.size() + " Loc: " + Line.indexOf(t));
					if (MovementList.size() > 1 && MovementList.get(MovementList.size() - 2) == t) {
						StartTile.setLight(false);
						MovementList.remove(StartTile);
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
			if(op!=null && op.getIsOpen()==false){
				isOpen = false;
			}
			if(isOpen == false){
				Tile t = m.getClickedTile2(mPos);
				op = new TileOptions(t, this.pf);
				stage.addActor(op.gettOptions());
				op.setIsOpen(true);
				isOpen=true;
			}
		}
		//Testing path movement
		if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)){
			if(StartTile != null){
				for(Tile t : MovementList){
					t.setLight(false);
				}
				MovementList.clear();
				StartTile = null;
			}else{
				StartTile = m.getClickedTile2(mPos);
				if(StartTile != null){
					StartTile.setLight(true);
					MovementList.add(StartTile);
				}
			}
		}
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
