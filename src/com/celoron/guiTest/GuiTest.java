package com.celoron.guiTest;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Game;
import com.celoron.engine.GuiButton;

public class GuiTest extends Game {
	/* for dragging */

	@Override
	public void onCreate() {
		GuiButton b= new GuiButton(this, new Vector2(0,0), asset.getTexture("data/bNormal.png"), asset.getTexture("data/bHover.png"));
		gui.addEntity(b);
	}

	@Override
	public void onUpdate() {
	}

	public static void main(String[] args) {
		new JoglApplication(new GuiTest(), "Gui Test", 800, 600, false);
	}
}