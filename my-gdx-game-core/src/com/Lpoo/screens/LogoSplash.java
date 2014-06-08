package com.Lpoo.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.Lpoo.game.JumpEm;
import com.Lpoo.game.SpriteAcessor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LogoSplash implements Screen{

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAcessor());
		
		Texture splashTexture = new Texture("img/Welcome.png");
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAcessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAcessor.ALPHA, 2).target(1).repeatYoyo(1, 1).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}).start(tweenManager);
		
		Texture imgTramp = new Texture(Gdx.files.internal("img/wood.png"));
		Texture imgBall = new Texture(Gdx.files.internal("img/redBall.png"));
		Texture imgWall = new Texture(Gdx.files.internal("img/wood.png"));
		Texture imgPaper = new Texture(Gdx.files.internal("img/forest.png"));
		
		JumpEm.imgBall = imgBall;
		JumpEm.imgTramp = imgTramp;
		JumpEm.imgWall = imgWall;
		JumpEm.imgPaper = imgPaper;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}

}
