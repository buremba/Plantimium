package com.celoron.test;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;

public class MyGame extends Game {
	Entity player;
	@Override
	public void onCreate() {
		Entity e;
		
		e = new Entity(this);
		e.setPosition(new Vector2(0,-Gdx.graphics.getHeight()/2));
		e.addComponent(new PhysicComp(new Vector2(Gdx.graphics.getWidth(),1), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(Gdx.graphics.getWidth(),1)));
		scene.addEntity(e);
		
		/* SECOND */
		e = new Entity(this);
		e.setPosition(new Vector2(Gdx.graphics.getWidth()/2,0));
		e.addComponent(new PhysicComp(new Vector2(1,Gdx.graphics.getHeight()), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(1,Gdx.graphics.getHeight())));
		scene.addEntity(e);
		
		/* SECOND */
		e = new Entity(this);
		e.setPosition(new Vector2(-Gdx.graphics.getWidth()/2,0));
		e.addComponent(new PhysicComp(new Vector2(1,Gdx.graphics.getHeight()), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(1,Gdx.graphics.getHeight())));
		scene.addEntity(e);
		
		e = new Entity(this);
		e.setPosition(new Vector2(0,Gdx.graphics.getHeight()/2));
		e.addComponent(new PhysicComp(new Vector2(Gdx.graphics.getWidth(),1), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(Gdx.graphics.getWidth(),1)));
		scene.addEntity(e);
		
		Entity e2= new Entity(this);
		e2.addComponent(new CreatorComp());
		
		scene.addEntity(e2);
		
		player= new Entity(this);
		player.setPosition(new Vector2(0,0));
		player.addComponent(new PhysicComp(new Vector2(32,32), BodyType.DynamicBody));
		player.addComponent(new PlayerControl(100));
		player.addComponent(new BulletFirer());
		player.addComponent( new TextureRender(new Texture(Gdx.files.internal("data/badlogicsmall.jpg"))));
		
		scene.addEntity(player);
	}

	@Override
	public void onUpdate() {
		/*
		 * There is nothing i have to think. after all components doing all work
		 * :D
		 */
		
		Gdx.app.log("getAccelerometerX", ""+Gdx.input.getAccelerometerX() );
		Gdx.app.log("getAccelerometerY", ""+Gdx.input.getAccelerometerY() );
		//Gdx.app.log("getAccelerometerZ", ""+Gdx.input.getAccelerometerZ() );
		world.setGravity(new Vector2( (float)Gdx.input.getAccelerometerX()*100, (float)Gdx.input.getAccelerometerY()*100  ));
	}
}
