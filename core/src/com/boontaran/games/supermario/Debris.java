package com.boontaran.games.supermario;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.boontaran.MessageEvent;
import com.boontaran.games.platformerLib.Entity;

//this is the debris of brick when destroyed by hero
public class Debris extends Entity implements Poolable {
	public static int ON_FINISHED = 1;
	
	//images that randomly to be chosen 
	private Image d1,d2,d3,d4;
	//time to be removed
	private float time;
	
	public Debris() {
		d1 = new Image(SuperMario.atlas.findRegion("brick_d1"));
		d2 = new Image(SuperMario.atlas.findRegion("brick_d2"));
		d3 = new Image(SuperMario.atlas.findRegion("brick_d3"));
		d4 = new Image(SuperMario.atlas.findRegion("brick_d4"));
		setSize(3, 3);
		
		airFriction = 0;
		aFriction = 0.2f;
		
		reset();
		
		edgeUpdateLimRatio = 2;
	}
	
	//start throwing the debris
	public void start() {
		time = (float) (1.5f + Math.random()*1.5f);
		setV(0, 0);
		setVX((float) (Math.random()*200 - 100));
		setVY((float) (Math.random()*100));
		setASpeed((float) (Math.random()*720 - 360));
	}

	//setting the random debris image 
	@Override
	public void reset() {
		int r = (int) (Math.random()*4);
		if(r==0) {
			setImage(d1);
		}
		else if(r==1) {
			setImage(d2);
		}
		else if(r==2) {
			setImage(d3);
		}
		else if(r==3) {
			setImage(d4);
		}
		
	}
	@Override
	public void update(float delta) {
		super.update(delta);
		
		//if time's up, remove it
		if(time > 0) {
			time -= delta;
			if(time <=0) {
				fire(new MessageEvent(ON_FINISHED));
			}
		}
		
		
	}
	
}
