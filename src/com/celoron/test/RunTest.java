package com.celoron.test;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunTest {
	public static void main(String[] args) {
		new JoglApplication(new MyGame(), "My Game", 500, 400, false);
	}
}
