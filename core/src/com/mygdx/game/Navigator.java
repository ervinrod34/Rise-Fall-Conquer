package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Navigator implements InputProcessor{
	private static double MAX_ZOOM = 0.20;
	
	private double zoomValue;
	private OrthographicCamera oGameCam;
	private SpriteBatch batch;

	public Navigator(OrthographicCamera oGameCam, SpriteBatch batch){
		zoomValue = 0.02;
		this.batch = new SpriteBatch();
		this.batch = batch;
		
		this.oGameCam = new OrthographicCamera();
		this.oGameCam = oGameCam;
	}
	
	public void inputHandle(){
		int iCamSpeed = 5;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			oGameCam.translate(iCamSpeed, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			oGameCam.translate(-iCamSpeed, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			oGameCam.translate(0, iCamSpeed);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			oGameCam.translate(0, -iCamSpeed);
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
		//1 is down
		//-1 is ups
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
	
	public void zoomOut () {
		
			oGameCam.zoom += zoomValue;
	}
	
	public void zoomIn () {
		if(oGameCam.zoom >= MAX_ZOOM)
		oGameCam.zoom -= zoomValue;
	}
	
	public double getZoomValue() {
		return zoomValue;
	}

	public void setZoomValue(double zoomValue) {
		this.zoomValue = zoomValue;
	}
}


