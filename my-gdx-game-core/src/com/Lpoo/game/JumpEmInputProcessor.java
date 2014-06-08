package com.Lpoo.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.World;

public class JumpEmInputProcessor implements InputProcessor{

	@SuppressWarnings("unused")
	private World world;
	private int x0, y0,xf,yf;
	private boolean touched;
	
	public int getX0(){
		return x0;
	}
	
	public int getY0(){
		return y0;
	}
	
	public int getXf(){
		return xf;
	}
	
	public int getYf(){
		return yf;
	}
	
	public boolean getTouched(){
		return touched;
	}
	
	public JumpEmInputProcessor(World world){
		this.world = world;
		touched = false;
		x0 = 0;
		y0 = 0;
		xf = 0;
		yf = 0;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		x0 = screenX;
		y0 = screenY;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {	
		if(screenX != x0 && screenY != y0)
		{
			xf = screenX;
			yf = screenY;
			touched = true;
			return true;
		}
			
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

	public void setTouched(boolean touched) {
		this.touched = touched;
		
	}

	

}
