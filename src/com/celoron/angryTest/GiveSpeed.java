package com.celoron.angryTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;
import com.celoron.test.PhysicComp;

/* give linear speed to entitys physics component after create */
public class GiveSpeed extends Component {
	Vector2 speed;
	public GiveSpeed(String id, Vector2 speed) {
		super(id);
		this.speed=speed;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void start() {
		PhysicComp pc= (PhysicComp) owner.getComponent(PhysicComp.class);
		if(pc == null){
			Gdx.app.log("give speed", "cant find physics component");
			return;
		}
		pc.body.setLinearVelocity(speed);
		owner.removeComponent(this);
	}

	@Override
	public void remove() {

	}

}