package com.boontaran.games.supermario;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.MessageEvent;

public class LevelCompletedDialog extends Group {
	public static final int ON_CLOSE = 1;
	
	public LevelCompletedDialog(int score) {
		//create bg
		NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("dialog_bg"),60,60,60,60);
		Image bg = new Image(patch);
		bg.setSize(600, 500);
		setSize(bg.getWidth() , bg.getHeight());
		addActor(bg);
		
		//the text 
		Image title = new Image(SuperMario.atlas.findRegion("level_completed"));
		addActor(title);
		title.setX((getWidth() - title.getWidth())/2);
		title.setY(getHeight() - title.getHeight() - 100);
		
		//score label
		LabelStyle style = new LabelStyle();
		style.font = SuperMario.font1;
		style.fontColor = new Color(0x624601ff);

		Label label = new Label("Score :", style);
		addActor(label);
		label.setPosition((getWidth() - label.getWidth())/2, title.getY() - 140);
		
		LabelStyle style2 = new LabelStyle();
		style2.font = SuperMario.font2;
		style2.fontColor = new Color(0x624601ff);
		
		//the score 
		Label scoreLabel = new Label(String.valueOf(score) , style2);
		addActor(scoreLabel);
		scoreLabel.setPosition((getWidth() - scoreLabel.getWidth())/2, label.getY() - 50);
		
		//OK button
		ImageButton okBtn = new ImageButton(
				new TextureRegionDrawable(SuperMario.atlas.findRegion("ok_btn")), 
				new TextureRegionDrawable(SuperMario.atlas.findRegion("ok_btn_down")));
		
		addActor(okBtn);
		okBtn.setX((getWidth() - okBtn.getWidth())/2);
		okBtn.setY(60);
		
		//fire event on button click
		okBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fire(new MessageEvent(ON_CLOSE));
			}
		});
	}

}
