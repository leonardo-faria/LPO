package com.Lpoo.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class used to create the floor so that when touced by jumpers, the game ends
 */
public class Floor {
	public Body body;
	Fixture fixture;
	float cx, cy, height;
	private float width;

	/**
	 * Constructor for objects of the class Floor
	 * @param world Where the body will be created
	 * @param cx Center (x) for the body 
	 * @param cy Center (y) for the body
	 * @param height Height of the Floor
	 * @param width Width of the Floor
	 * @param angle Angle of the floor with Axis
	 */
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

	/**
	 * Returns width of the Floor
	 * @return Width of the Floor
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Sets the width of the Floor
	 * @param width new width of the Floor
	 */
	public void setWidth(float width) {
		this.width = width;
	}
}
