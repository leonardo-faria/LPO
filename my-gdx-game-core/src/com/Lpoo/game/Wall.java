package com.Lpoo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall {
	public Body body;
	Fixture fixture;
	float cx, cy, height, width;

	
	public Wall(World world, float cx, float cy, float height, float width,
			float angle,String user) {

		this.cx = cx;
		this.cy = cy;
		this.height = height;
		this.width = width;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(cx, cy);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);

		Texture img = new Texture(Gdx.files.internal("img/wood.png"));
		Sprite sprt = new Sprite(img);
		sprt.setSize(width*2,height*2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 1f;
		fixtureDef.density = 6;
		body = world.createBody(bodyDef);
		body.setUserData(sprt);
		fixture = body.createFixture(fixtureDef);
		fixture.setUserData(user);
	}
	
	
}
