package com.celoron.test2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;
import com.celoron.test.TextureRender;

public class TestGame extends Game implements InputProcessor {
	private Entity city;
	@Override
	public void onCreate() {
		city=new Entity("City",this);
		city.setPosition(new Vector2(0,0));
		city.AddComponent(new TextureRender("render", new Texture("data/city.png")));
		
		sceneManager.addEntity(city);
		
		Entity text=new Entity("text",this);
		text.AddComponent(new TextRender("render", "39", new Color(0,0,1,1) ));
		text.AddComponent(new FollowParent("follow", city, new Vector2(-15,15)));
		
		sceneManager.addEntity(text);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void onUpdate() {
		//city.getPosition().add(0.5f ,0);
	}

	public static void main(String[] args) {
		new JoglApplication(new TestGame(), "Test Game", 800, 600, false);
	}
	
	@Override
	public boolean keyDown(int arg0) {
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		return false;
	}
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		/*city=new Entity("City",this);
		city.setPosition(convertMousePos(new Vector2(x,y)) );
		city.AddComponent(new TextureRender("render", new Texture("data/city.png")));
		
		sceneManager.addEntity(city);
		
		Gdx.app.log("a", "touched");*/
		
		city.setPosition(convertMousePos(new Vector2(x,y)));
		
		return false;
	}
	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}
	@Override
	public boolean touchMoved(int arg0, int arg1) {
		return false;
	}
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

}