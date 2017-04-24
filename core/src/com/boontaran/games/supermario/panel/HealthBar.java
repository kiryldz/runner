package com.boontaran.games.supermario.panel;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.boontaran.games.supermario.SuperMario;

public class HealthBar extends Group {
	private Image face,track,bar;
	private float fullWidth = 100;
	
	public HealthBar() {
		setTransform(false);
		
		//the 'face' image
		face= new Image(SuperMario.atlas.findRegion("health_bar_face"));
		addActor(face);
		
		//track
		NinePatch trackPatch = new NinePatch(SuperMario.atlas.findRegion("health_bar_track"), 6, 6, 6, 6);
		track =new Image(trackPatch);
		track.setWidth(fullWidth + 8);
		
		track.setX(35);
		addActor(track);
		track.setY(face.getTop() - (track.getHeight()));
		
		//bar
		NinePatch barPatch = new NinePatch(SuperMario.atlas.findRegion("health_bar_bar"), 1, 1, 6, 6);
		bar =new Image(barPatch);
		addActor(bar);
		bar.setX(track.getX() + 4f);
		bar.setY(track.getY()+2.5f);
		
		setSize(track.getRight(), face.getHeight());
	}
	
	//set the bar width based on ratio ( 0 to 1)
	public void setValue(float ratio) {
		float w = ratio * fullWidth;
		if(w < 2) {
			bar.setVisible(false);
		} else {
			bar.setVisible(true);
		}
		bar.setWidth(w);
	}

}
