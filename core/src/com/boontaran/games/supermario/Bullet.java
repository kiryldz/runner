package com.boontaran.games.supermario;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.MessageEvent;
import com.boontaran.games.platformerLib.Entity;

public class Bullet extends Entity {
	//events
	public static final int REMOVE = 1;
	public static final int REMOVE_NO_EXP = 2;
	
	private float speed;
	//damage to take if hit an enemy
	private float damage = 5;
	
	public Bullet() {
		// set the image
		Image img = new Image(SuperMario.atlas.findRegion("bullet"));
		setImage(img);
		setRadius(25);
		
		edgeUpdateLimRatio = 0.1f;
		
		//full bounce if hit ground
		restitution = 1; 
		airFriction = 0;
		friction = 0;
	}
	
	//launch by assigning speed based on direction
	public void launch(boolean toRight) {
		setV(0,0);
		if(toRight) {
			speed = 450;
		} else {
			speed = -450;
		}
		setVX(speed);
	}
	

	@Override
	public void hitWall(Entity ent) {
		//hit a wall, fire an event to remove this bullet 
		fire(new MessageEvent(REMOVE));
	}
	
	//get the damage of this bullet
	public float getDamage() {
		return damage;
	}
	
	//remove the bullet silently, because not in screen anymore
	@Override
	public void onSkipUpdate() {
		fire(new MessageEvent(REMOVE_NO_EXP));
	}
	

}
