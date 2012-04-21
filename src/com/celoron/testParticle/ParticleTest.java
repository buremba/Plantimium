package com.celoron.testParticle;

import com.celoron.engine.core.Entity;
import com.celoron.engine.core.Game;

public class ParticleTest extends Game {

	@Override
	public void onCreate() {
		Entity parTest= new Entity(this);
		parTest.addComponent(new ParticleRenderComp("data/p.par", "data"));
		parTest.addComponent(new ParticleManagerComp());
		
		
		scene.addEntity(parTest);
	}

	@Override
	public void onUpdate() {
	}
	
	public void onRender(){
		
	}
}