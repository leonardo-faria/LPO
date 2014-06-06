package com.Lpoo.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class JumpEmCollision implements ContactListener{

	World world;
	
	public JumpEmCollision(World world){
		this.world = world;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();
		
		if(A.getUserData() == "Jumper" && B.getUserData() == "Trampoline")
			B.getBody().setUserData("destroy");
		
		if(B.getUserData() == "Jumper" && A.getUserData() == "Trampoline")
			A.getBody().setUserData("destroy");

		if(B.getUserData() == "Jumper" && A.getUserData() == "Floor")
			A.getBody().setUserData("loose");
		if(A.getUserData() == "Jumper" && B.getUserData() == "Floor")
			B.getBody().setUserData("loose");
	}

	@Override
	public void endContact(Contact contact) {
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
