package com.mygdx.game;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

import MainMenu.MainMenu;
import test.suite.TestSuiteRunner;

public class MyGdxGame extends Game {
	
	public static Game GAME;
	public static Skin MENUSKIN;
	public static final String ASSET_PATH = "asset" + File.separator;
	
	@Override
	public void create () {
		TestSuiteRunner.runTests();
		GAME = this;
		String path = MyGdxGame.ASSET_PATH + "TableAssets" + File.separator + "uiskin.json";
		try{
			MENUSKIN = new Skin(Gdx.files.internal(path));
		}catch(GdxRuntimeException e){
			Gdx.app.error(this.getClass().getName(), "Could not load texture: " + Gdx.files.internal(path).file().getAbsolutePath());
		}
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
