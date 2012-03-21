package com.ahmet.b2d;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer10;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class Game extends GIcombin {
	private com.badlogic.gdx.graphics.OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private World world;
	private ArrayList<Body> boxes = new ArrayList<Body>();
	Body groundBody,a,b;
	private DistanceJoint mouseJoint = null;
	Body hitBody = null;
	int i=0;
	PolygonShape ps;
	List<Vector2> vertices;
	@Override
	public void create () {
		camera = new OrthographicCamera(48, 32);
		camera.position.set(0, 16, 0);
		debugRenderer = new Box2DDebugRenderer();
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
		
		EarClippingTriangulator ect=new EarClippingTriangulator();
		vertices=new ArrayList<Vector2>();

		vertices.add(new Vector2(-1, 5));
		vertices.add(new Vector2(-1, 0));
		vertices.add(new Vector2(1, 0));
		vertices.add(new Vector2(1, 5));
		vertices.add(new Vector2(4, 9));
		vertices.add(new Vector2(3, 10));
		vertices.add(new Vector2(0, 5));
		vertices.add(new Vector2(-3, 10));
		vertices.add(new Vector2(-4, 9));
		
		vertices=ect.computeTriangles(vertices);
		
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;
		boxBodyDef.position.x = 0;
		boxBodyDef.position.y = 5;
		Body boxBody = world.createBody(boxBodyDef);

		for(int i=0; i<vertices.size(); i+=3)
		{
			ps = new PolygonShape();
			Vector2[] temp=new Vector2[] {
				new Vector2(vertices.get(i+2).x,vertices.get(i+2).y),
				new Vector2(vertices.get(i+1).x,vertices.get(i+1).y),
				new Vector2(vertices.get(i).x,vertices.get(i).y)
			};
			ps.set(temp);
			boxBody.createFixture(ps, 1);
		}
		a=boxBody;
		DistanceJointDef def = new DistanceJointDef();
		def.collideConnected = true;		
		def.initialize(a, groundBody, new Vector2(0,5), new Vector2(0,1));
		def.length=0.2f;
		mouseJoint = (DistanceJoint)world.createJoint(def);
		groundBody.setAwake(true);
	}

	@Override
	public void render () {
		i++;
		if(i==100)
		{
			BodyDef boxBodyDef = new BodyDef();
			boxBodyDef.type = BodyType.DynamicBody;
			boxBodyDef.position.x = 4;
			boxBodyDef.position.y = 15;
			Body boxBody = world.createBody(boxBodyDef);
			for(int i=0; i<vertices.size(); i+=3)
			{
				ps = new PolygonShape();
				Vector2[] temp=new Vector2[] {
					new Vector2(vertices.get(i+2).x/2,vertices.get(i+2).y/2),
					new Vector2(vertices.get(i+1).x/2,vertices.get(i+1).y/2),
					new Vector2(vertices.get(i).x/2,vertices.get(i).y/2)
				};
				ps.set(temp);
				boxBody.createFixture(ps, 1);
			}
			b=boxBody;
			DistanceJointDef def = new DistanceJointDef();
			def.collideConnected = true;		
			def.initialize(a, b, new Vector2(3,10), new Vector2(4,15));
			def.length=0.5f;
			mouseJoint = (DistanceJoint)world.createJoint(def);
			b.setAwake(true);
			
		}
		long start = System.nanoTime();
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		camera.apply(gl);
		batch.getProjectionMatrix().set(camera.combined);

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
