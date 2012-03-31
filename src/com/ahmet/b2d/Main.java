package com.ahmet.b2d;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JoglApplication(new Game(),"a",600,400,false);

	}

}
