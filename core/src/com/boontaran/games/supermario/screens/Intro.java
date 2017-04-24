package com.boontaran.games.supermario.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.games.StageGame;
import com.boontaran.games.supermario.SuperMario;

public class Intro extends StageGame {
	public static final int PLAY = 1;
	
	
	@Override
	protected void create() {
		//intro bg
		Image bg = new Image(SuperMario.atlas.findRegion("intro_bg"));
		
		//resize the bg to fill the screen, keep aspect ratio
		fillScreen(bg, true, false);
		addChild(bg);
		
		//game title
		Image title = new Image(SuperMario.atlas.findRegion("title"));
		centerActorX(title);
		title.setY(getHeight() - title.getHeight() - 100);
		addChild(title);
		
		//play button
		ImageButton playBtn = new ImageButton(
				new TextureRegionDrawable(SuperMario.atlas.findRegion("play_btn")), 
				new TextureRegionDrawable(SuperMario.atlas.findRegion("play_btn_down")));
		centerActorX(playBtn);
		playBtn.setY(100);
		addChild(playBtn);
		
		//btn listener
		playBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SuperMario.media.playSound("click");
				call(PLAY);
			}
			
		});
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.ESCAPE || keycode == Keys.BACK){ //if the back key pressed
			Gdx.app.exit();
			return true;
		}
		return super.keyDown(keycode);
	}


	
	
	

}
