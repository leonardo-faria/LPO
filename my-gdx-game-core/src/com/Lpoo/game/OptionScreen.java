package com.Lpoo.screens;

import aurelienribon.tweenengine.TweenManager;

import com.Lpoo.game.JumpEm;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionScreen implements Screen{

	private Button easyButton, mediumButton, hardButton;
	private TextButton backButton;
	private ButtonGroup buttonGroup;
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private BitmapFont white;
	private Label heading, easy,medium, hard;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

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
	
		easyButton.setChecked(true);
				
		buttonGroup = new ButtonGroup(easyButton,mediumButton,hardButton);
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

		backButton = new TextButton("Back", textButtonStyle );
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		backButton.pad(15);
		
		//Creating heading
		heading = new Label("Difficulty", new LabelStyle(white, Color.WHITE));
		heading.setFontScale(1);
		
		easy = new Label("Easy", new LabelStyle(white, Color.WHITE));
		easy.setFontScale(0.5f);
		
		medium = new Label("Medium", new LabelStyle(white, Color.WHITE));
		medium.setFontScale(0.5f);
		
		hard = new Label("Hard", new LabelStyle(white, Color.WHITE));
		hard.setFontScale(0.5f);
		
		

		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(easy);
		table.getCell(easy).spaceLeft(20);
		table.add(easyButton);
		table.add(medium);
		table.getCell(medium).spaceLeft(20);
		table.getCell(medium).spaceRight(10);
		table.add(mediumButton);
		table.add(hard);
		table.getCell(hard).spaceLeft(20);
		table.getCell(hard).spaceRight(10);
		table.add(hardButton);
		table.row();
		table.add(backButton);
		table.getCell(backButton).spaceRight(100);
		table.getCell(backButton).spaceTop(70);
		stage.addActor(table);

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
		// TODO Auto-generated method stub

	}

}
