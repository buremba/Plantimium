package com.celoron.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;

public class BulletFirer extends Component {
	private float timeToFire;

	public BulletFirer(String id) {
		super(id);
		timeToFire = 0.1f;
	}

	@Override
	public void update() {
		timeToFire -= game.deltaTime;
		if (Gdx.input.isTouched()) {
			if (timeToFire < 0) {
				fire();
			}
		}
	}

	public void fire() {
		Vector2 mpos = game.relativeMousePos();
		Vector2 v = mpos.sub(owner.getPosition()).nor();
		Vector2 delta= v.cpy();
		delta.mul(30);

		Entity bullet = new Entity("bullet", game);
		bullet.setScale(0.5f);
		bullet.setPosition(owner.getPosition().cpy().add(delta));
		/*bullet.AddComponent(new TextureRender("render", new Texture(Gdx.files
				.internal("data/bullet.png"))));*/
		bullet.AddComponent(new RectRender("render", new Vector2(10,10) ));
		bullet.AddComponent(new PhysicComp("phy", new Vector2(10,10), BodyType.DynamicBody));
		bullet.AddComponent(new Bullet("bullets", v.mul(50000), 10));
		
		game.scene.addEntity(bullet);

		timeToFire = 0.1f;
	}

	@Override
	public void start() {

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
