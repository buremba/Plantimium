package com.celoron.testAngry;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunAngry {
	public static void main(String[] args) {
		new JoglApplication(new Angry(), "Angry Test", 800, 600, false);
	}
}
