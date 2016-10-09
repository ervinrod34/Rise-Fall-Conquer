package com.mygdx.game;

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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import MainMenu.MainMenu;

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

	public Navigator(OrthographicCamera oGameCam, SpriteBatch batch, Stage stage) {
		zoomValue = 0.06;
		lastTouch = new Vector3();
		this.batch = new SpriteBatch();
		this.batch = batch;

		this.oGameCam = new OrthographicCamera();
		this.oGameCam = oGameCam;

		this.stage = stage;
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
		if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
			lastTouch.set(screenX, screenY,0);
			return true;
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
		if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
		    
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
