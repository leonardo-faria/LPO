package com.Lpoo.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.corba.se.impl.interceptors.PIHandlerImpl;

public class Trampoline {
	public Body body;
	Fixture fixture;
	float cx, cy, length, angle;
	World world;
	
	public Trampoline(World world, Vector2 pi, Vector2 pf) {

		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		Vector2 v[];
		v = new Vector2[2];
		v[0] = new Vector2(pi);
		v[1] = new Vector2(pf);
		length = (float) Math.sqrt((pf.x - pi.x) * (pf.x - pi.x)
				+ (pf.y - pi.y) * (pf.y - pi.y))/2;
		angle = (float) Math.atan2(pf.y - pi.y, pf.x - pi.x);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(length , (float) 0.1, new Vector2(
				(float) ((pf.x + pi.x) * 0.5), (float) ((pf.y + pi.y) * 0.5)),
				angle);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 2f;
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData("Trampoline");
	}


}


