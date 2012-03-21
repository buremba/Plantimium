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

public class Game extends GIcombin implements InputProcessor {
	private com.badlogic.gdx.graphics.OrthographicCamera camera;
	private ImmediateModeRenderer10 renderer;
	private Box2DDebugRenderer debugRenderer;

	private SpriteBatch batch;
	private BitmapFont font;
	private TextureRegion textureRegion;

	private World world;
	private ArrayList<Body> boxes = new ArrayList<Body>();
	Body groundBody;
	private DistanceJoint mouseJoint = null;
	Body hitBody = null;

	@Override
	public void create () {
		camera = new OrthographicCamera(48, 32);
		camera.position.set(0, 16, 0);
		renderer = new ImmediateModeRenderer10();
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		textureRegion = new TextureRegion(new Texture(Gdx.files.internal("data/badlogicsmall.jpg")));
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
		
		EarClippingTriangulator ect=new EarClippingTriangulator();
		List<Vector2> vertices=new ArrayList<Vector2>();

		vertices.add(new Vector2(-1, 5));
		vertices.add(new Vector2(-1, 0));
		vertices.add(new Vector2(1, 0));
		vertices.add(new Vector2(1, 5));
		vertices.add(new Vector2(4, 10));
		vertices.add(new Vector2(3, 10));
		vertices.add(new Vector2(0, 5));
		vertices.add(new Vector2(-3, 10));
		vertices.add(new Vector2(-4, 10));
		
		vertices=ect.computeTriangles(vertices);
		
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;
		boxBodyDef.position.x = 0;
		boxBodyDef.position.y = 5;
		Body boxBody = world.createBody(boxBodyDef);

		for(int i=0; i<vertices.size(); i+=3)
		{
			PolygonShape ps = new PolygonShape();
			Vector2[] temp=new Vector2[] {
				new Vector2(vertices.get(i+2).x,vertices.get(i+2).y),
				new Vector2(vertices.get(i+1).x,vertices.get(i+1).y),
				new Vector2(vertices.get(i).x,vertices.get(i).y)
			};
			System.out.println(i);
			ps.set(temp);
			boxBody.createFixture(ps, 1);
		}
		System.out.println(vertices.size()/3);


			// add the box to our list of boxes
			boxes.add(boxBody);
		
		
		/*BodyDef boxBodyDef2 = new BodyDef();
		boxBodyDef2.type = BodyType.DynamicBody;
		boxBodyDef2.position.x = 0;
		boxBodyDef2.position.y = 25;
		Body boxBody2 = world.createBody(boxBodyDef2);
		boxBody2.createFixture(boxPoly, 1);
		// add the box to our list of boxes
		boxes.add(boxBody2);
	boxPoly.dispose();
	*/

		
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact (Contact contact) {}
			@Override
			public void endContact (Contact contact) {}
			@Override
			public void preSolve (Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve (Contact contact, ContactImpulse impulse) {}
		});
	}

	@Override
	public void render () {
		long start = System.nanoTime();
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		float updateTime = (System.nanoTime() - start) / 1000000000.0f;
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		camera.apply(gl);
		batch.getProjectionMatrix().set(camera.combined);

		camera.apply(Gdx.gl10);
		debugRenderer.render(world, camera.combined);
		gl.glPointSize(4);
		renderer.begin(GL10.GL_POINTS);
		for (int i = 0; i < world.getContactCount(); i++) {
			Contact contact = world.getContactList().get(i);
			if (contact.isTouching()) {
				WorldManifold manifold = contact.getWorldManifold();
				int numContactPoints = manifold.getNumberOfContactPoints();
				for (int j = 0; j < numContactPoints; j++) {
					Vector2 point = manifold.getPoints()[j];
					renderer.color(0, 1, 0, 1);
					renderer.vertex(point.x, point.y, 0);
				}
			}
		}
		renderer.end();
		gl.glPointSize(1);
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.begin();
		font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond() + " update time: " + updateTime, 0, 20);
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
}
