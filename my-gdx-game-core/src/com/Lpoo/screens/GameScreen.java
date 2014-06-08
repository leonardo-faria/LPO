package com.Lpoo.screens;

import com.Lpoo.game.Floor;
import com.Lpoo.game.JumpEm;
import com.Lpoo.game.JumpEmCollision;
import com.Lpoo.game.JumpEmInputProcessor;
import com.Lpoo.game.Jumper;
import com.Lpoo.game.Trampoline;
import com.Lpoo.game.Wall;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * TODO: track ball position
 */

public class GameScreen implements Screen {
	private World world;
	private Box2DDebugRenderer debugRenderer;
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

	public TextButton pauseButton, quitButton;

	private float jumperRadius;
	private long startTime;
	private int score;
	private int trampolineNumber;
	float height;
	float width;

	@Override
	public void render(float delta) {

		world.step(TIMESTEP, VelocityIterations, PositionIterations);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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
			if (length > 15)
				length = 15;
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
		sprite.setSize(width/2, height/2);
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
				JumpEm.lastTime = (int) TimeUtils
						.timeSinceMillis(startTime);
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

		imgTramp = new Texture(Gdx.files.internal("img/wood.png"));
		imgBall = new Texture(Gdx.files.internal("img/redBall.png"));
		imgWall = new Texture(Gdx.files.internal("img/wood.png"));
		imgPaper = new Texture(Gdx.files.internal("img/forest.png"));
		sprite = new Sprite(imgPaper);

		pauseTable = new Table(skin);
		pauseTable.setFillParent(true);

		quitTable = new Table(skin);
		quitTable.setFillParent(true);

		titleTable = new Table(skin);
		titleTable.setFillParent(true);

		BitmapFont white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
		BitmapFont whiteSmall = new BitmapFont(Gdx.files.internal("font/white16.fnt"), false);

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("wood");
		textButtonStyle.down = skin.getDrawable("wood");
		textButtonStyle.font = whiteSmall;
		textButtonStyle.fontColor = Color.WHITE;

		pauseButton = new TextButton("Pause",textButtonStyle);
		quitButton = new TextButton("Quit", textButtonStyle);

		pauseTable.add(pauseButton);
		pauseTable.top().left();
		pauseTable.debug();

		quitTable.add(quitButton);
		quitTable.bottom().left();
		stage.addActor(pauseTable);




		headingPause = new Label("PAUSED", new LabelStyle(white, Color.WHITE));
		headingPause.setFontScale(1.9f);

		titleTable.add(headingPause);
		titleTable.center();

		startTime = TimeUtils.millis();
		score = 0;
		world = new World(new Vector2(0, -9.8f), true);

		inputProcessor = new JumpEmInputProcessor(this);
		collisionProcessor = new JumpEmCollision(world);
		world.setContactListener(collisionProcessor);
		world.setContactFilter(collisionProcessor);

		Gdx.input.setInputProcessor(inputProcessor);

		debugRenderer = new Box2DDebugRenderer();
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10,
				Gdx.graphics.getHeight() / 10);

		Vector3 size = new Vector3(width, height, 0);
		camera.unproject(size);

		jumperRadius = (float) (size.x / 25.0);
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
		stage.addActor(titleTable);
		stage.addActor(quitTable);
		TIMESTEP = 0;
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		TIMESTEP = 1 / 60f;
		stage.clear();
		stage.addActor(pauseTable);
		inputProcessor.setPause(false);
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();
	}

	public float getHeight() {
		return height;
	}

}
