package com.celoron.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GuiManager {
	Game game;
	
	private List<GuiObject> objects;
	private List<GuiObject> objectsToAdd;
	private List<GuiObject> objectsToRemove;

	
	public GuiManager(Game game){
		this.game= game;
		
		objects = new ArrayList<GuiObject>();
		objectsToAdd = new ArrayList<GuiObject>();
		objectsToRemove = new ArrayList<GuiObject>();
	}
	
	public void renderAll() {
		for (GuiObject o : objectsToAdd) {
			objects.add(o);
		}
		objectsToAdd.clear();

		for (GuiObject o : objectsToRemove) {
			objects.remove(o);
		}
		objectsToRemove.clear();

		for (GuiObject o : objects) {
			o.render();
		}
	}

	public void addEntity(GuiObject e) {
		objectsToAdd.add(e);
	}

	public void removeEntity(GuiObject e) {
		objectsToRemove.add(e);
	}
	
	public static Vector2 convertMousePos(Vector2 v){
		return new Vector2(
				v.x  - Gdx.graphics.getWidth()  / 2 , 
				-v.y + Gdx.graphics.getHeight() / 2 );
	}
}
