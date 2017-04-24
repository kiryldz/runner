package com.boontaran.games.supermario.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.boontaran.games.supermario.SuperMario;

public class BulletCounter extends Group {
	private Label label;
	
	public BulletCounter() {
		setTransform(false);
		//bullet image
		Image bullet = new Image(SuperMario.atlas.findRegion("bullet_icon"));
		addActor(bullet);
		
		//the label showing how many bullets in stock
		LabelStyle style = new LabelStyle();
		style.font = SuperMario.font1;
		style.fontColor = new Color(0xffffffff);
		label = new Label("555", style);
		setSize(label.getRight() + 30 , 30);
		bullet.setY((getHeight()-bullet.getHeight())/2);
		label.setY((getHeight()-label.getHeight())/2);
		
		addActor(label);
		label.setX(30);
		label.setText("0");
		
	}
	
	//set the label text
	public void setCount(int count) {
		label.setText(String.valueOf(count));
	}

}
