package com.celoron.testMario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.basic.TextureRender;
import com.celoron.engine.core.Component;
import com.celoron.engine.core.Entity;
import com.celoron.testAngry.Box;

public class Designer extends Component implements InputProcessor {

	@Override
	public void update() {
		
	}

	@Override
	public void start() {
		
	}

	@Override
	public void remove() {

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
			Entity e = new Entity(game);
			
			e.setScale(0.15f);
			e.setPosition(game.convertMousePos( new Vector2(x,y) ));
			
			e.addComponent(new TextureRender(game.asset.getTexture("data/box.jpg")));
			e.addComponent(new MoveableBox(new Vector2(256, 256).mul(0.15f), BodyType.DynamicBody));
			e.addComponent(new Box());
			
			e.setName("Box");
			
			game.scene.addEntity(e);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
