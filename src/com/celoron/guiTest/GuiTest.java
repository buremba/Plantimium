package com.celoron.guiTest;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.celoron.engine.Game;

public class GuiTest extends Game {
	@Override
	public void onCreate() {
		loadScene("data/scene.xml");
	}

	@Override
	public void onUpdate() {
		
	}

	public static void main(String[] args) {
		new JoglApplication(new GuiTest(), "Gui Test", 800, 600, false);
	}
}