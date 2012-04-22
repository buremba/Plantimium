package com.celoron.testMario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.basic.Line;
import com.celoron.engine.basic.RectRender;
import com.celoron.engine.basic.TextureRender;
import com.celoron.engine.core.Entity;
import com.celoron.engine.core.Game;
import com.celoron.engine.physic.PhysicComp;

public class MarioGame extends Game {
	Entity mario;
	@Override
	public void onCreate() {
		mario= new Entity(this);
		float scale=0.7f;
		mario.setScale(scale);
		mario.addComponent(new TextureRender(asset.getTexture("data/mario.png")));
		mario.addComponent(new PhysicComp(new Vector2(64,64).mul(scale), BodyType.DynamicBody));
		mario.addComponent(new Mario());
		mario.addComponent(new Designer());
		scene.addEntity(mario);
		
		Entity dragger= new Entity(this);
		dragger.addComponent(new Line(new Vector2(), 3));
		scene.addEntity(dragger);
		
		MoveableBox.dragLine= dragger;
		MoveableBox.player=mario;
		
		Entity star= new Entity(this);
		star.addComponent(new TextureRender(asset.getTexture("data/star.png")));
		scene.addEntity(star);
		
		createPlanes();
	}

	@Override
	public void onUpdate() {
		
		/* camera sliding */
		float slideDistance=50.0f;
		Vector2 marioPos= mario.getPosition();
		if( marioPos.x-camera.position.x > slideDistance){
			camera.position.x= marioPos.x-slideDistance;
		}
		else if( marioPos.x-camera.position.x < -slideDistance){
			camera.position.x= marioPos.x+slideDistance;
		}
		
	}
	
	public void createPlanes(){
		Entity e;
		
		e = new Entity(this);
		e.setPosition(new Vector2(0,-Gdx.graphics.getHeight()/2));
		e.addComponent(new PhysicComp(new Vector2(Gdx.graphics.getWidth(),1), BodyType.StaticBody));
		e.addComponent(new RectRender(new Vector2(Gdx.graphics.getWidth(),1)));
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
