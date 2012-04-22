package com.celoron.testMario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.celoron.engine.core.Component;
import com.celoron.engine.core.Entity;
import com.celoron.engine.physic.PhysicComp;
import com.celoron.engine.physic.PhysicListener;

public class Mario extends Component implements InputProcessor, PhysicListener{
	PhysicComp pc;
	float maximumSpeed=10.0f;
	@Override
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			pc.body.applyForceToCenter(500, 0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			pc.body.applyForceToCenter(-500, 0);
		}
		
		Vector2 speed= pc.body.getLinearVelocity();
		if(speed.x>maximumSpeed)speed.x=maximumSpeed; else if(speed.x<-maximumSpeed)speed.x=-maximumSpeed;
		if(speed.y>maximumSpeed)speed.y=maximumSpeed; else if(speed.y<-maximumSpeed)speed.y=-maximumSpeed;
		pc.body.setLinearVelocity(speed);
	}

	@Override
	public void start() {
		pc.body.setFixedRotation(true);
	}
	
	public boolean canStart(Entity testOwner){
		pc= (PhysicComp) testOwner.getComponent(PhysicComp.class);
		if(pc==null){
			Gdx.app.log("Error", "mario component cant find physic component");
			return false;
		}
		return true;
	}

	@Override
	public void remove() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.SPACE){
			pc.body.applyForceToCenter(0, 10000);
			return true;
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public void onContact(Entity other, Contact contact) {
		//Gdx.app.log("Contact", "!!");
	}

}
