package com.boontaran.games.supermario.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.boontaran.games.supermario.SuperMario;

public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg  =new LwjglApplicationConfiguration();

		//size of game
		cfg.width = 960;
		cfg.height = 640;

		float scale = 1;
		//scaling factor
		scale = 0.6f;

		cfg.width = (int) (scale*960);
		cfg.height = (int) (scale*640);

		new LwjglApplication(new SuperMario(),cfg);
	}
}
