package com.Lpoo.game;

import com.Lpoo.screens.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class JumpEm extends Game {

	public static final String TITLE = "Jump'Em", VERSION = "ALPHA 0.1";
	public static int difficulty, lastScore, lastTime;
	public static Score scoreArcade, scoreChallenge;

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
		FileHandle file = Gdx.files.internal("Arcade.jpm");
		Json json = new Json();
		if (file.exists()) {
			System.out.println(file.readString());
			scoreArcade = json.fromJson(Score.class, file.readString());
		}
		setScreen(new MainMenu());

	}

	@Override
	public void render() {
		super.render();
	}

}