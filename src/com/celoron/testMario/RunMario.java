package com.celoron.testMario;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunMario {
	public static void main(String[] args) {
		new JoglApplication(new MarioGame(), "Mario", 800, 600, false);
	}
}
