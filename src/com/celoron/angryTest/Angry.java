package com.celoron.angryTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;
import com.celoron.test.PhysicComp;
import com.celoron.test.RectRender;
import com.celoron.test.TextureRender;

public class Angry extends Game {
	Entity sapan;
	@Override
	public void onCreate() {
		sapan= new Entity("spaan", this);
		sapan.setPosition(new Vector2(-200, 0));
		sapan.AddComponent(new TextureRender("render", asset.getTexture("data/city.png")));
		sapan.AddComponent(new Sapan("sapan"));
		
		scene.addEntity(sapan);
		
		createPlanes();
	}

	@Override
	public void onUpdate() {

	}
	
	public void createPlanes(){
		Entity e;
		
		e = new Entity("plane", this);
		e.setPosition(new Vector2(0,-Gdx.graphics.getHeight()/2));
		e.AddComponent(new PhysicComp("phy", new Vector2(Gdx.graphics.getWidth(),1), BodyType.StaticBody));
		e.AddComponent(new RectRender("render", new Vector2(Gdx.graphics.getWidth(),1)));
		scene.addEntity(e);
		
		/* SECOND */
		e = new Entity("plane", this);
		e.setPosition(new Vector2(Gdx.graphics.getWidth()/2,0));
		e.AddComponent(new PhysicComp("phy", new Vector2(1,Gdx.graphics.getHeight()), BodyType.StaticBody));
		e.AddComponent(new RectRender("render", new Vector2(1,Gdx.graphics.getHeight())));
		scene.addEntity(e);
		
		/* SECOND */
		e = new Entity("plane", this);
		e.setPosition(new Vector2(-Gdx.graphics.getWidth()/2,0));
		e.AddComponent(new PhysicComp("phy", new Vector2(1,Gdx.graphics.getHeight()), BodyType.StaticBody));
		e.AddComponent(new RectRender("render", new Vector2(1,Gdx.graphics.getHeight())));
		scene.addEntity(e);
		
		e = new Entity("plane", this);
		e.setPosition(new Vector2(0,Gdx.graphics.getHeight()/2));
		e.AddComponent(new PhysicComp("phy", new Vector2(Gdx.graphics.getWidth(),1), BodyType.StaticBody));
		e.AddComponent(new RectRender("render", new Vector2(Gdx.graphics.getWidth(),1)));
		scene.addEntity(e);
	}

}
