package com.Lpoo.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.Lpoo.game.ActorAccessor;
import com.Lpoo.game.JumpEm;
import com.Lpoo.game.Score;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.Json;

public class OptionScreen implements Screen {

	private Button easyButton, mediumButton, hardButton;
	private TextButton backButton,resetButton;
	private ButtonGroup buttonGroup;
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private BitmapFont white;
	private Label heading, easy, medium, hard;
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

		atlas = new TextureAtlas("ui/Normal.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);

		// creating buttons
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

		switch (JumpEm.difficulty) {
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

		buttonGroup = new ButtonGroup(easyButton, mediumButton, hardButton);
		buttonGroup.setMaxCheckCount(1);
		buttonGroup.setMinCheckCount(1);
		buttonGroup.setUncheckLast(true);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("wood");
		textButtonStyle.down = skin.getDrawable("wood");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.WHITE;

		backButton = new TextButton("Back", textButtonStyle);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		backButton.pad(15);
		resetButton = new TextButton("Reset Scores", textButtonStyle);
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				JumpEm.scoreArcade = new Score();
				Json json = new Json();
				FileHandle file = Gdx.files.local("Arcade.jpm");
				file.writeString(json.toJson(JumpEm.scoreArcade), false);
			}
		});
		resetButton.pad(15);

		// Creating heading
		heading = new Label("Settings", new LabelStyle(white, Color.WHITE));

		easy = new Label("Easy   ", new LabelStyle(white, Color.WHITE));
		easy.setFontScale(1f);

		medium = new Label("Medium ", new LabelStyle(white, Color.WHITE));
		medium.setFontScale(1f);

		hard = new Label("Hard   ", new LabelStyle(white, Color.WHITE));
		hard.setFontScale(1f);

		table.add(heading);
		table.getCell(heading).spaceBottom(50);
		table.row();
		Table difficulties = new Table(skin);
		difficulties.add(easy);
		difficulties.add(easyButton);
		difficulties.row();
		difficulties.add(medium);
		difficulties.add(mediumButton);
		difficulties.row();
		difficulties.add(hard);
		difficulties.add(hardButton);
		table.add(difficulties);
		table.row();
		table.add(resetButton);
		table.getCell(resetButton).spaceTop(50);
		table.debug();
		table.row();
		table.add(backButton);
		table.getCell(backButton).spaceTop(10);
		table.debug();
		if (Gdx.app.getType() == ApplicationType.Android) {
			table.setTransform(true);
			table.setOrigin(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			table.setScale((float) (Gdx.graphics.getWidth() / 300.0));
		}
		stage.addActor(table);

		// animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// pretty colours
		Timeline.createSequence().beginSequence().push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1)).end().repeat(Tween.INFINITY, 0)
				.start(tweenManager);

		// buttons fade in
		Timeline.createSequence().beginSequence().push(Tween.set(easy, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(easyButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(medium, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(mediumButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(hard, ActorAccessor.ALPHA).target(0)).push(Tween.set(hardButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(resetButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(backButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(easy, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(easyButton, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(medium, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(mediumButton, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(hard, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(hardButton, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(resetButton, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(backButton, ActorAccessor.ALPHA, .25f).target(1)).end().start(tweenManager);

		// table fade in
		Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

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
		skin.dispose();
		white.dispose();
	}

}