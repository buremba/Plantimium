package com.ahmet.polyshaping;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Game3 extends InputAdapter implements ApplicationListener, InputProcessor {
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Rectangle glViewport;

	ShapeBuilder sb;
	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sb=new ShapeBuilder(this);
	}
	public OrthographicCamera getCamera()
	{
		return cam;
	}
	@Override
	public void render () {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_LINEAR);
		
		gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);

		cam.update();
		cam.apply(gl);
		batch.setProjectionMatrix(cam.combined);
		sb.Draw(batch);
	}
	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {

		return false;
	}
	@Override
	public boolean touchMoved (int x, int y) {

		return false;
	}
	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {

		return false;
	}
	@Override
	public boolean touchDragged(int x,int y,int pointer) {

		return false;
	}
	@Override
	public boolean keyDown (int keycode) {
		//Gdx.app.log("keyDown", Integer.toString(keycode));

		if(keycode==Input.Keys.MINUS) {
			 cam.zoom += 0.06;
		}else
		if(keycode==Input.Keys.PLUS) {
			cam.zoom -= 0.06;
		}else
		if(keycode==Input.Keys.LEFT) {
            if (cam.position.x > 0)
                    cam.translate(-8, 0, 0);
	    }else
	    if(keycode==Input.Keys.RIGHT) {
	            if (cam.position.x < 1024)
	                    cam.translate(8, 0, 0);
	    }else
	    if(keycode==Input.Keys.DOWN) {
	            if (cam.position.y > 0)
	                    cam.translate(0, -8, 0);
	    }else
	    if(keycode==Input.Keys.UP) { // up
	            if (cam.position.y < 1024)
	                    cam.translate(0, 8, 0);
	    }
	    if(keycode==Input.Keys.Z) {
	            cam.rotate(-6, 0, 0, 1);
	    }
	    if(keycode==Input.Keys.X) {
	            cam.rotate(6, 0, 0, 1);
	    }
			
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int arg0, int arg1) {
		cam.setToOrtho(false, arg0, arg1);
		glViewport.width = arg0;
		glViewport.height = arg1;
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}

