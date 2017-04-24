package com.boontaran.games.supermario.control;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.games.supermario.SuperMario;

public class JoyStick extends Group {
	
	private Image idle,right,left; // iamges of 3 states
	
	//the 3 states
	private static final int IDLE = 0;
	private static final int RIGHT = 1;
	private static final int LEFT = 2;
	private int direction;
	
	
	
	public JoyStick(float minHeight) {
		//idle image
		idle = new Image(SuperMario.atlas.findRegion("joystick"));
		addActor(idle);
		
		float scale = 1;
		
		//resize if less than min size
		if(idle.getHeight() < minHeight) {
			scale = minHeight/idle.getHeight();
		}	
		idle.setHeight(idle.getHeight()*scale);
		idle.setWidth(idle.getWidth()*scale);
		
		setSize(idle.getWidth(), idle.getHeight());
		
		//right & left state image
		//also match the size with the idle image
		right = new Image(SuperMario.atlas.findRegion("joystick_right"));
		right.setSize(getWidth(), getHeight());
		addActor(right);
		
		left = new Image(SuperMario.atlas.findRegion("joystick_left"));
		left.setSize(getWidth(), getHeight());
		addActor(left);
		
		setDir(IDLE);
		
		
		//listen to the changes
		addListener(new ClickListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
				//pass the touch location
				handleTouch(x,y);
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,int pointer) {
				handleTouch(x,y);
				super.touchDragged(event, x, y, pointer);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,int pointer, int button) {
				setDir(IDLE);
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
	}
	
	//get the state
	public boolean isRight() {
		return direction == RIGHT;
	}
	public boolean isLeft() {
		return direction == LEFT;
	}
	
	
	//set state based on touch location
	private void handleTouch(float x,float y) {
		if(x > getWidth()/2) {
			setDir(RIGHT);
		} else {
			setDir(LEFT);
		}
	}
	
	//set display based on state
	private void setDir(int dir) {
		idle.setVisible(false);
		right.setVisible(false);
		left.setVisible(false);
		
		if(dir==IDLE) idle.setVisible(true);
		if(dir==RIGHT) right.setVisible(true);
		if(dir==LEFT) left.setVisible(true);
		
		direction = dir;
		
	}

}
