package com.boontaran.games.supermario;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.MessageEvent;
import com.boontaran.MessageListener;

public class PauseDialog extends Group {
	// 2 mode of the dialog, quit game or resume game
	public static final int ON_RESUME = 1;
	public static final int ON_QUIT = 2;
	
	//music & sound mute btn
	private ToggleButton musicBtn,soundBtn;

	public PauseDialog() {
		//the bg
		NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("dialog_bg"),60,60,60,60);
		Image bg = new Image(patch);
		bg.setSize(600, 500);
		setSize(bg.getWidth() , bg.getHeight());
		addActor(bg);
		
		//title
		Image title = new Image(SuperMario.atlas.findRegion("paused"));
		addActor(title);
		title.setX((getWidth() - title.getWidth())/2);
		title.setY(getHeight() - title.getHeight() - 36);
		
		//resume btn
		ImageButton resumeBtn = new ImageButton(
				new TextureRegionDrawable(SuperMario.atlas.findRegion("resume_btn")), 
				new TextureRegionDrawable(SuperMario.atlas.findRegion("resume_btn_down")));
		
		addActor(resumeBtn);
		resumeBtn.setX(getWidth() - resumeBtn.getWidth()-40);
		resumeBtn.setY(46);
		
		//fire the 'resume' event
		resumeBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fire(new MessageEvent(ON_RESUME));
			}
		});
		
		//exit btn
		ImageButton exitBtn = new ImageButton(
				new TextureRegionDrawable(SuperMario.atlas.findRegion("quit_btn")), 
				new TextureRegionDrawable(SuperMario.atlas.findRegion("quit_btn_down")));
		
		addActor(exitBtn);
		exitBtn.setX(40);
		exitBtn.setY(46);
		
		//fire 'quit' event
		exitBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fire(new MessageEvent(ON_QUIT));
			}
		});
		
		
		//the toggle button of music mute
		musicBtn = new ToggleButton(new Image(SuperMario.atlas.findRegion("music_on")), new Image(SuperMario.atlas.findRegion("music_off")));
		addActor(musicBtn);
		musicBtn.setY(300);
		musicBtn.setX(getWidth()/2 - musicBtn.getWidth()/2);
		musicBtn.setOn(!SuperMario.data.isMusicMuted());
		
		//listen the action
		musicBtn.addListener(new MessageListener(){
			@Override
			protected void receivedMessage(int message, Actor actor) {
				//not muted
				if(message == ToggleButton.ON) {
					//save the persistent state
					SuperMario.data.setMusicMute(false);
					//update the media class
					SuperMario.media.updateState();
				}
				//muted
				else if(message == ToggleButton.OFF) {
					SuperMario.data.setMusicMute(true);
					SuperMario.media.updateState();
				}
			}
		});
		
		//same above, but it for sound
		soundBtn = new ToggleButton(new Image(SuperMario.atlas.findRegion("sound_on")), new Image(SuperMario.atlas.findRegion("sound_off")));
		addActor(soundBtn);
		soundBtn.setY(musicBtn.getY() - 80);
		soundBtn.setX(getWidth()/2 - soundBtn.getWidth()/2);
		soundBtn.setOn(!SuperMario.data.isSoundMuted());
		soundBtn.addListener(new MessageListener(){
			@Override
			protected void receivedMessage(int message, Actor actor) {
				if(message == ToggleButton.ON) {
					SuperMario.data.setSoundMute(false);
					SuperMario.media.updateState();
				}
				else if(message == ToggleButton.OFF) {
					SuperMario.data.setSoundMute(true);
					SuperMario.media.updateState();
				}
			}
		});
	}

}
