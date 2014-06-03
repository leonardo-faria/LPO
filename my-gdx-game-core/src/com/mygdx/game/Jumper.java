package com.mygdx.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Jumper{

 public Body body;
 Fixture fixture;
 float cx,cy;
 
 public Jumper(World world, float cx, float cy) {
  
  this.cx = cx;
  this.cy = cy;
  
  BodyDef bodyDef = new BodyDef();
  bodyDef.type = BodyType.DynamicBody;
  bodyDef.position.set(cx, cy);
  

  CircleShape shape = new CircleShape();
  shape.setRadius(1);
  
  FixtureDef fixtureDef= new FixtureDef();
  fixtureDef.shape = shape;
  fixtureDef.restitution = 1f;
  fixtureDef.friction = 1f;
  fixtureDef.density = 6;
  
  body = world.createBody(bodyDef);
  fixture = body.createFixture(fixtureDef);
 }

public Body getBody() {
	return body;
}

public void setBody(Body body) {
	this.body = body;
}
 
 
 
 
 
 
}