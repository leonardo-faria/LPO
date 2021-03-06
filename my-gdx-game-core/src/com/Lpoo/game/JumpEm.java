package com.Lpoo.game;

import com.Lpoo.screens.LogoSplash;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;

/**
 * Class used to start the game
 * 
 */
public class JumpEm extends Game {

	public static final String TITLE = "Jump'Em", VERSION = "ALPHA 0.1";
	public static int difficulty, lastScore, lastTime;
	public static Score scoreArcade, scoreChallenge;
	public static Texture imgTramp,imgBall, imgWall, imgPaper;

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		super.setScreen(screen);
	}

	@Override
	public Screen getScreen() {
		// TODO Auto-generated method stub
		return super.getScreen();
	}

	@Override
	public void create() {
		difficulty = 1;
		scoreArcade = new Score(0, 0);
		FileHandle file = Gdx.files.local("Arcade.jpm");
		Json json = new Json();
		if (file.exists())
			scoreArcade = json.fromJson(Score.class, file.readString());
		setScreen(new LogoSplash());

	}

	@Override
	public void render() {
		super.render();
	}

}