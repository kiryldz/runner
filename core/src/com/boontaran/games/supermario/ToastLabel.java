package com.boontaran.games.supermario;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.boontaran.MessageEvent;


public class ToastLabel extends Group {
	public static final int REMOVE = 1;
	
	private Label label;
	private LabelStyle style;
	private float time=-1;
	
	public ToastLabel() {
		//create the label
		style = new LabelStyle();
		style.font = SuperMario.font1;
		style.fontColor = new Color(0xfeed8eff);
		
		label = new Label("", style);
		addActor(label);
	}
	public void init(String text , float time) {
		
		//set text and position
		label.setText(text);
		label.pack();
		
		label.setX(-label.getWidth()/2);
		
		//time length the label will appear
		if(time == 0) time = 0.3f;
		this.time = time;
		
	}
	@Override
	public void act(float delta) {
		//move up
		if(time > 0) {
			time -= delta;
			moveBy(0, delta*100);
			
			//has completed
			if(time <= 0) {
				time = -1;
				fire(new MessageEvent(REMOVE));
			}
		}
		super.act(delta);
	}
	

}
