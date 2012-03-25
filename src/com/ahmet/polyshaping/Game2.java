package com.ahmet.polyshaping;

import com.ahmet.b2d.GIcombin;
import com.ahmet.b2d.Mesh2d;
import com.ahmet.b2d.RenderStack;
import com.ahmet.b2d.Tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Game2 extends GIcombin implements InputProcessor {
	Mesh2d m1;
	RenderStack rs;
	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		Vector2[] vv={
				new Vector2(-1,0),
				new Vector2(1, 0),
				new Vector2(1, 5),
				new Vector2(10, 10),
				new Vector2(7,10),
				new Vector2(0,5),
				new Vector2(-7,10),
				new Vector2(-10,10),
				new Vector2(-1,5)
		};
		
		vv=Tools.Triangulate(vv);
		m1=new Mesh2d(vv,new Vector2(0,0),new Vector2(1,3),new Vector3(255,0,0),true);
	}

	@Override
	public void render () {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
		m1.Draw(null);
		
	}
	@Override
	public void dispose () {}
	@Override
	public void pause() {}
	@Override
	public void resize(int arg0, int arg1) {}
	@Override
	public void resume() {}
	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {
		

		return false;
	}

}
