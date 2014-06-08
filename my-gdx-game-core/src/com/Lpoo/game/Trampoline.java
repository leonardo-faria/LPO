package com.Lpoo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Trampoline {
	public Body body;
	Fixture fixture;
	float cx, cy, length, angle;
	World world;
	
	public Trampoline(World world, Vector2 center, float length, float angle, Texture img) {

		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(center);
		bodyDef.angle=angle;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(length , (float) 0.3);
		
		Sprite sprt = new Sprite(img);
		sprt.setSize(length*2,0.6f);

		sprt.setOriginCenter();
		sprt.setRotation(MathUtils.radiansToDegrees* angle);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 1.5f;
		body = world.createBody(bodyDef);
		body.setUserData(sprt);
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData("Trampoline");
	}


}


