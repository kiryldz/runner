package com.boontaran.games.supermario;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.platformerLib.Entity;

public class Brick extends Entity {
	//it's just a rectangle entity with "brick" image
	public Brick(Rectangle rect) {
		super();
		setSize(rect.width, rect.height);
		Image img = new Image(SuperMario.atlas.findRegion("brick"));
		setImage(img);
	}

}
