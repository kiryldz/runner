package com.boontaran.games.supermario.panel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.boontaran.games.supermario.SuperMario;

public class ScoreCounter extends Group {
	private Label label;
	
	public ScoreCounter() {
		setTransform(false);
		
		//the label
		LabelStyle style = new LabelStyle();
		style.font = SuperMario.font1;
		style.fontColor = new Color(0xffffffff);
		label = new Label("", style);
		label.setWidth(260);
		setScore(0);
		
		addActor(label);
		setSize(label.getWidth(), label.getHeight());
	}
	
	//set text
	public void setScore(int score) {
		label.setText("Score : "+score);
	}
}
