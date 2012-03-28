package com.celoron.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.celoron.engine.Component;

public class PhysicComp extends Component {
	public Body body;
	public PolygonShape polygonShape;
	public BodyDef bodyDef;
	
	private BodyType btype;
	
	public PhysicComp(String id, Vector2 dim, BodyType btype) {
		super(id);
		
		this.btype=btype;
		polygonShape = new PolygonShape();
		polygonShape.setAsBox(dim.x, dim.y);
	}

	@Override
	public void start() {
		bodyDef = new BodyDef();
		bodyDef.type = btype;
		bodyDef.position.set(owner.getPosition().x, owner.getPosition().y);
		bodyDef.angle = (float) (0/*Math.PI*/);
		bodyDef.allowSleep = false;
		body = game.world.createBody(bodyDef);
		body.createFixture(polygonShape, 5.0f);
		//body.applyForce(new Vec2(), new Vec2());
	}

	@Override
	public void update() {
		owner.setPosition(body.getPosition());
		owner.setRotation((float) (body.getAngle()*180/Math.PI));
	}

}
