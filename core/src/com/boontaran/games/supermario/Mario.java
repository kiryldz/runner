package com.boontaran.games.supermario;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.MessageEvent;
import com.boontaran.games.Clip;
import com.boontaran.games.platformerLib.Entity;
import com.boontaran.games.supermario.enemies.Enemy;
import com.boontaran.games.supermario.enemies.Enemy2;

public class Mario extends Entity {
	
	//events
	public static final int HERO_DIE = 1;
	
	private float ax = 3000;
	
	//the states
	private int state=-1;
	private static final int IDLE=0;
	private static final int WALK=1;
	private static final int JUMP_UP=2;
	private static final int JUMP_DOWN=3;
	private static final int ATTACKED_BY_ENEMY=4;
	private static final int FIRE=5;
	private static final int DIE=6;
	
	//hero animation
	private Clip clip ;
	
	
	//hero's full health
	private float fullHealth = 3;
	private float health = fullHealth;
	
	//damage to take if hero attack enemy
	private float damage = 1;
	
	//time when hero in star power
	private float starTime = 10; //sec
	private float starTimer;
	
	//star hilite blinking
	private float hiliteAlpha=1;
	private boolean hiliteUp=false;
	
	//indicate the hero is just attacked
	private float justAttackedTime;
	//hero has died, hero has completed the mission
	private boolean hasDied,hasCompleted;
	
	
	//state that the fire key has released, to prevent multiple bullet firing at a time
	private boolean fireKeyHasUp;
	
	//frames
	private int idleFrames[] =new int[]{0,0};
	private int walkFrames[] =new int[]{4,5,6,7,6,5};
	private int fireFrames[] =new int[]{1,1,1};
	private int fireInAirFrames[] =new int[]{3,3,3};
	
	//if true, the state will be maintained until animation completed
	private boolean waitingOnComplete;
	
	//reference to the Level class
	private Level level;
	
	
	public Mario(Level level) {
		this.level = level;
		
		//construct the clip, and clip listener
		clip = new Clip(SuperMario.atlas.findRegion("mario") , 120,120);
		setSize(50	, 95);
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
		
		//no bouncing
		restitution = 0;
		
		//walk speed 
		maxSpeedX = Settings.WALK_SPEED;
		changeState(IDLE);
	}
	
	//notified the keys being pressed 
	public void onKey(boolean left , boolean right, boolean jump,boolean fire) {
		if(hasDied || hasCompleted) return;
		
		a.x = 0;
		friction = 0.5f;
				
		boolean inAir = isInAir();
		
		
		//unable to move when the hero is just attacked
		if(justAttackedTime <= 0) {
			
			//set the acceleration and state based on direction
			if(left) {
				a.x  = -ax;
				friction = 0;
				
				if(!inAir) {
					changeState(WALK);
				} 
			}
			if(right) {
				a.x = ax;
				friction = 0;
				
				if(!inAir) {
					changeState(WALK);
				} 
			}
			
			if(!right && !left && inAir) {
				v.x /=1.1f;
			}
			//fire a bullet
			if(fire && fireKeyHasUp) {
				fireBullet();
				fireKeyHasUp = false;
			}
		}
		
		
		if(!fire) {
			fireKeyHasUp = true;
		}
		
		//state if in air
		if(inAir) {
			if(v.y < 0)
				changeState(JUMP_DOWN);
			else
				changeState(JUMP_UP);
		} else {
			if(!right && !left) {
				changeState(IDLE);
			}	
		}
		
		//hero jump
		if(jump) {
			
			//do if hero touch ground only
			if(!isInAir()) {
				jump();
			}
		}
		
		//flip the display if moving left	
		if(v.x > 0) {
			setScaleX(1);
		} else if(v.x < 0) {
			setScaleX(-1);
		}
		
	}
	
	//jumping
	private void jump() {
		//speed to jump
		setVY(Settings.JUMP_SPEED);
		
		//sound
		SuperMario.media.playSound("jump");
	}
	private void fireBullet() {
		if(level.getBulletCounts() == 0) return; //not have a bullet
		
		//firing the bullet
		level.heroFireBullet(getScaleX()==1);
		changeState(FIRE);
		
		SuperMario.media.playSound("bullet");
	}
	

	
	@Override
	protected void hitCeil(Entity ent) {
		super.hitCeil(ent);
		
		if(ent instanceof Brick) {	
			//hit a brick
			level.heroHitBrick((Brick) ent);
		}
		else if(ent instanceof MysteryBox) {	
			//hit a mystery box
			level.heroHitMystery((MysteryBox) ent);
		}
	}
	@Override
	public void hitLand(Entity land) {
		super.hitLand(land);
		
		if(justAttackedTime > 0) {
			justAttackedTime = 0.01f;
		}
	}
	
	
	//is still in attacked time, hero can't be attacked by enemy twice
	public boolean isImmune() {
		return justAttackedTime > 0;
	}
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(hasCompleted) {
			if(!isInAir()) {
				a.x = ax;
				changeState(WALK);
			}
		}
		
