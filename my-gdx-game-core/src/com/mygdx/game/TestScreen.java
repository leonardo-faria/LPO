package com.Lpoo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class TestScreen implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private JumpEmInputProcessor inputProcessor;
	private Jumper jump;
	private Floor floor;
	private Floor tr;
	
	private Floor top;
	private Wall left,right;
	private final float TIMESTEP = 1 / 60f;
	private final int VelocityIterations = 8, PositionIterations = 3;

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VelocityIterations, PositionIterations);
		
		
		debugRenderer.render(world, camera.combined);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 10;
		camera.viewportHeight = height / 10;

	}

	public Jumper getJumper() {
		return jump;
	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -15f), true);
		
		inputProcessor = new JumpEmInputProcessor(world);
		Gdx.input.setInputProcessor(inputProcessor);
		
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10,
				Gdx.graphics.getHeight() / 10);

		jump = new Jumper(world, 0, 0);
		floor = new Floor(world, 0, -20);
		top = new Floor(world, 0, 20);
		left = new Wall(world, -10, 0);
		right = new Wall(world, 10, 0);
		jump.getBody().applyTorque(1000, true);
		jump.getBody().applyForceToCenter(10000, -20000, true);
		
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