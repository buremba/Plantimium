package com.celoron.test;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;

public class CreatorComp extends Component {
	private float timeToCreate=0;
	public CreatorComp(String id) {
		super(id);
	}

	@Override
	public void update() {
		timeToCreate-=game.deltaTime;
		if (Gdx.input.isTouched()) {
			if(timeToCreate<0)
				create();
		}
	}

	public void create() {
		Random generator = new Random();
		float scale= (float) (generator.nextDouble()+1)/10;
		
		Entity e = new Entity("falling box", game);
		e.setScale(scale);
		e.setPosition(game.relativeMousePos());
		e.AddComponent(new TextureRender("render", new Texture(Gdx.files.internal("data/box.jpg"))));
		e.AddComponent(new PhysicComp("phy", new Vector2(128, 128).mul(scale), BodyType.DynamicBody));
		//e.AddComponent(new RectRender("render", new Vector2(30, 30).mul(scale)));

		game.sceneManager.addEntity(e);
		
		timeToCreate=0.3f;
	}

	@Override
	public void start() {

	}

}
