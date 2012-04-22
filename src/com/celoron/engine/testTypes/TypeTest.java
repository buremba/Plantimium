package com.celoron.engine.testTypes;

import com.celoron.engine.basic.TextureRender;
import com.celoron.engine.core.Entity;
import com.celoron.engine.core.Game;
import com.celoron.engine.physic.PhysicComp;

public class TypeTest extends Game {

	@Override
	public void onCreate() {
		/* first: register components so asset manager can load them 
		 * NOTE: this will be someone else is work. not Game class*/
		asset.registerComponent(TextureRender.class, "TextureRender");
		asset.registerComponent(PhysicComp.class, "PhysicComp");
		
		/* second: load you file that contains type definition */
		asset.loadTypes("data/myTypes.xml");
		
		/* and the last thing: create FUCKING entity from type definition */
		Entity star= asset.createEntity("Star");
		scene.addEntity(star);
		
		scene.addEntity(asset.createEntity("Plane"));
	}

	@Override
	public void onUpdate() {

	}

}
