package com.Lpoo.game;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {

	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton exitButton, startButton;
	private BitmapFont white;
	private Label heading;
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

		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);

		//creating buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("buttonNormal9");
		textButtonStyle.down = skin.getDrawable("buttonPressed9");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.BLACK;

		exitButton = new TextButton("EXIT", textButtonStyle );
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		exitButton.pad(10);

		startButton = new TextButton("START", textButtonStyle);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new TestScreen());
			}
		});
		startButton.pad(10);

		//Creating heading
		heading = new Label(JumpEm.TITLE, new LabelStyle(white, Color.WHITE));
		heading.setFontScale(2);

		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(startButton);
		table.getCell(startButton).spaceBottom(15);
		table.row();
		table.add(exitButton);
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
		.push(Tween.set(startButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(exitButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
		.push(Tween.to(startButton, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(exitButton, ActorAccessor.ALPHA, .25f).target(1))
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
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();

	}

}
