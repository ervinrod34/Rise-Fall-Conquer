package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import MainMenu.MainMenu;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	private map.Map mBoard;
	private Game game;
	@Override
	public void create () {
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
		mBoard.disposeMap();
	}
	
	/**
	 * Handles player input
	 */
	
}
