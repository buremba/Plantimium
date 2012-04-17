package com.celoron.testGui;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunGuiTest {
	public static void main(String[] args) {
		new JoglApplication(new GuiTest(), "Gui Test", 800, 600, false);
	}
}
