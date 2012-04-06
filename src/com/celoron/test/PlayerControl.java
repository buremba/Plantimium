package com.celoron.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;

public class PlayerControl extends Component {
	private float speed;

	public PlayerControl(String id, float speed) {
		super(id);

		this.speed = speed;
	}

	@Override
	public void update() {
		PhysicComp pc= (PhysicComp) owner.getComponent(PhysicComp.class);
		if(pc == null){
			Gdx.app.log("a", "x");
			return;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			pc.body.applyForceToCenter(new Vector2(-1*speed,0));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			pc.body.applyForceToCenter(new Vector2(0,-1*speed));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			pc.body.applyForceToCenter(new Vector2(0,1*speed));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			pc.body.applyForceToCenter(new Vector2(1*speed,0));
		}

		/*
		Vector2 v = game.relativeMousePos().sub(owner.getPosition());
		double angle = Math.acos(v.nor().dot(new Vector2(1, 0)));
		if (v.y < 0)
			angle = 2 * Math.PI - angle;
			*/
		//owner.setRotation((float) (angle / Math.PI * 180) - 90);
	}

	@Override
	public void start() {
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
