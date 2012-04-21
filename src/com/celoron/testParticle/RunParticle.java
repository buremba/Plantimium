package com.celoron.testParticle;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class RunParticle {
	public static void main(String[] args) {
		new JoglApplication(new ParticleTest(), "Particle Test", 800, 600, false);
	}
}