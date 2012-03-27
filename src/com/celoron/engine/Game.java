package com.celoron.engine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Game extends InputAdapter implements ApplicationListener {
	private OrthographicCamera camera;
	public SpriteBatch batch;
	//private World world; /*next update*/
	
	protected SceneManager sceneManager;
	
	public boolean needsGL20(){
		return false;
	}

	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0, 0, 0);
		batch = new SpriteBatch();
		
		sceneManager=new SceneManager();
		
		onCreate();
		
	}
	
	public void resume(){
		
	}

	public void render (){
		sceneManager.updateAll(this);
		onUpdate();
		
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		camera.apply(gl);

		batch.begin();
		batch.getProjectionMatrix().set(camera.combined);
		camera.apply(Gdx.gl10);
		
		sceneManager.renderAll(this);
        
        batch.end();

	}

	public void resize (int width, int height) {
		
	}

	public void pause () {
		
	}

	public void dispose () {
		
	}
	
	public abstract void onCreate();
	public abstract void onUpdate();
}
