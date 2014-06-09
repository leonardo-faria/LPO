package com.Lpoo.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *  Class used to create the jumpers as dynamic Bodies
 */
public class Jumper{

	public Body body;
	Fixture fixture;
	float cx, cy, r;

	/**
	 * Creates a object of the class Jumper
	 * @param world Where the body will be created
	 * @param cx Center of the jumper
	 * @param cy Center of the jumper
	 * @param r Radius of the jumper
	 * @param img Texture used for the jumper
	 */
	public Jumper(World world, float cx, float cy, float r, Texture img) {

		this.cx = cx;
		this.cy = cy;
		this.r = r;
		

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(cx, cy);

		CircleShape shape = new CircleShape();
		shape.setRadius(r);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 1f;
		fixtureDef.friction = 1f;
		fixtureDef.density = 6;
		
		Sprite sprt = new Sprite(img);
		sprt.setSize(2*r, 2*r);
		sprt.setOrigin(r,r);
		

		body = world.createBody(bodyDef);
		body.setUserData(sprt);
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData("Jumper");
		
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Vector2 getCenter() {
		return new Vector2(cx,cy);
	}

}
