package com.celoron.test;

import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;

public class Bullet extends Component {
	private Vector2 speed;
	private float life;

	public Bullet(String id, Vector2 speed, float life) {
		super(id);

		this.speed = speed;
		this.life = life;
	}

	@Override
	public void update() {
		life -= game.deltaTime;
		//owner.getPosition().add(speed.cpy().mul(game.deltaTime));

		if (life < 0) {
			game.sceneManager.removeEntity(owner);
		}
	}

	@Override
	public void start() {
		PhysicComp pc= (PhysicComp) owner.getComponent(PhysicComp.class);
		if(pc != null){
			pc.body.setLinearVelocity(speed);
		}
	}

	@Override
	public void remove() {
		
	}

}
