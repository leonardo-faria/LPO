package com.Lpoo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/*
 * TODO Criar atraso no desenho de trampolim
 */
public class TestScreen implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private JumpEmInputProcessor inputProcessor;
	private JumpEmCollision collisionProcessor;
	private Jumper jumper;
	private Jumper jumper2;
	private Floor floor;
	private Floor tr;

	private Floor top;
	private Wall left, right;
	private Trampoline test;
	private final float TIMESTEP = 1 / 60f;
	private final int VelocityIterations = 8, PositionIterations = 3;

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
			
			float length = (float) Math.sqrt((coordf.x - coord0.x) * (coordf.x - coord0.x)
					+ (coordf.y - coord0.y) * (coordf.y - coord0.y))/2;
			if(length>5)
				length=5;
			float angle = (float) Math.atan2(coordf.y - coord0.y, coordf.x - coord0.x);
			if(angle>Math.toRadians(30))
				angle=(float) Math.toRadians(30);
			if(angle<-Math.toRadians(30))
				angle=(float) -Math.toRadians(30);
			Vector2 center = new Vector2((float) ((coordf.x + coord0.x)*0.5),(float) ((coordf.y + coord0.y)*0.5));
			test = new Trampoline(world,center, length, angle);
			inputProcessor.setTouched(false);
		}

		Array<Body> bodies = new Array<Body>();
		 world.getBodies(bodies);
		 
		 for(int i = 0; i < bodies.size; i++)
		 {
			 if(bodies.get(i).getUserData() instanceof String)
				 world.destroyBody(bodies.get(i));
				 
		 }
		
		debugRenderer.render(world, camera.combined);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 10;
		camera.viewportHeight = height / 10;

	}

	public Jumper getJumper() {
		return jumper;
	}

	@Override
	public void show() {
		world = new World(new Vector2(0, -9f), true);

		inputProcessor = new JumpEmInputProcessor(world);
		collisionProcessor = new JumpEmCollision(world);
		world.setContactListener(collisionProcessor);
		Gdx.input.setInputProcessor(inputProcessor);

		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10,
				Gdx.graphics.getHeight() / 10);

//		jumper = new Jumper(world, 0, 0, 2);
		jumper = new Jumper(world, -3, 7, 1);
		jumper = new Jumper(world, 0, 5, 1);
		jumper = new Jumper(world, 0, 5, 1);
		jumper = new Jumper(world, 5, -2, 1);
		jumper = new Jumper(world, 2, 9, 1);
		jumper2 = new Jumper(world, -2, 4, 1);
		floor = new Floor(world, 0, -20);
		top = new Floor(world, 0, 20);
		left = new Wall(world, -10, 0, 20, 1, 0);
		right = new Wall(world, 10, 0, 20, 1, 0);

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