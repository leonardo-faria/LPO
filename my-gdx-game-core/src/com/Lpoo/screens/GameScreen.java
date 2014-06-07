package com.Lpoo.screens;

import com.Lpoo.game.Floor;
import com.Lpoo.game.JumpEm;
import com.Lpoo.game.JumpEmCollision;
import com.Lpoo.game.JumpEmInputProcessor;
import com.Lpoo.game.Jumper;
import com.Lpoo.game.Trampoline;
import com.Lpoo.game.Wall;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * TODO Criar atraso no desenho de trampolim
 */
public class GameScreen implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private JumpEmInputProcessor inputProcessor;
	private JumpEmCollision collisionProcessor;
	private Floor floor;
	private Array<Jumper> jumpers;

	private Wall top;
	private Wall left, right;
	private Trampoline test;
	private final float TIMESTEP = 1 / 60f;
	private final int VelocityIterations = 2, PositionIterations = 2;

	private int mode;
	private long jumperRadius;
	private long startTime;
	private int score;
	float height;
	float width;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(TIMESTEP, VelocityIterations, PositionIterations);

		if (inputProcessor.getTouched()) {
			float x0 = inputProcessor.getX0();
			float y0 = inputProcessor.getY0();
			float xf = inputProcessor.getXf();
			float yf = inputProcessor.getYf();
			Vector3 coord0 = new Vector3(x0, y0, 0);
			Vector3 coordf = new Vector3(xf, yf, 0);
			camera.unproject(coord0);
			camera.unproject(coordf);

			float length = (float) Math.sqrt((coordf.x - coord0.x) * (coordf.x - coord0.x) + (coordf.y - coord0.y)
					* (coordf.y - coord0.y)) / 2;
			if (length > 15)
				length = 15;
			if (length < 3)
				length = 3;
			float angle = (float) Math.atan2(coordf.y - coord0.y, coordf.x - coord0.x);
			if (angle > Math.PI / 2.0) {
				angle = (float) -(Math.PI - angle);
			} else if (angle < -Math.PI / 2.0) {
				angle = (float) (Math.PI + angle);
			}
			if (angle > Math.toRadians(30))
				angle = (float) Math.toRadians(30);
			if (angle < -Math.toRadians(30))
				angle = (float) -Math.toRadians(30);
			Vector2 center = new Vector2((float) ((coordf.x + coord0.x) * 0.5), (float) ((coordf.y + coord0.y) * 0.5));
			test = new Trampoline(world, center, length, angle);
			inputProcessor.setTouched(false);
		}

		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);

		for (int i = 0; i < bodies.size; i++) {
			if (bodies.get(i).getUserData() == "destroy") {
				world.destroyBody(bodies.get(i));
				int r = MathUtils.random(100);
				if (r > 35) {
					score++;
					jumpers.add(new Jumper(world, MathUtils.random((float) -(floor.getWidth() * 0.5),
							(float) (floor.getWidth() * 0.5)), 0, jumperRadius));
				}
			} else if (bodies.get(i).getUserData() == "lose") {
				JumpEm.lastTime = (int) TimeUtils.timeSinceMillis(startTime);
				JumpEm.lastScore = score;
				((Game) Gdx.app.getApplicationListener()).setScreen(new LoseScreen());
			}

		}

		debugRenderer.render(world, camera.combined);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 10;
		camera.viewportHeight = height / 10;

	}

	@Override
	public void show() {

		startTime = TimeUtils.millis();
		score = 0;
		world = new World(new Vector2(0, -9f), true);

		inputProcessor = new JumpEmInputProcessor(world);
		collisionProcessor = new JumpEmCollision(world);
		world.setContactListener(collisionProcessor);
		world.setContactFilter(collisionProcessor);

		Gdx.input.setInputProcessor(inputProcessor);

		debugRenderer = new Box2DDebugRenderer();
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

		Vector3 size = new Vector3(width, height, 0);
		camera.unproject(size);
		if (Gdx.app.getType() == ApplicationType.Android) {
			float temp;
			temp = -size.x;
			size.x=-size.y;
			size.y=temp;
			camera.rotate(90);
		}
		jumperRadius=(long) (size.x/25.0);
		left = new Wall(world, -size.x, 0, Math.abs(size.y) * 3, 1, 0);
		right = new Wall(world, size.x, 0, Math.abs(size.y) * 3, 1, 0);
		floor = new Floor(world, 0, (float) (size.y - 1), 1, size.x, 0);
		top = new Wall(world, 0, -size.y * 2, 1, size.x, 0);
		jumpers = new Array<Jumper>();
		jumpers.add(new Jumper(world, 0, 0, jumperRadius));

	}

	@Override
	public void hide() {

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
		world.dispose();
		debugRenderer.dispose();
	}

}
