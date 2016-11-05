package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import tools.UnitMaker;
/**
 * Launcher for creating sprites from sheet
 * @author Porter
 *
 */
public class ToolLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new UnitMaker(), config);
	}

}
