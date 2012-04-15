package com.celoron.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.celoron.engine.Component;

public class PhysicComp extends Component {
	public Body body;
	private PolygonShape shape;
	
	/* for more information about Body Definition, please read bullet2d manual. 
	 * bodyDef doesnt have to be static. but i want to save memory.*/
	private static BodyDef bodyDef = new BodyDef();
	
	private BodyType btype;
	
	public static final float BOX2D_SCALE=30.0f; /* pixel per meter. exmpl: 30 pixel=1 meter */
	
	public PhysicComp(String id, Vector2 dim, BodyType btype) {
		super(id);
		
		this.btype=btype;
		shape = new PolygonShape();
		shape.setAsBox(dim.x/2/BOX2D_SCALE, dim.y/2/BOX2D_SCALE); /* im dividing by 2 because box2d dimension system */
	}

	@Override
	public void start() {
		bodyDef.type = btype;
		bodyDef.angle=(float) (owner.getRotation()/90.0 * Math.PI);
		bodyDef.position.set(owner.getPosition().x/BOX2D_SCALE, owner.getPosition().y/BOX2D_SCALE);
		bodyDef.allowSleep = true;
		body = game.world.createBody(bodyDef);
		
		MassData mass= new MassData();
		mass.mass=10;
		body.setMassData(mass);
		body.createFixture(shape, 5.0f);
		//body.applyForce(new Vec2(), new Vec2());
	}

	@Override
	public void update() {
		owner.setPosition(body.getPosition().mul(BOX2D_SCALE));
		owner.setRotation((float) (body.getAngle()*180/Math.PI));
	}

	@Override
	public void remove() {
		game.world.destroyBody(body);
	}

}
