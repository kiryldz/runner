package com.boontaran.games.supermario;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.MessageEvent;
import com.boontaran.games.platformerLib.Entity;

public class Flag extends Entity {
	public static final int RAISED = 1;
	
	//height of flag pole
	private float height = 300;
	//container of pole and flag
	private Group group;
	//
	private Image flag;
	
	// the raising time
	private float downTime;
	private boolean raised;
	
	public Flag() {
		group = new Group();
		noGravity = true;
		setNoLandCollision(true);
		
		//create pole and set position
		NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("pole"),7,7,4,4);
		Image pole = new Image(patch);
		pole.setHeight(height);
		
		setSize(pole.getWidth(), height);
		pole.setX(-getWidth()/2);
		pole.setY(-getHeight()/2);
		
		//create flag and positioning
		flag = new Image(SuperMario.atlas.findRegion("flag"));
		group.addActor(flag);
		group.addActor(pole);
		
		flag.setY(pole.getY() + pole.getHeight() - flag.getHeight());
		flag.setX(pole.getX() + 10);
		
		//accessories of pole
		Image top = new Image(SuperMario.atlas.findRegion("pole_top"));
		group.addActor(top);
		top.setX(pole.getX() + (pole.getWidth() - top.getWidth())/2);
		top.setY(pole.getTop()-2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		
		//draw the container (pole and flag inside it)
		if(!skipDraw) {
			group.setPosition(getX(), getY());
			group.draw(batch, parentAlpha);
		}
		
	}
	

	@Override
	public void update(float delta) {
		//when the raising is finished, fire an event 
		if(downTime > 0) {
			downTime -= delta;
			if(downTime <=0 ) {
				fire(new MessageEvent(RAISED));
			}
		}
		super.update(delta);
	}

	@Override
	public void act(float delta) {
		group.act(delta);
		super.act(delta);
	}

	//raise the flag
	public void down() {
		raised = true;
		//flag.addAction(Actions.moveBy(0, height-flag.getHeight(), 0.7f));
		flag.addAction(Actions.moveBy(0, -(height-flag.getHeight()), 0.7f));
		downTime = 2f;
	}
	public boolean hasRaised() {
		return raised;
	}

}
