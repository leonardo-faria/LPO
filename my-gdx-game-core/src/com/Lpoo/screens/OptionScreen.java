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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionScreen implements Screen{

	private Button easyButton, mediumButton, hardButton;
	private TextButton backButton;
	private ButtonGroup buttonGroup;
	private Stage stage;
	private TextureAtlas atlas;
	private Table table;
	private Label heading, easy,medium, hard;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/Normal.pack"));
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		//creating buttons
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = skin.getDrawable("radioDown");
		buttonStyle.down = skin.getDrawable("radioDown");
		buttonStyle.checked = skin.getDrawable("radioUp");

		easyButton = new Button(buttonStyle);
		easyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				JumpEm.difficulty = 1;
			}

		});
		mediumButton = new Button(buttonStyle);
		mediumButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				JumpEm.difficulty = 2;
			}

		});
		hardButton = new Button(buttonStyle);
		hardButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				JumpEm.difficulty = 3;
			}		
		});

		switch(JumpEm.difficulty) {
		case 1:
			easyButton.setChecked(true);
			break;
		case 2:
			mediumButton.setChecked(true);
			break;
		case 3:
			hardButton.setChecked(true);
			break;
		default:
			break;
		}

		buttonGroup = new ButtonGroup(easyButton,mediumButton,hardButton);
		buttonGroup.setMaxCheckCount(1);
		buttonGroup.setMinCheckCount(1);
		buttonGroup.setUncheckLast(true);


		backButton = new TextButton("Back", skin, "default");
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		backButton.pad(15);

		//Creating heading
		heading = new Label("Difficulty",  skin, "default");

		easy = new Label("Easy",  skin, "default");
		easy.setFontScale(0.5f);

		medium = new Label("Medium", skin, "default");
		medium.setFontScale(0.5f);

		hard = new Label("Hard", skin, "default");
		hard.setFontScale(0.5f);



		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(easy);
		table.add(easyButton);
		table.row();
		table.add(medium);
		table.add(mediumButton);
		table.row();
		table.add(hard);
		table.add(hardButton);
		table.row();
		table.add(backButton);
		table.getCell(backButton).spaceTop(70);
		table.debug();
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
		.push(Tween.set(easy, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(easyButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(medium, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(mediumButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(hard, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(hardButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(backButton, ActorAccessor.ALPHA).target(0))
		.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
		.push(Tween.to(easy, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(easyButton, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(medium, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(mediumButton, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(hard, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(hardButton, ActorAccessor.ALPHA, .25f).target(1))
		.push(Tween.to(backButton, ActorAccessor.ALPHA, .25f).target(1))
		.end().start(tweenManager);

		//table fade in
		Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);


	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
	}

}
