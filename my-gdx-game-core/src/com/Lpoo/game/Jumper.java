package com.Lpoo.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Jumper{

 public Body body;
 Fixture fixture;
 float cx,cy,r;
 
public Jumper(World world, float cx, float cy, float r) {
  
  this.cx = cx;
  this.cy = cy;
  this.r=r;
  
  BodyDef bodyDef = new BodyDef();
  bodyDef.type = BodyType.DynamicBody;
  bodyDef.position.set(cx, cy);
  

  CircleShape shape = new CircleShape();
  shape.setRadius(r);
  
  FixtureDef fixtureDef= new FixtureDef();
  fixtureDef.shape = shape;
  fixtureDef.restitution = 1f;
  fixtureDef.friction = 1f;
  fixtureDef.density = 6;
  
  body = world.createBody(bodyDef);
  fixture = body.createFixture(fixtureDef);
  fixture.setUserData("Jumper");
 }

public Body getBody() {
	return body;
}

public void setBody(Body body) {
	this.body = body;
}
 
 
 
 
 
 
}