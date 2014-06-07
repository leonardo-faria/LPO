package com.Lpoo.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.Lpoo.game.ActorAccessor;
import com.Lpoo.game.JumpEm;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LoseScreen implements Screen{

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton returnButton;
	private BitmapFont white;
	private Label heading, timeGame ,points;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/Normal.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);

		//creating buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("wood");
		textButtonStyle.down = skin.getDrawable("wood");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.WHITE;

		returnButton = new TextButton("Accept", textButtonStyle );
		returnButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});

		returnButton.pad(15);

		heading = new Label("SCORE", new LabelStyle(white, Color.WHITE));
		heading.setFontScale(2);

		timeGame = new Label("Total Play Time: " + JumpEm.lastTime, new LabelStyle(white, Color.WHITE));

		points = new Label("Points Acumulated: " + JumpEm.lastScore, new LabelStyle(white, Color.WHITE));

		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(timeGame);
		table.getCell(timeGame).spaceBottom(30);
		table.row();
		table.add(points);
		table.getCell(points).spaceBottom(30);
		table.row();
		table.add(returnButton);
		stage.addActor(table);

		//animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		//pretty colours
		Timeline.createSequence().beginSequence()
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0,0,1))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0,1,0))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,0,0))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,1,0))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0,1,1))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,0,1))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,1,1))
		.end().repeat(Tween.INFINITY, 0).start(tweenManager);

		//buttons fade in
		Timeline.createSequence().beginSequence()
		.push(Tween.set(timeGame, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(points, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(returnButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
		.push(Tween.to(timeGame, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(points, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(returnButton, ActorAccessor.ALPHA, .25f).target(1))
		.end().start(tweenManager);

		//table fade in
		Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
