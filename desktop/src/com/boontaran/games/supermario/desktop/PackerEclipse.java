package com.boontaran.games.supermario.desktop;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

//this is an utility class to pack the images into a large pack image
public class PackerEclipse {

	public static void main(String[] args) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.filterMin = TextureFilter.MipMapLinearNearest;
		settings.filterMag = TextureFilter.Linear;
		settings.paddingX = 4;
		settings.paddingY = 4;
		settings.maxHeight = 2048;
		settings.maxWidth = 2048;

		TexturePacker.process(settings, "raw_images", "../android/assets/images", "pack");
	}

}
