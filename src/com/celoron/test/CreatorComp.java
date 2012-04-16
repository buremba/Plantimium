package com.celoron.test;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;

public class CreatorComp extends Component {
	private float timeToCreate;
	Random generator;
	
	static Texture t; /* temporarly */
	public CreatorComp() {
		timeToCreate=0;
		generator = new Random();
		
		t=new Texture(Gdx.files.internal("data/box.jpg"));
	}

	@Override
	public void update() {
		timeToCreate-=game.deltaTime;
		if(timeToCreate<0)
			create();
	}

	public void create() {
		float scale= (float) (generator.nextDouble()+1)/10;
		
		Entity e = new Entity(game);
		
		e.setScale(scale);
		
		/* trying to save memory */
		/*e.setPosition(new Vector2(-200+generator.nextInt(400), 200));*/
		e.getPosition().x = -Gdx.graphics.getWidth()/4+generator.nextInt(Gdx.graphics.getWidth()/2);
		e.getPosition().y = Gdx.graphics.getHeight()/4;
		
		e.addComponent(new TextureRender(t));
		e.addComponent(new PhysicComp(new Vector2(256, 256).mul(scale), BodyType.DynamicBody));
		
		//e.AddComponent(new RectRender("render", new Vector2(256, 256).mul(scale)));
		
		game.scene.addEntity(e);
		
		timeToCreate=1.0f;
	}

	@Override
	public void start() {

	}

	@Override
	public void remove() {
		
	}

}
