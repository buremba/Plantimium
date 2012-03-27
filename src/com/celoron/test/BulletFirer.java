package com.celoron.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;

public class BulletFirer extends Component {
	private float timeToFire;
	public BulletFirer(String id) {
		super(id);
		timeToFire=0.1f;
	}

	@Override
	public void update() {
		timeToFire-=game.deltaTime;
		if(Gdx.input.isTouched()){
			if(timeToFire<0){
				fire();
			}
		}
	}
	
	public void fire(){
		Vector2 mpos= game.relativeMousePos();
		Vector2 v=mpos.sub(owner.getPosition()).nor().mul(100);
		
		Entity bullet= new Entity("bullet", game);
		bullet.setScale(0.5f);
		bullet.setPosition(owner.getPosition().cpy());
		bullet.AddComponent(new Bullet("bullets", v, 1));
		bullet.AddComponent(new TextureRender("render", new Texture(Gdx.files.internal("data/bullet.png")) ));
		game.sceneManager.addEntity(bullet);
		
		timeToFire=0.1f;
	}

	@Override
	public void start() {
		
	}

}
