package com.boontaran.games.supermario.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.boontaran.games.StageGame;
import com.boontaran.games.supermario.LevelButton;
import com.boontaran.games.supermario.SuperMario;

public class LevelMap extends StageGame {

    PagedScrollPane scroll;

    public static final int ON_ICON_SELECTED = 1;
	public static final int ON_BACK = 2;
	
	public int selectedLevelId;

    //total score
    int totalScore=0;
    int curLevelProgress;

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("map_bg"), 2, 2, 2, 2);
        Image bg = new Image(patch);
        fillScreen(bg, true, false);
        layer.add(bg);
        return layer;
    }

    @Override
	protected void create() {
		
		//background
		NinePatch patch = new NinePatch(SuperMario.atlas.findRegion("map_bg"), 2, 2, 2, 2);
		Image bg = new Image(patch);
		fillScreen(bg, true, false);
		addChild(bg);

        //stage.clear();

        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(getWidth(),getHeight());
        //stack.add(buildBackgroundLayer());
        //stack.add(buildObjectsLayer());

        scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(25);
        int c = 1;
        for (int l = 0; l < 2; l++) {
            Table levels = new Table().pad(50);
            levels.defaults().pad(20, 40, 20, 40);
            for (int y = 0; y < 2; y++) {
                levels.row();
                for (int x = 0; x < 3; x++) {
                    levels.add(getLevelButton(c++)).expand().fill();
                }
            }
            scroll.addPage(levels);
        }
        stack.add(scroll);

		curLevelProgress = 1+SuperMario.data.getLevelProgress();


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

    public LevelButton getLevelButton(int level) {
        LabelStyle Labelstyle = new LabelStyle();
        Labelstyle.font = SuperMario.font1;
        Labelstyle.fontColor = new Color(0x116ab5ff);

        LevelButton button = new LevelButton(level,"World-"+level);

        String levelName=String.valueOf(level);
        Label label = new Label(levelName, Labelstyle);
        //label.setFontScale(1f);
        label.setAlignment(Align.bottom);
        //label.setAlignment(Align.center);

        if(5<level){
            //button.stack(new Image(skinRunner.getDrawable("ball_lock")), label).expand().fill();
        }else{
            //button.stack(new Image(skinRunner.getDrawable("ball")), label).expand().fill();
        }

        //skinLibgdx.add("star-filled", skinLibgdx.newDrawable("white", Color.YELLOW), Drawable.class);
        //skinLibgdx.add("star-unfilled", skinLibgdx.newDrawable("white", Color.GRAY), Drawable.class);

        int stars = 3;

        Table starTable = new Table();
        starTable.defaults().pad(5);
        if (stars >= 0) {
            for (int star = 0; star < 3; star++) {
                if (stars > star) {
                    //starTable.add(new Image(skinRunner.getDrawable("star_y"))).width(20).height(20);
                } else {
                    //starTable.add(new Image(skinRunner.getDrawable("star_g"))).width(20).height(20);
                }
            }
        }

        //button.row();
        //button.add(starTable).height(30);

        button.setName(levelName);
        button.addListener(levelButtonListener);
        return button;
    }




/*    public LevelButton getLevelButton(int level){
        LevelButton level1 = new LevelButton(level, "World-"+level);
        addChild(level1);
        centerActorX(level1);
        if(level==1){
            level1.setY(getHeight() - level1.getHeight() - 10);
        }else{
            level1.setY((getHeight() - level1.getHeight() - 10) - level1.getHeight() - 20);
        }

        level1.addListener(levelButtonListener);
        totalScore += SuperMario.data.getScore(1);

        if (level>1){
            if(level1.getId() > curLevelProgress) {
                level1.lock();
            }
        }


        return level1;
    }*/

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
