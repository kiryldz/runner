package com.boontaran.games.supermario;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.boontaran.MessageEvent;

public class LevelButton extends Group {
	public static final int ON_CLICK = 1;
	
	private boolean locked = false;
	private int id;
	private TextButton button ;
	
	public LevelButton(int id, String name) {
		setTransform(false);
		this.id = id;
		
		TextButtonStyle style = new TextButtonStyle();
		style.font = SuperMario.font1;
		style.fontColor = new Color(0x624601ff);
		NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("level_select_bg"), 14, 14, 14, 14);
		style.up = new NinePatchDrawable(patch);
		
		NinePatch patch2 = new NinePatch(SuperMario.atlas.findRegion("level_select_bg_down"), 14, 14, 14, 14);
		style.down = new NinePatchDrawable(patch2);
				
		NinePatch patch3 = new NinePatch(SuperMario.atlas.findRegion("level_select_bg_disabled"), 14, 14, 14, 14);
		style.disabled = new NinePatchDrawable(patch3);
		
		button = new TextButton(name, style) ;
		addActor(button);
		
		setSize(button.getWidth(), button.getHeight());
		
		
		
		addCaptureListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				event.setTarget(LevelButton.this);
				return true;
			}
		});
	}
	
	//lock the icon
	public void lock() {
		locked = true;
		button.setTouchable(Touchable.disabled);
		button.setDisabled(true);
	}
	
	//unlock the icon
	public void unlock() {
		locked = false;
		button.setTouchable(Touchable.enabled);
		button.setDisabled(false);
	}
	public boolean isLocked() {
		return locked;
	}
	public int getId() {
		return id;
	}
	
	
	
	
}
