package com.celoron.angryTest;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;
import com.celoron.test.PhysicComp;
import com.celoron.test.TextureRender;

public class Sapan extends Component implements InputProcessor {
	private boolean drag=false;

	@Override
	public void update() {

	}

	@Override
	public void start() {

	}

	@Override
	public void remove() {

	}
	
	public void throwBox(Vector2 speed){
		Entity e = new Entity(game);
		
		e.setScale(0.15f);
		e.setPosition(owner.getPosition());
		e.setRotation(speed.angle());
		
		e.addComponent(new TextureRender(game.asset.getTexture("data/box.jpg")));
		e.addComponent(new PhysicComp(new Vector2(256, 256).mul(0.15f), BodyType.DynamicBody));
		e.addComponent(new GiveSpeed(speed));
		
		game.scene.addEntity(e);
	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
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
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		Vector2 pos= game.convertMousePos(new Vector2(x,y));
		
		if(pos.dst(owner.getPosition()) < 48){
			drag=true;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int arg2, int arg3) {
		if(drag){
			Vector2 pos= game.convertMousePos(new Vector2(x,y));
			drag=false;
			throwBox(owner.getPosition().cpy().sub(pos).mul(0.1f));
			
		}
		return false;
	}

}
