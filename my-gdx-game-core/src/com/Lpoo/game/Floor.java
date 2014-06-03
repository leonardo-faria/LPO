package com.Lpoo.game;

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
	float cx, cy;
	 
	 public Floor(World world, float cx, float cy) {
	  
	  this.cx = cx;
	  this.cy = cy;
	  
	  BodyDef bodyDef = new BodyDef();
	  bodyDef.type = BodyType.StaticBody;
	  bodyDef.position.set(cx, cy);
	  
	  PolygonShape shape = new PolygonShape();
	  shape.setAsBox(10,(float) 0.01);
	  
	  FixtureDef fixtureDef= new FixtureDef();
	  fixtureDef.shape = shape;
	  fixtureDef.friction = 1f;
	  
	  body = world.createBody(bodyDef);
	  fixture = body.createFixture(fixtureDef);
	 }
}