package com.ahmet.polyshaping;

import com.celoron.engine.core.Game;

public class celogui extends Game {
	@Override
	public void onCreate(){
		loadScene("data/scene.xml");
	}

	@Override
	public void onUpdate() {
		System.out.println("asd");
	}
}