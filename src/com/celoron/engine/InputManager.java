package com.celoron.engine;

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
			listener.keyDown(key);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char key) {
		for (InputProcessor listener : listeners) {
			listener.keyTyped(key);
		}
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		for (InputProcessor listener : listeners) {
			listener.keyUp(key);
		}
		return false;
	}

	@Override
	public boolean scrolled(int n) {
		for (InputProcessor listener : listeners) {
			listener.scrolled(n);
		}
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		for (InputProcessor listener : listeners) {
			listener.touchDown(x, y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		for (InputProcessor listener : listeners) {
			listener.touchUp(x, y, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		for (InputProcessor listener : listeners) {
			listener.touchDragged(x, y, pointer);
		}
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		for (InputProcessor listener : listeners) {
			listener.touchMoved(x, y);
		}
		return false;
	}
	
}
