package com.Lpoo.game;

import com.Lpoo.screens.GameScreen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * Reads the the user inputs to create the trampoline and pause the game
 *
 */
public class JumpEmInputProcessor implements InputProcessor{


	private int x0, y0,xf,yf;
	private Screen gameScreen;
	private boolean touched, pause,quit;

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

	public boolean getPause(){
		return pause;
	}

	public JumpEmInputProcessor(Screen gameScreen){
		this.gameScreen = gameScreen;
		touched = false;
		pause = false;
		quit = false;
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

		if((screenX >= 0 && screenX <= ((GameScreen) gameScreen).pauseButton.getRight()) && (screenY >= 0 && screenY <= ((GameScreen) gameScreen).pauseButton.getHeight()))
		{
			if(!pause)
				gameScreen.pause();

			else
				gameScreen.resume();

			return true;
		}
		else if(pause){
			if((screenX >= 0 && screenX <= ((GameScreen) gameScreen).quitButton.getRight()) && (screenY >= ((GameScreen) gameScreen).getHeight() - ((GameScreen) gameScreen).quitButton.getHeight() && screenY <= ((GameScreen) gameScreen).getHeight()))
				quit = true;
		}
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
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;

	}

	public void setPause(boolean pause) {
		this.pause = pause;

	}

	public boolean getQuit() {
		return quit;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;

	}

}