		//star hilite animation (alpha value), based on elapsed time
		if(starTimer > 0) {
			starTimer -= delta;
			
			if(hiliteUp) {
				hiliteAlpha += delta*5;
				if(hiliteAlpha > 1) {
					hiliteAlpha = 1;
					hiliteUp = false;
				}
			} else {
				hiliteAlpha -= delta*5;
				
				if(hiliteAlpha < 0)  {
					hiliteAlpha = 0;
					hiliteUp = true;
				}
			}
			
			
			if(starTimer <= 0) {
				setImage(null);
				
				SuperMario.media.playMusic("level");
				SuperMario.media.stopMusic("star");
			}
		}
		
		
		if(justAttackedTime > 0) {
			justAttackedTime -= delta;
			
			
			if(justAttackedTime <= 0) {
				
			}
		}
	}
	private void changeState(int newState) {
		changeState(newState, false);
	}	
	
	//changing the state
	private void changeState(int newState,boolean force) {
		if(state == newState) return; // already in that state
		if(justAttackedTime > 0) return; //still attacked
		
		
		if(waitingOnComplete && !force) {  //waiitng to complete pref animation
			return;
		} else {
			waitingOnComplete = false;  //it forced to change
		}
		
		
		if(hasDied) return;
		
		state = newState;
		
		//change the clip animation based on state
		switch (state) {
		case IDLE:
			clip.playFrames(idleFrames, true);
			break;
		case WALK:
			clip.playFrames(walkFrames, true);
			break;
		case JUMP_UP:
			clip.singleFrame(2);
			break;
		case JUMP_DOWN:
			clip.singleFrame(2);
			break;
		case ATTACKED_BY_ENEMY:
			clip.singleFrame(8);
			break;		
		case DIE:
			clip.singleFrame(9);
			break;
		case FIRE:
			if(isInAir()) {
				clip.playFrames(fireInAirFrames, false);
			} else {
				clip.playFrames(fireFrames, false);
			}
			waitingOnComplete = true;
			break;
		default:
			break;
		}
	}
	
	
	public float getDamage() {
		return damage;
	}
	
	//hero step an enemy, bounce up
	public void stepEnemy(Enemy enemy) {
		setY(enemy.getTop() + getHeight()/2);
		setVY(300); //bounce up
	}
	
	//hero attacked by enemy
	public void attackedBy(Enemy enemy) {
		//check if enemy type is Enemy2 (turtle enemy)
		//if he is hiding, do nothing with hero
		if(enemy instanceof Enemy2) {
			Enemy2 enemy2 = (Enemy2) enemy;
			
			if(enemy2.isSliding()) {
				
			}
			else if(enemy2.isHiding()) {
				return;
			}
		}
		
		//reduce health
		health -= enemy.getDamage();
		
		if(health <=0) {
			//out of health, hero will die
			if(v.x > 0) {
				setScaleX(1);
			} else if(v.x < 0) {
				setScaleX(-1);
			}
			
			die();
		} else {
			//still has health
			changeState(ATTACKED_BY_ENEMY,true);
			justAttackedTime = 2;  //within 2 sec they can't attack hero again
			if(getX() > enemy.getX()) {
				setVX(350);
			} else {
				setVX(-350);
			}
		}
		
		//move up
		setVY(350);
	}
	
	
	
	public boolean isDied() {
		return hasDied;
	}
	
	//hero died, 
	private void die() {
		
		a.x = 0;
		setVX(0);
		
		//change state
		changeState(DIE,true);
		hasDied = true;
		setNoCollision(true);
		setNoLandCollision(true);
		
		//notify the Level by firing an event
		fire(new MessageEvent(HERO_DIE));
	}
	
	//falling to a cliff
	public void fall() {
		if(hasDied) return;
		
		die();
		setVY(0);
	}
	
	
	public float getHealthRatio() {
		return health/fullHealth;
	}
	public void gameCompleted() {
		hasCompleted=true;		
	}
	public void justBeatBoss() {
		a.x = 0;
	}
	
	
}
