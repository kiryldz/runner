package com.boontaran.games.supermario.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.boontaran.games.StageGame;
import com.boontaran.games.supermario.LevelButton;
import com.boontaran.games.supermario.SuperMario;

public class LevelMap extends StageGame {
	public static final int ON_ICON_SELECTED = 1;
	public static final int ON_BACK = 2;
	
	public int selectedLevelId;
			
	
	@Override
	protected void create() {
		
		//background
		NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("map_bg"), 2, 2, 2, 2);
		Image bg = new Image(patch);
		fillScreen(bg, true, false);
		addChild(bg);
		
		
		//total score
		int totalScore=0;
				
		int curLevelProgress = 1+SuperMario.data.getLevelProgress();
		
		
		//world 1
		LevelButton level1 = new LevelButton(1, "Day-1");
		addChild(level1);
		centerActorX(level1);
		level1.setY(getHeight() - level1.getHeight() - 10);
		level1.addListener(levelButtonListener);
		totalScore += SuperMario.data.getScore(1);
		
		
		//world 2
		LevelButton level2 = new LevelButton(2, "Day-2");
		addChild(level2);
		centerActorX(level2);
		level2.setY(level1.getY() - level2.getHeight() - 20);
		level2.addListener(levelButtonListener);

		if(level2.getId() > curLevelProgress) {
			level2.lock();
		}
		totalScore += SuperMario.data.getScore(2);

		//world 3
		LevelButton level3 = new LevelButton(3, "Day-3");
		addChild(level3);
		centerActorX(level3);
		level3.setY(level2.getY() - level3.getHeight() - 20);
		level3.addListener(levelButtonListener);

		if(level3.getId() > curLevelProgress) {
			level3.lock();
		}
		totalScore += SuperMario.data.getScore(3);

		LevelButton level4 = new LevelButton(4, "Day-4");
		addChild(level4);
		centerActorX(level4);
		level4.setY(level3.getY() - level4.getHeight() - 20);
		level4.addListener(levelButtonListener);

		if(level4.getId() > curLevelProgress) {
			level4.lock();
		}
		totalScore += SuperMario.data.getScore(4);

		LevelButton level5 = new LevelButton(5, "Day-5");
		addChild(level5);
		centerActorX(level5);
		level5.setY(level4.getY() - level5.getHeight() - 20);
		level5.addListener(levelButtonListener);

		if(level5.getId() > curLevelProgress) {
			level5.lock();
		}
		totalScore += SuperMario.data.getScore(5);

		
		//displaying total score
		LabelStyle style = new LabelStyle();
		style.font = SuperMario.font1;
		style.fontColor = new Color(0x116ab5ff);
		Label label = new Label("Score : "+totalScore, style);
		addChild(label);
		label.setY(10);
		centerActorX(label);
	}
	//if icon clicked
	private ClickListener levelButtonListener = new ClickListener(){

		@Override
		public void clicked(InputEvent event, float x, float y) {
			LevelButton icon = (LevelButton)event.getTarget();
			
			if(icon.isLocked()) return;
			
			
			
			//note the selected id
			selectedLevelId = icon.getId();
			
			//notify main program
			call(ON_ICON_SELECTED);
			
			SuperMario.media.playSound("click");
			
			
		}
		
	};
	
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.ESCAPE || keycode == Keys.BACK){  //if back key is pressed
			call(ON_BACK);
			SuperMario.media.playSound("click");
			
			return true;
		}
		return super.keyDown(keycode);
	}



}
