package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import MainMenu.MainMenu;
//import test.suite.TestSuiteRunner;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	private Game game;
	@Override
	public void create () {
		//TestSuiteRunner.runTests();
		game = this;
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	/**
	 * Handles player input
	 */
	
}
