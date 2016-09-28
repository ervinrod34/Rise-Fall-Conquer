package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import MainMenu.MainMenu;
import test.suite.TestSuiteRunner;

public class MyGdxGame extends Game {
	
	public static Game GAME;
	public static Skin MENUSKIN;
	
	@Override
	public void create () {
		TestSuiteRunner.runTests();
		GAME = this;
		MENUSKIN = new Skin(Gdx.files.internal("TableAssets/uiskin.json"));
		setScreen(new MainMenu());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
	
	/**
	 * Handles player input
	 */
	
}
