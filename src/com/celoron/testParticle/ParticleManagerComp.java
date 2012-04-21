package com.celoron.testParticle;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.core.Component;

public class ParticleManagerComp extends Component implements InputProcessor  {
	ParticleEffect pe;
	@Override
	public void update() {

	}

	@Override
	public void start() {
		pe= ((ParticleRenderComp)owner.getComponent(ParticleRenderComp.class)).getParticle();
	}

	@Override
	public void remove() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Vector2 pos= game.convertMousePos(new Vector2(x,y));
		
		pe.setPosition(pos.x, pos.y);
		pe.start();
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

}
