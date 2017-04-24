package com.boontaran.games.supermario;

import com.boontaran.MessageEvent;
import com.boontaran.games.Clip;
import com.boontaran.games.platformerLib.Entity;

public class Coin extends Entity {
	public static final int REQUEST_REMOVE= 1;
	
	//score of coin
	private int score = 10;
	//track the time when it throwed, when time's up, remove this coin
	private float throwTime;
	
	private Clip clip;
	
	//whether a reference coin or not
	//reference coin used to synchronize all coins animation
	private boolean isReference;
	
	public Coin() {
		clip = new Clip(SuperMario.atlas.findRegion("coin") , 36 , 36);
		setRadius(16);
		clip.setFPS(12);
		setClip(clip);
	}
	public void setAsRefference() {
		//if it's a reference set the animation frames
		clip.playFrames(new int[]{0,1,2,3,4,5}, true);
		noGravity = true;
		setNoCollision(true);
		setNoLandCollision(true);
		
	}
	
	//set if coin floating in air
	public void setFloat() {
		noGravity = true;
		setV(0, 0);
	}
	
	//throw the coin
	public void throwUp() {
		noGravity = false;
		setVY(200);
		setVX(0);
		
		//the throwing time, is sec
		throwTime = 0.6f;
	}
	@Override
	public void update(float delta) {
		
		//remove it when throwing time has up
		if(throwTime > 0) {
			throwTime -= delta;
			if(throwTime <=0) {
				fire(new MessageEvent(REQUEST_REMOVE));
			}
		}
		
		//if it's a regular coin (not a reference) sync the frame with the reference
		if(!isReference) {
			clip.singleFrame(Level.coin.getClip().getCurFrameId());
		}
		
		super.update(delta);
	}
	private Clip getClip() {
		return clip;
	}
	public int getScore() {
		return score;
	}
	

}
