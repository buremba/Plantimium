package com.celoron.testAngry;

import com.badlogic.gdx.physics.box2d.Contact;
import com.celoron.engine.core.Component;
import com.celoron.engine.core.Entity;
import com.celoron.engine.physic.PhysicListener;
import com.celoron.testParticle.ParticleRenderComp;

public class Star extends Component implements PhysicListener{
	@Override
	public void update() {

	}

	@Override
	public void start() {

	}

	@Override
	public void remove() {
		
	}

	@Override
	public void onContact(Entity other, Contact contact) {
		if(other.getComponent(Box.class)!=null){ /* if other have a box component, its means its a f*cking box */
			/* then explode  */
			PhysicExtra physic= (PhysicExtra) owner.getComponent(PhysicExtra.class);
			if(physic != null) physic.explode(10);
			
			/* create explosion particle effect */
			Entity parTest= new Entity(game);
			parTest.setPosition(owner.getPosition());
			parTest.addComponent(new ParticleRenderComp("data/star.par", "data"));
			
			game.scene.addEntity(parTest);
		}
	}
}
