package com.boontaran.games.supermario.enemies;

import com.boontaran.games.Clip;
import com.boontaran.games.platformerLib.Entity;
import com.boontaran.games.supermario.Bullet;
import com.boontaran.games.supermario.Mario;
import com.boontaran.games.supermario.SuperMario;

public class Enemy1 extends Enemy {
	//states
	protected static final int WALK = 1;
	private static final int JUMP = 2;
	private static final int ATTACKED = 3;
	private static final int ATTACK_HERO = 4;
	private static final int HIT_BY_BULLET = 5;
	private static final int DIE = 6;
	
	//
	protected boolean isMoveRight;
	protected float speed = 100;  //px per sec
	protected int state = -1;
	
	//enemy frames
	protected int walkFrames[] = new int[]{0,1,2,3,4,5,6,7,8,9};
	protected int attackedFrames[] = new int[]{11,11,11};
	protected int attackHeroFrames[] = new int[]{10,10,10,10};
	protected int hitByBulletFrames[] = new int[]{11,11,11,11};
	protected int dieFrame = 12;
	//
	
	private float waitTime; //wait time after attacked by bullet
	
	//
	protected boolean waitingOnComplete;
	
	protected Clip clip;
	
	public Enemy1() {
		//animation clip
		clip = new Clip(SuperMario.atlas.findRegion("enemy1") , 100,120);
		setSize(60,50);
				
		setClip(clip);
		clip.setFPS(12);
		
		//listen on every animation completed
		clip.addListener(new Clip.ClipListener() {
			
			@Override
			public void onFrame(int num) {}
			
			@Override
			public void onComplete() {
				waitingOnComplete = false;
			}
		});
	}
	
	

	@Override
	public void update(float delta) {
		
		if(!hasDied) {
			if(waitTime <= 0) {
				//walk
				if(!isInAir()) {
					chState(WALK);
					
					//set speed & flip based on direction
					if(isMoveRight) {
						setScaleX(1);
						setVX(speed);
					} else {
						setScaleX(-1);
						setVX(-speed);
					}
					
				} else {
					//in air
					chState(JUMP);
				}
			}
			//consume the wait time
			if(waitTime > 0) {
				waitTime -= delta;
			}
		
		}
		
		super.update(delta);
	}
	
	//flip the display & direction
	public void flip() {
	
		if(isMoveRight) {
			isMoveRight = false;
			setScaleX(-1);
			
		} else {
			isMoveRight = true;
			setScaleX(1);
			
		}
	}	
	
	///flip when hitting wall
	@Override
	public void hitWall(Entity ent) {
		flip();
	}
	
	
	@Override
	public void attackedByHero(Mario hero,float damageMultiplier) {
		super.attackedByHero(hero,damageMultiplier);
		
		//change state or die if lack of health
		if(health > 0) {
			chState(ATTACKED);
		} else {
			die();
		}
	}
	
	@Override
	public void attackedBy(Bullet bullet) {
		super.attackedBy(bullet);
		
		if(health > 0) {
			//change state 
			waitTime = 0.4f;
			chState(HIT_BY_BULLET);
			
			//change direction based on where the bullet came from
			if(bullet.getX() < getX()) {
				isMoveRight = false;
				setScaleX(-1);
			} else {
				isMoveRight = true;
				setScaleX(1);
			}
		} else {
			//die
			setVY(200);
			die();
		}
	}
	
	//getting attack by the turtle enemy
	public void attackedBy(Enemy2 slidingEnemy) {
		super.attackedBy(slidingEnemy);
		
		chState(DIE);
		die();
		setVY(200);
	}
	
	//attack hero
	@Override
	public void attackHero(Mario hero) {
		chState(ATTACK_HERO);
		
		if(hero.getX() > getX()) {
			isMoveRight = true;
		} else {
			isMoveRight = false;		
		}
	}

	//die
	@Override
	protected void die() {
		chState(DIE);
		super.die();
	}


	protected void chState(int newstate) {
		chState(newstate,false);
	}
	
	//change clip state
	protected void chState(int newstate,boolean forced) {
		if(state == newstate) return; //already at the state
		
		//wating for prev animation completed
		if(waitingOnComplete)  {
			if(forced) {  //except forced
				waitingOnComplete = false;
			} else {
				return;
			}
		}
		
		int oldState = state;
		state = newstate;
			
		//assign the clip frames based on state
		switch (state) {
		case WALK:
			clip.playFrames(walkFrames, true);
			break;
		case JUMP:
			clip.singleFrame(0);
			break;
		case ATTACKED:
			clip.playFrames(attackedFrames, false);
			waitingOnComplete = true;
			break;
		case ATTACK_HERO:
			clip.playFrames(attackHeroFrames, false);
			waitingOnComplete = true;
			break;
		case HIT_BY_BULLET:
			clip.playFrames(hitByBulletFrames, false);
			waitingOnComplete = true;
			break;
		case DIE:
			clip.singleFrame(dieFrame);
			break;
		default:
			state = oldState;
			break;
		}
		
		
	}

}
