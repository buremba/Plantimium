package com.celoron.test;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;

public class MyGame extends Game {
	Entity player;
	@Override
	public void onCreate() {
		Entity e = new Entity("plane", this);
		e.setPosition(new Vector2(0,-100));
		
		e.AddComponent(new PhysicComp("phy", new Vector2(100,1), BodyType.StaticBody));
		e.AddComponent(new RectRender("render", new Vector2(100,1)));

		sceneManager.addEntity(e);
		
		Entity e2= new Entity("cliker", this);
		e2.AddComponent(new CreatorComp("create"));
		
		sceneManager.addEntity(e2);
		
		/*player= new Entity("player",this);
		player.setPosition(new Vector2(0,0));
		player.AddComponent(new PhysicComp("phy", new Vector2(32,32), BodyType.DynamicBody));
		player.AddComponent(new PlayerControl("controller", 1000000));
		player.AddComponent(new BulletFirer("firer"));
		player.AddComponent( new TextureRender("render", new Texture(Gdx.files.internal("data/badlogicsmall.jpg"))));
		
		sceneManager.addEntity(player);*/
	}

	@Override
	public void onUpdate() {
		/*
		 * There is nothing i have to think. after all components doing all work
		 * :D
		 */
	}

	public static void main(String[] args) {
		new JoglApplication(new MyGame(), "My Game", 800, 600, false);
	}
}
