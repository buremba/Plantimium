package com.ahmet.b2d;

import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class Game extends GIcombin implements InputProcessor {
	private com.badlogic.gdx.graphics.OrthographicCamera camera;

	private Box2DDebugRenderer debugRenderer;

	private SpriteBatch batch;

	private World world;
	Body groundBody,hitBody;
	Mesh2d ak;
	Body2d bk;
	MouseJointDef def;
	MouseJoint mouseJoint;
	Rect a;
	RenderStack rs;
	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);
		camera.position.set(0, 10, 0);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		rs=new RenderStack();
		createPhysicsWorld();
		Gdx.input.setInputProcessor(this);
		
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
				new Vector2(-1,0),
				new Vector2(1, 0),
				new Vector2(1, 5),
				new Vector2(8, 10),
				new Vector2(5,10),
				new Vector2(0,6),
				new Vector2(-5,10),
				new Vector2(-8,10),
				new Vector2(-1,5)
		};
		
		vv=Tools.Triangulate(vv);
		ak=new Mesh2d(vv,new Vector2(0,5),new Vector2(1,3),new Vector3(255,0,0),true);
		bk=new Body2d(ak,world);
		a=new Rect(new Vector2(150,150),new Vector2(20,20),new Vector3(255,0,0),false);
		rs.stack.add(a);
	}

	@Override
	public void render () {
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		camera.update();
		camera.apply(gl);
		batch.getProjectionMatrix().set(camera.combined);
		camera.apply(Gdx.gl10);
		debugRenderer.render(world, camera.combined);
		
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.begin();
		rs.Draw();
		bk.Draw(null);
		batch.end();
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
	
	Vector3 testPoint = new Vector3();
	QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture (Fixture fixture) {
			// if the hit fixture's body is the ground body
			// we ignore it
			if (fixture.getBody() == groundBody) return true;

			// if the hit point is inside the fixture of the body
			// we report it
			if (fixture.testPoint(testPoint.x, testPoint.y)) {
				hitBody = bk.body;
				return false;
			} else
				return true;
		}
	};

	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {
		// translate the mouse coordinates to world coordinates
		testPoint.set(x, y, 0);
		camera.unproject(testPoint);

		// ask the world which bodies are within the given
		// bounding box around the mouse pointer
		hitBody = null;
		world.QueryAABB(callback, testPoint.x - 0.1f, testPoint.y - 0.1f, testPoint.x + 0.1f, testPoint.y + 0.1f);

		// if we hit something we create a new mouse joint
		// and attach it to the hit body.
		if (hitBody != null) {
			MouseJointDef def = new MouseJointDef();
			def.bodyA = groundBody;
			def.bodyB = hitBody;
			def.collideConnected = true;
			def.target.set(testPoint.x, testPoint.y);
			def.maxForce = 1000.0f * hitBody.getMass();

			mouseJoint = (MouseJoint)world.createJoint(def);
			hitBody.setAwake(true);
		}else
		{
			ak.addVertex(x, y);
			rs.stack.add(new Rect(new Vector2(x,Gdx.graphics.getHeight()-y),new Vector2(20,20),new Vector3(255,0,0),false));
		}

		return false;
	}

	/** another temporary vector **/
	Vector2 target = new Vector2();

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		// if a mouse joint exists we simply update
		// the target of the joint based on the new
		// mouse coordinates
		if (mouseJoint != null) {
			camera.unproject(testPoint.set(x, y, 0));
			mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
		}
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		// if a mouse joint exists we simply destroy it
		if (mouseJoint != null) {
			world.destroyJoint(mouseJoint);
			mouseJoint = null;
		}
		return false;
	}
}
