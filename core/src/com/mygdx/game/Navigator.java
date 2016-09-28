package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import MainMenu.MainMenu;

public class Navigator implements InputProcessor {
	private static double MAX_ZOOM = 0.20;

	private double zoomValue;
	private OrthographicCamera oGameCam;
	private SpriteBatch batch;

	private Stage stage;
	private Table gameTable;

	public Navigator(OrthographicCamera oGameCam, SpriteBatch batch, Stage stage) {
		zoomValue = 0.02;
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

	public void inputHandle() {
		int iCamSpeed = 5;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			oGameCam.translate(iCamSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			oGameCam.translate(-iCamSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			oGameCam.translate(0, iCamSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
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
		batch.setProjectionMatrix(oGameCam.combined);
		oGameCam.update();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		// 1 is down
		// -1 is ups
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

	public void zoomOut() {

		oGameCam.zoom += zoomValue;
	}

	public void zoomIn() {
		if (oGameCam.zoom >= MAX_ZOOM)
			oGameCam.zoom -= zoomValue;
	}

	public double getZoomValue() {
		return zoomValue;
	}

	public void setZoomValue(double zoomValue) {
		this.zoomValue = zoomValue;
	}
}
