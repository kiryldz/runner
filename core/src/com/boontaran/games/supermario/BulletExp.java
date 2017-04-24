package com.boontaran.games.supermario;

import com.boontaran.MessageEvent;
import com.boontaran.games.Clip;
import com.boontaran.games.platformerLib.Entity;

public class BulletExp extends Entity {
	//event to indicate the animation has complete
	public static final int COMPELTED = 1;
	private Clip clip;
	
	public BulletExp() {
		noGravity = true;
		setNoCollision(true);
		setNoLandCollision(true);
				
		clip = new Clip(SuperMario.atlas.findRegion("bullet_exp") , 40 , 40);
		setClip(clip);
		
		//listen to the clip
		clip.addListener(new Clip.ClipListener() {
			
			@Override
			public void onFrame(int num) {}
			
			
			//when animation finished, fire an completed event
			@Override
			public void onComplete() {
				fire(new MessageEvent(COMPELTED));
			}
		});
	}
	
	//start animation
	public void start() {
		clip.playFrames(0,2, false);
	}

}
