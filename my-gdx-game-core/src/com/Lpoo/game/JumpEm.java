package com.Lpoo.game;

import com.Lpoo.screens.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class JumpEm extends Game {
 
	public static final String TITLE =  "Jump'Em", VERSION = "ALPHA 0.1";
	public static int difficulty, bestScore, lastScore, BestTime, lastTime;
	
	
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
		bestScore = 0;
		BestTime = 0;
		setScreen(new MainMenu());

	}

	@Override
	public void render() {
		super.render();
	}
}