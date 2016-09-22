package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	//Texture img;
	private map.Map mBoard;
	private OrthographicCamera oGameCam;
	private Navigator nav;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		mBoard = new map.Map();
		oGameCam = new OrthographicCamera(1280, 720);
		nav = new Navigator(oGameCam, batch);
		Gdx.input.setInputProcessor(nav);
		oGameCam.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(90/255f, 128/255f, 44/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		mBoard.drawMap(batch);
		//batch.draw(img, 0, 0);
		batch.end();
		nav.inputHandle();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		mBoard.disposeMap();
		//img.dispose();
	}
	
	/**
	 * Handles player input
	 */

}
