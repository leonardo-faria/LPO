package com.Lpoo.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Floor {
	public Body body;
	Fixture fixture;
	float cx, cy, height;
	private float width;

	public Floor(World world, float cx, float cy, float height, float width,
			float angle) {

		this.cx = cx;
		this.cy = cy;
		this.height = height;
		this.setWidth(width);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height, new Vector2(cx, cy), angle);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 1f;
		fixtureDef.density = 6;
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData("Floor");
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
}
