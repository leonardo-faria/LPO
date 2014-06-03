package com.Lpoo.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.World;

public class JumpEmInputProcessor implements InputProcessor{

	private World world;
	private int x0, y0;
	
	public JumpEmInputProcessor(World world){
		this.world = world;
		x0 = 0;
		y0 = 0;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		x0 = screenX;
		y0 = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {	
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
