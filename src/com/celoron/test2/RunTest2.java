package com.celoron.test2;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunTest2 {
	public static void main(String[] args) {
		new JoglApplication(new TestGame(), "Test Game", 800, 600, false);
	}
}
