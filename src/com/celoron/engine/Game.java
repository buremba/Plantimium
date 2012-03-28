package com.celoron.engine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Game extends InputAdapter implements ApplicationListener {
	private OrthographicCamera camera;
	public SpriteBatch batch;
	// private World world; /*next update*/

	public float deltaTime;
	public float lastFrameTime;

	public SceneManager sceneManager;
	public GL10 gl;

	public World world;

	public boolean needsGL20() {
		return false;
	}

	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.position.set(0, 0, 0);
		batch = new SpriteBatch();

		sceneManager = new SceneManager();

		gl = Gdx.graphics.getGL10();

		world = new World(new Vector2(0, -50), true);


		lastFrameTime = System.nanoTime();
		
		onCreate();
	}

	public void resume() {

	}

	public void render() {
		deltaTime = (System.nanoTime() - lastFrameTime) / 1000000000.0f;
		lastFrameTime = System.nanoTime();

		sceneManager.updateAll(this);
		onUpdate();
		world.step(deltaTime, 8, 3);

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		camera.apply(gl);

		batch.begin();
		batch.getProjectionMatrix().set(camera.combined);

		sceneManager.renderAll(this);

		batch.end();
	}

	public void resize(int width, int height) {

	}

	public void pause() {

	}

	public void dispose() {

	}

	public abstract void onCreate();

	public abstract void onUpdate();

	public Vector2 relativeMousePos() {
		return new Vector2(Gdx.input.getX() + camera.position.x
				- Gdx.graphics.getWidth() / 2, -Gdx.input.getY()
				+ camera.position.y + Gdx.graphics.getHeight() / 2);
	}
}
