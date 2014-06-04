package com.Lpoo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class JumpEm extends Game {
 
	
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
		setScreen(new LogoSplash());

	}

	@Override
	public void render() {
		super.render();
	}
}