package com.celoron.testAngry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.basic.PhysicComp;
import com.celoron.engine.basic.RectRender;
import com.celoron.engine.basic.TextRender;
import com.celoron.engine.basic.TextureRender;
import com.celoron.engine.core.Entity;
import com.celoron.engine.core.Game;

public class Angry extends Game {
	Entity sapan;
	TextRender fpsText;
	
	float timePassed=0;
	int fpsCount=0;
	float timeToRefreshFps=0;
	float refreshTime=0.3f;
	@Override
	public void onCreate() {
		sapan= new Entity(this);
		sapan.setPosition(new Vector2(-200, 0));
		sapan.addComponent(new TextureRender(asset.getTexture("data/city.png")));
		sapan.addComponent(new Sapan());
		
		scene.addEntity(sapan);
		
		createPlanes();
		
		Entity fps= new Entity(this);
		fpsText=new TextRender("Fps", new Color(1,1,1,1));
		fps.addComponent(fpsText);
		fps.setPosition(new Vector2(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
		scene.addEntity(fps);
		
		ContactListener listener= new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
			}
			
			@Override
			public void endContact(Contact contact) {
				
			}
			
			@Override
			public void beginContact(Contact contact) {				
				Object data =contact.getFixtureA().getUserData();
				if(data instanceof Entity && ((Entity)data).getName()!=null){
					//Gdx.app.log("contact A:", ""+((Entity)data).getName());
				}
				data =contact.getFixtureA().getUserData();
				if(data instanceof Entity && ((Entity)data).getName()!=null){
					//Gdx.app.log("contact B:", ""+((Entity)data).getName());
				}
			}
		};
		world.setContactListener(listener);
	}

	@Override
	public void onUpdate() {
		timePassed+=deltaTime;
		fpsCount++;
		
		timeToRefreshFps-=deltaTime;
		if(timeToRefreshFps<0){
			fpsText.setText("Fps: "+(int)(fpsCount/timePassed));
			timeToRefreshFps=refreshTime;
			
			timePassed=0;
			fpsCount=0;
		}
		//world.setGravity(new Vector2( (float)Gdx.input.getAccelerometerX(), (float)Gdx.input.getAccelerometerY()  ));
	}
	
	public void createPlanes(){
		Entity e;
		
		e = new Entity(this);
		e.setPosition(new Vector2(0,-Gdx.graphics.getHeight()/2));
		e.addComponent(new PhysicComp(new Vector2(Gdx.graphics.getWidth(),1), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(Gdx.graphics.getWidth(),1)));
		scene.addEntity(e);
		
		e = new Entity(this);
		e.setPosition(new Vector2(Gdx.graphics.getWidth()/2,0));
		e.addComponent(new PhysicComp(new Vector2(1,Gdx.graphics.getHeight()), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(1,Gdx.graphics.getHeight())));
		scene.addEntity(e);
		
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
	}
}
