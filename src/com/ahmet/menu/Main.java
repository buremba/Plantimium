package com.ahmet.menu;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JoglApplication(new Menu(),"a",480,320,false);

	}

}

