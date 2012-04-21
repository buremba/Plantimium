package com.celoron.testAngry;

import com.badlogic.gdx.Gdx;
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
		Entity parTest= new Entity(game);
		ParticleRenderComp pe= new ParticleRenderComp("data/star.par", "data");
		parTest.addComponent(pe);
		pe.getParticle().setPosition(owner.getPosition().x, owner.getPosition().y);
		
		game.scene.addEntity(parTest);
	}

	@Override
	public void onContact(Entity other, Contact contact) {
		Gdx.app.log("contact", "i have contacted with another entity");
		
		if(other.getComponent(Box.class)!=null){ /* if other have a box component, its means its a f*cking box */
			/* then explode  */
			PhysicExtra physic= (PhysicExtra) owner.getComponent(PhysicExtra.class);
			if(physic != null) physic.explode(10);
		}
		
	}

}
