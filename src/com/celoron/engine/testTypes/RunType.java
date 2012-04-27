package com.celoron.engine.testTypes;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunType {
	public static void main(String[] args) {
		new JoglApplication(new TypeTest(), "Type Test", 800, 600, false);
	}
}