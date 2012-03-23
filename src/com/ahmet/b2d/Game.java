package com.ahmet.b2d;

import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

public class Game extends GIcombin {
	private com.badlogic.gdx.graphics.OrthographicCamera camera;

	private Box2DDebugRenderer debugRenderer;

	private SpriteBatch batch;

	private World world;
	Body groundBody;
	Mesh2d ak;
	Body2d bk;
	@Override
	public void create () {
		camera = new OrthographicCamera(48, 32);
		camera.position.set(0, 5, 0);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		createPhysicsWorld();
		
	}

	private void createPhysicsWorld () {

		world = new World(new Vector2(0, -10), true);
		PolygonShape groundPoly = new PolygonShape();
		groundPoly.setAsBox(50, 1);
		
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		groundBody = world.createBody(groundBodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundPoly;
		fixtureDef.filter.groupIndex = 0;
		groundBody.createFixture(fixtureDef);
		groundPoly.dispose();

		
		Vector2[] vv={
				new Vector2(-1, 5),
				new Vector2(-1, 0),
				new Vector2(1, 0),
				new Vector2(1, 5),
				new Vector2(4, 9),
				new Vector2(3, 10),
				new Vector2(0, 5),
				new Vector2(-3, 10),
				new Vector2(-4, 9)						
		};

		vv=Tools.Triangulate(vv);
		ak=new Mesh2d(vv,new Vector2(0,3),new Vector2(1,3),new Vector3(255,0,0),true);
		bk=new Body2d(ak,world);
	}

	@Override
	public void render () {
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
		camera.update();
		camera.apply(gl);
		
		batch.getProjectionMatrix().set(camera.combined);
		ak.angle=(float) Math.toDegrees(bk.body.getAngle());
		ak.position=bk.body.getPosition();
		ak.Draw(null);
		camera.apply(Gdx.gl10);
		debugRenderer.render(world, camera.combined);
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	@Override
	public void dispose () {
		world.dispose();
	}
	@Override
	public void pause() {}
	@Override
	public void resize(int arg0, int arg1) {}
	@Override
	public void resume() {}
}
