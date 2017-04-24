package com.boontaran.games.supermario.enemies;

import com.boontaran.games.Clip;
import com.boontaran.games.supermario.Mario;
import com.boontaran.games.supermario.SuperMario;

//THIS is the turtle enemy
public class Enemy2 extends Enemy1 {
	//hiding
	private static final int HIDE = 10;
	//states	
	private boolean hiding,sliding;
	
	
	public Enemy2() {
		setSize(60, 35);
		
		//images of turtle
		clip = new Clip(SuperMario.atlas.findRegion("enemy2") , 90,72);
		setClip(clip);
		clip.setFPS(12);
		clip.addListener(new Clip.ClipListener() {
			
			@Override
			public void onFrame(int num) {}
			
			@Override
			public void onComplete() {
				waitingOnComplete = false;
			}
		});
		
		//frames of this enemy type
		walkFrames = new int[]{0,1,2,3,2,1};
		attackedFrames  = new int[]{2,2,2,2};
		attackHeroFrames  = new int[]{2,2,2};
		hitByBulletFrames = new int[]{0,0,0,0};
		dieFrame = 5;
		
		score = 200;
		speed = 125;
	}
	
	@Override
	public void attackHero(Mario hero) {
		//if the enemy hit hero
				
		if(hiding && !sliding) {
			//it should attack hero but in this case when turtle is hiding, the enemy won't attack hero
			//instead it will start sliding
			
			setSliding(hero);
		}
	}
	
	
	@Override
	public void attackedByHero(Mario hero,float damageMultiplier) {
		if(hero.getDamage() * damageMultiplier > 20) {
			
			setVY(200);
			die();
			
			return;
		}
		
		
		//if sliding, stop sliding
		if(sliding) {
			stopSliding();
			return;
		}
		
		//if not hiding, hide
		if(!hiding) {
			hide();
			return;
		} else {
			//slide this
			setSliding(hero);
		}
	}
	private void stopSliding() {
		sliding = false;
		speed = 0;
	}
	
	//slide right or left, based on hero position
	private void setSliding(Mario hero) {
		sliding = true;
		speed = 400;
		if(getX() > hero.getX()) {
			isMoveRight = true;
			moveBy(4, 0);
		} else {
			isMoveRight = false;
			moveBy(-4, 0);
		}
	}
	
	
	@Override
	public void flip() {
		//System.out.println("flip");
		super.flip();
	}

	private void hide() {
		chState(HIDE);
		hiding = true;
		speed = 0;
	}
	public boolean isHiding() {
		return hiding;
	}
	
	//change the clip state
	protected void chState(int newstate) {
		
		
		if(state == newstate) return;
		if(waitingOnComplete) return;
		if(hiding && !hasDied) return;
		
		super.chState(newstate);
		state = newstate;
		
		switch (state) {
		
		case HIDE:
			clip.singleFrame(4);
			break;
			
		default:	
			break;
		}
		
	}
	public boolean isSliding() {
		return sliding;
	}

}
