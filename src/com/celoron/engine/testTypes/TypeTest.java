package com.celoron.engine.testTypes;

import com.celoron.engine.basic.TextureRender;
import com.celoron.engine.core.Game;
import com.celoron.engine.physic.PhysicComp;
import com.celoron.testAngry.PhysicExtra;

public class TypeTest extends Game {

	@Override
	public void onCreate() {
		/* first: register components so asset manager can load them 
		 * NOTE: this will be someone else is work. not Game class*/
		asset.registerComponent(TextureRender.class, "TextureRender");
		asset.registerComponent(PhysicComp.class, "PhysicComp");
		asset.registerComponent(PhysicExtra.class, "PhysicExtra");
		
		/* second: load you file that contains type definition */
		asset.loadTypes("data/myTypes.xml");
		
		/* you can use types in scene file */
		loadScene("data/scene2.xml");
	}

	@Override
	public void onUpdate() {

	}

}
