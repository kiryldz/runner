package com.boontaran.games.supermario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.boontaran.games.GdxGame;
import com.boontaran.games.StageGame;
import com.boontaran.games.supermario.data.Data;
import com.boontaran.games.supermario.media.Media;
import com.boontaran.games.supermario.screens.Intro;
import com.boontaran.games.supermario.screens.LevelMap;

public class SuperMario extends GdxGame {
	private static final String TAG = "SuperMario";
	
	//asset loader & the state
	private AssetManager manager;
	private boolean isLoadingAssets;
	
	//shared texture for the game
	public static TextureAtlas atlas;
	//font
	public static BitmapFont font1,font2;
	
	//screens
	private Intro intro;
	private LevelMap map;
	
	//persistent data handling
	public static Data data;
	public static Media media;
	
	//game events
	
	public static final int LEVEL_FAILED = 101;
	public static final int LEVEL_COMPLETED = 102;
	public static final int LEVEL_PAUSED = 103;
	public static final int LEVEL_RESUMED = 104;
	
	
	
	private FPSLogger fpsLogger;
	
	public SuperMario() {
		//World.debug = true;
	}
	
	@Override
	public void create() {
		super.create();

		
		
		Gdx.input.setCatchBackKey(true);
		data = new Data();
		
		if(Settings.PROFILE_GL) {
			GLProfiler.enable();
		}
		if(Settings.LOG_FPS) {
			fpsLogger = new FPSLogger();
		}
		
		Gdx.app.log(TAG, "libgdx version :"+com.badlogic.gdx.Version.VERSION);
		
		StageGame.setAppSize(960, 540);
		//World.debug = true;
		
		//load & generate bitmap font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SuperMario256.ttf"));
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,:.'%()-";
		parameter.size = 36;
		parameter.minFilter = TextureFilter.MipMapLinearNearest;
		parameter.magFilter = TextureFilter.Linear;
		parameter.genMipMaps = true;
		font1 = generator.generateFont(parameter);
		
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter2.characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,:.'%()-";
		parameter2.size = 48;
		parameter2.minFilter = TextureFilter.MipMapLinearNearest;
		parameter2.magFilter = TextureFilter.Linear;
		parameter2.genMipMaps = true;
		font2 = generator.generateFont(parameter2);
		
		generator.dispose();
		
		
		//load assets
		manager = new AssetManager();
		manager.load("images/pack.atlas", TextureAtlas.class);
		
		//load sounds
		manager.load("sounds/click.mp3" , Sound.class);
		manager.load("sounds/jump.mp3" , Sound.class);
		manager.load("sounds/coin.ogg" , Sound.class);
		manager.load("sounds/hit.ogg" , Sound.class);
		manager.load("sounds/hit2.ogg" , Sound.class);
		manager.load("sounds/bullet_pack.ogg" , Sound.class);
		manager.load("sounds/bullet.mp3" , Sound.class);
		manager.load("sounds/flag.mp3" , Sound.class);
		manager.load("sounds/level_completed.mp3" , Sound.class);
		
				
		//load music
		manager.load("sounds/music/intro.ogg" , Music.class);
		manager.load("sounds/music/level.ogg" , Music.class);
		
			
		isLoadingAssets = true;
	}
	private void onAssetsCompleted() {
		//show intro
		showIntro();
		
	}
	
	private void showIntro() {
		//create
		intro = new Intro();
		setScreen(intro);
		
		//listener
		intro.setCallback(new StageGame.Callback() {
			@Override
			public void call(int code) {
				//play btn clicked
				if(code == Intro.PLAY) {
					showLevelMap();
				}
			}

			@Override
			public void call(int code, int value) {
				
			}

			
		});
		
		//start the music
		SuperMario.media.playMusic("intro");
		
	}
	
	
	//showing level map with the icons
	private void showLevelMap() {
		//create
		map = new LevelMap();
		setScreen(map);
		
		//the callback
		map.setCallback(new StageGame.Callback() {
			@Override
			public void call(int code) {
				//icon selected, start level with the particular id
				if(code == LevelMap.ON_ICON_SELECTED) {
					startLevel(map.selectedLevelId);
				}
				//back to intro screen
				else if(code == LevelMap.ON_BACK) {
					showIntro();
				}
			}

			@Override
			public void call(int code, int value) {
				
			}
		});
		
		SuperMario.media.stopMusic("level");
		SuperMario.media.playMusic("intro");

	}
	
	
	private void startLevel(int levelId) {
		Level level=null;
		
		//create "level" object based on the id
		level = new Level(levelId);
		setScreen(level);

		//set bg
		level.setBackgroundRegion("level_bg");
		
		//the level callback
		level.setCallback(new StageGame.Callback() {
			@Override
			public void call(int code) {
				//failed, back to map
				if(code == Level.FAILED) {
					showLevelMap();
				}
				
				//completed back to map also
				else if(code == Level.COMPLETED) {
					showLevelMap();	
				}
				
			}

			@Override
			public void call(int code, int value) {
				
			}
		});
		
		
		SuperMario.media.stopMusic("intro");
		SuperMario.media.playMusic("level");
		
	
	}
	
	
	@Override
	public void render() {
		
		//loading assets
		if(isLoadingAssets) {
			if(manager.update()) { //if assets loaded
				isLoadingAssets = false;
				atlas = manager.get("images/pack.atlas" ,TextureAtlas.class );
				media = new Media(manager);
				onAssetsCompleted();
			}
		}
		
		if(Settings.PROFILE_GL) {
			System.out.println("---------");
			System.out.println("draw calls : "+GLProfiler.drawCalls);
			System.out.println("texture bindings:"+GLProfiler.textureBindings);
			GLProfiler.reset();
		}
		if(Settings.LOG_FPS) {
			fpsLogger.log();
		}
		super.render();
	}

	
}

