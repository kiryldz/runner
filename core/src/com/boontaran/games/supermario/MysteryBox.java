package com.boontaran.games.supermario;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.MessageListener;
import com.boontaran.games.platformerLib.Entity;

public class MysteryBox extends Entity {
	public static final int EXPIRED = 1;
	
	//the contents, it should only one of these :
	private int numCoin, numBullets;
	
	
	private Level level;
	
	private Image img,imgHit,imgInactive;
	private float hitTime;
	private boolean hasExpired;
	
	
	public MysteryBox(Level level,Rectangle rect) {
		this.level = level;
		setSize(rect.width, rect.height);
		
		// 3 image states of the box
		img = new Image(SuperMario.atlas.findRegion("mystery_box"));
		imgHit = new Image(SuperMario.atlas.findRegion("mystery_box_hit"));
		imgInactive = new Image(SuperMario.atlas.findRegion("mystery_box_inactive"));
		
		
		setImage(img);
		
		//floating
		noGravity = true;
	}
	
	//set the num of coin
	public void setCoin(int num) {
		numCoin = num;
	}
	//set bullets
	public void setBullet(int numBullets) {
		this.numBullets = numBullets;
	}
	
	//when hero hit the box
	public void hit() {
		if(hasExpired) return;
		SuperMario.media.playSound("coin");
		
		//if coin inside
		if(numCoin > 0) {
			level.toastCoin(this);
			hitTime = 0.1f;
			setImage(imgHit);
			numCoin--;
			
			if(numCoin==0) {
				setExpired();
			}
			return;
		}
		
		//if bullets inside
		if(numBullets>0) {
			
			//put bullet stock above the box
			BulletStock stock = new BulletStock(numBullets);
			stock.setX(getX() + (getWidth() - stock.getWidth())/2);
			stock.setY(getTop() + stock.getHeight()/2);
			stock.setFloating(false);
			level.addEntity(stock);
			
			//little bounce
			stock.setVY(70);
			setExpired();
			
			return;
		}
		
		
		
	}
	
	//box has expired, turn into a metal block
	private void setExpired() {
		setImage(imgInactive);
		hasExpired = true;
	}
	@Override
	public void update(float delta) {
		super.update(delta);
		
		//prevent multi hit at a time, check the min elapsed time between hits
		if(hitTime > 0) {
			hitTime -= delta;
			if(hitTime <=0) {
				setImage(img);
				if(hasExpired) {
					setImage(imgInactive);
					
				}
			}
		}
	}

	
	

}
