package com.Lpoo.screens;

import com.Lpoo.game.JumpEm;
import com.Lpoo.game.JumpEmCollision;
import com.Lpoo.game.JumpEmInputProcessor;
import com.Lpoo.game.entities.Floor;
import com.Lpoo.game.entities.Jumper;
import com.Lpoo.game.entities.Trampoline;
import com.Lpoo.game.entities.Wall;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {
	private World world;
	private OrthographicCamera camera;
	private JumpEmInputProcessor inputProcessor;

	private Label headingPause;
	private JumpEmCollision collisionProcessor;
	private Floor floor;
	private Array<Jumper> jumpers;
	private Stage stage;
	private Table pauseTable, titleTable, quitTable;

	private Wall top;
	private Wall left, right;
	private Trampoline test;
	private Texture imgTramp,imgBall, imgWall, imgPaper;
	private float TIMESTEP = 1 / 60f;
	private final int VelocityIterations = 8, PositionIterations = 3;
	private SpriteBatch batch;
	private Sprite sprite;

	private TextureAtlas atlas;
	private Skin skin;

	public TextButton pauseButton;
	public TextButton quitButton;

	private float jumperRadius;
	private long startTime, pauseTime;
	private int totalPause;
	private int score;
	private int trampolineNumber;
	private float height;
	private float width;

	@Override
	public void render(float delta) {

		world.step(TIMESTEP, VelocityIterations, PositionIterations);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!inputProcessor.getPause())
			if (inputProcessor.getTouched()
					&& trampolineNumber < JumpEm.difficulty * 3 + 1) {
				trampolineNumber++;
				float x0 = inputProcessor.getX0();
				float y0 = inputProcessor.getY0();
				float xf = inputProcessor.getXf();
				float yf = inputProcessor.getYf();
				Vector3 coord0 = new Vector3(x0, y0, 0);
				Vector3 coordf = new Vector3(xf, yf, 0);
				camera.unproject(coord0);
				camera.unproject(coordf);
				float length = (float) Math.sqrt((coordf.x - coord0.x)
						* (coordf.x - coord0.x) + (coordf.y - coord0.y)
						* (coordf.y - coord0.y)) / 2;
				if (length > jumperRadius*10)
					length = jumperRadius*10;
				if (length < 3)
					length = 3;
				float angle = (float) Math.atan2(coordf.y - coord0.y, coordf.x
						- coord0.x);
				if (angle > Math.PI / 2.0) {
					angle = (float) -(Math.PI - angle);
				} else if (angle < -Math.PI / 2.0) {
					angle = (float) (Math.PI + angle);
				}
				if (angle > Math.toRadians(30))
					angle = (float) Math.toRadians(30);
				if (angle < -Math.toRadians(30))
					angle = (float) -Math.toRadians(30);
				Vector2 center = new Vector2(
						(float) ((coordf.x + coord0.x) * 0.5),
						(float) ((coordf.y + coord0.y) * 0.5));
				test = new Trampoline(world, center, length, angle, imgTramp);
				inputProcessor.setTouched(false);
			}

		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.setSize(width/10, height/10);
		sprite.setCenter(0, 0);
		sprite.draw(batch);
		for (int i = 0; i < bodies.size; i++) {
			if(bodies.get(i).getUserData() instanceof Sprite)
			{

				Sprite sprite = (Sprite) bodies.get(i).getUserData();
				sprite.setCenter(bodies.get(i).getPosition().x, bodies.get(i).getPosition().y);
				sprite.setRotation(bodies.get(i).getAngle()* MathUtils.radiansToDegrees);
				sprite.draw(batch);

			}

			if (bodies.get(i).getFixtureList().first().getUserData() == "destroy") {
				trampolineNumber--;
				world.destroyBody(bodies.get(i));
				score += JumpEm.difficulty;
				int r = MathUtils.random(100);
				if (r > 35 && jumpers.size < JumpEm.difficulty * 3) {
					jumpers.add(new Jumper(world, MathUtils.random(
							(float) -(floor.getWidth() * 0.5),
							(float) (floor.getWidth() * 0.5)), 0,
							jumperRadius, imgBall));
				}
			} else if (bodies.get(i).getFixtureList().first().getUserData() == "lose") {
				JumpEm.lastTime = ((int) TimeUtils.timeSinceMillis(startTime))- totalPause;
				JumpEm.lastScore = score;
				((Game) Gdx.app.getApplicationListener()).setScreen(new LoseScreen());
			}

		}
		batch.end();



		if(inputProcessor.getQuit())
		{
			inputProcessor.setQuit(false);
			((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
		}

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 10;
		camera.viewportHeight = height / 10;

	}

	@Override
	public void show() {

		batch = new SpriteBatch();

		stage = new Stage();

		atlas = new TextureAtlas("ui/Normal.pack");
		skin = new Skin(atlas);

		imgBall = JumpEm.imgBall; 
		imgTramp = JumpEm.imgTramp;
		imgWall = JumpEm.imgWall; 
		imgPaper = JumpEm.imgPaper; 
		sprite = new Sprite(imgPaper);

		pauseTable = new Table(skin);
		pauseTable.setFillParent(true);

		quitTable = new Table(skin);
		quitTable.setFillParent(true);

		titleTable = new Table(skin);
		titleTable.setFillParent(true);

		BitmapFont white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("wood");
		textButtonStyle.down = skin.getDrawable("wood");
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.WHITE;

		pauseButton = new TextButton("Pause",textButtonStyle);
		pauseButton.pad((float) (Gdx.graphics.getWidth() / 300.0)*10);
		quitButton = new TextButton("Quit", textButtonStyle);
		quitButton.pad((float) (Gdx.graphics.getWidth() / 300.0)*10);

		pauseTable.add(pauseButton);
		pauseTable.debug();

		quitTable.add(quitButton);
		
		if (Gdx.app.getType() == ApplicationType.Android) {
			pauseTable.setTransform(true);
			pauseTable.top().left();
			
			quitTable.setTransform(true);
			
			titleTable.setTransform(true);
			titleTable.setOrigin(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			titleTable.setScale((float) (Gdx.graphics.getWidth() / 300.0));
			quitTable.bottom().left();
		}
		
		stage.addActor(pauseTable);




		headingPause = new Label("PAUSED", new LabelStyle(white, Color.WHITE));
		headingPause.setFontScale(1.9f);

		titleTable.add(headingPause);
		titleTable.center();

		startTime = TimeUtils.millis();
		totalPause = 0;
		score = 0;
		world = new World(new Vector2(0, -15f), true);

		inputProcessor = new JumpEmInputProcessor(this);
		collisionProcessor = new JumpEmCollision(world);
		world.setContactListener(collisionProcessor);
		world.setContactFilter(collisionProcessor);

		Gdx.input.setInputProcessor(inputProcessor);

		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10,
				Gdx.graphics.getHeight() / 10);

		Vector3 size = new Vector3(width, height, 0);
		camera.unproject(size);

		jumperRadius = (float) (size.x / 17.0);
		left = new Wall(world, -size.x, 0, Math.abs(size.y) * 3, 1, 0,"Wall");
		right = new Wall(world, size.x, 0, Math.abs(size.y) * 3, 1, 0,"Wall");
		floor = new Floor(world, 0, (float) (size.y - 1), 1, size.x, 0);
		top = new Wall(world, 0, -size.y * 2, 1, size.x, 0, "Top");
		jumpers = new Array<Jumper>();
		jumpers.add(new Jumper(world, 0, 0, jumperRadius, imgBall));

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		inputProcessor.setPause(true);
		pauseTime = TimeUtils.millis();
		stage.addActor(titleTable);
		stage.addActor(quitTable);
		TIMESTEP = 0;

	}

	@Override
	public void resume() {
		TIMESTEP = 1 / 60f;
		totalPause +=(int) TimeUtils.timeSinceMillis(pauseTime);
		stage.clear();
		stage.addActor(pauseTable);
		inputProcessor.setPause(false);

	}

	@Override
	public void dispose() {
		world.dispose();
		batch.dispose();
		stage.dispose();
		imgTramp.dispose();
		imgBall.dispose(); 
		imgWall.dispose(); 
		imgPaper.dispose();
		atlas.dispose();
		skin.dispose();
	}

	public float getHeight() {
		return height;
	}

}
