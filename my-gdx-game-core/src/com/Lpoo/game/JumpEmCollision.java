package com.Lpoo.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class JumpEmCollision implements ContactListener, ContactFilter{

	World world;

	public JumpEmCollision(World world){
		this.world = world;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();

		if(A.getUserData() == "Jumper" && B.getUserData() == "Trampoline")
			B.setUserData("destroy");

		if(B.getUserData() == "Jumper" && A.getUserData() == "Trampoline")
			A.setUserData("destroy");

		if(B.getUserData() == "Jumper" && A.getUserData() == "Floor")
			A.setUserData("lose");
		if(A.getUserData() == "Jumper" && B.getUserData() == "Floor")
			B.setUserData("lose");
		
		if(A.getUserData() == "Top")
			B.getBody().setLinearVelocity(B.getBody().getLinearVelocity().x,0);
		
		if(B.getUserData() == "Top")
			A.getBody().setLinearVelocity(A.getBody().getLinearVelocity().x,0);
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

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		
		if(fixtureB.getUserData() == "Jumper" && fixtureA.getUserData() == "Trampoline")
		{
			if(fixtureB.getBody().getLinearVelocity().y > 0)
				return false;
		}
		else if(fixtureA.getUserData() == "Jumper" && fixtureB.getUserData() == "Trampoline")
			if(fixtureA.getBody().getLinearVelocity().y > 0)
				return false;

		return true;
	}

}
