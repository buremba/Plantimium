package com.celoron.test;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;

public class MyGame extends Game {

	@Override
	public void onCreate() {
		
		
		Entity e = new Entity("plane", this);
		e.setPosition(new Vector2(0,-100));
		
		e.AddComponent(new PhysicComp("phy", new Vector2(100,1), BodyType.StaticBody));
		e.AddComponent(new RectRender("render", new Vector2(200,2)));

		sceneManager.addEntity(e);
		
		e= new Entity("cliker", this);
		e.AddComponent(new CreatorComp("create"));
		
		sceneManager.addEntity(e);
	}

	@Override
	public void onUpdate() {
		/*
		 * There is nothing i haveto think. after all components doing all work
		 * :D
		 */
	}

	public static void main(String[] args) {
		new JoglApplication(new MyGame(), "My Game", 800, 600, false);
	}
}
