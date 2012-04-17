package com.celoron.engine.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {
	private List<InputProcessor> listeners;
	
	public InputManager(){
		listeners= new ArrayList<InputProcessor>();
	}
	
	public void addListener(InputProcessor listener){
		listeners.add(listener);
	}

	@Override
	public boolean keyDown(int key) {
		for (InputProcessor listener : listeners) {
			if(listener.keyDown(key))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char key) {
		for (InputProcessor listener : listeners) {
			if(listener.keyTyped(key))
				return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		for (InputProcessor listener : listeners) {
			if(listener.keyUp(key))
				return true;
		}
		return false;
	}

	@Override
	public boolean scrolled(int n) {
		for (InputProcessor listener : listeners) {
			if(listener.scrolled(n))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		for (InputProcessor listener : listeners) {
			if(listener.touchDown(x, y, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		for (InputProcessor listener : listeners) {
			if(listener.touchUp(x, y, pointer, button))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		for (InputProcessor listener : listeners) {
			if(listener.touchDragged(x, y, pointer))
				return true;
		}
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		for (InputProcessor listener : listeners) {
			if(listener.touchMoved(x, y))
				return true;
		}
		return false;
	}

	public void removeListener(Component component) {
		listeners.remove(component);
	}
	
}
