package com.celoron.engine.core;

import java.util.ArrayList;
import java.util.List;


public class SceneManager {	
	private List<Entity> entitys;
	private List<Entity> entitysToAdd;
	private List<Entity> entitysToRemove;

	/*
	 * why there is entitysToAdd? because if you add entity on update, its cause
	 * to crash on loop.
	 */
	
	public SceneManager(){
		entitys = new ArrayList<Entity>();
		entitysToAdd = new ArrayList<Entity>();
		entitysToRemove = new ArrayList<Entity>();
	}

	public void updateAll(Game game) {
		/* add entity list to new entitys
		 * remove entity from list
		 * and finally update all entitys*/
		
		/* this prosedure not doing with addAll() method
		 * because entity need start method*/
		for (Entity entity : entitysToAdd) {
			entitys.add(entity);
			entity.start();
		}
		entitysToAdd.clear();

		for (Entity entity : entitysToRemove) {
			entity.removeAllComponent();
			entitys.remove(entity);
		}
		entitysToRemove.clear();

		for (Entity entity : entitys) {
			entity.update();
		}
	}

	public void renderAll(Game game) {
		for (Entity entity : entitys) {
			entity.render();
		}
	}

	public void addEntity(Entity e) {
		/* this method not directly adding entity toy entitys list
		 * because its cause incomplete update list */
		entitysToAdd.add(e);
	}

	public void removeEntity(Entity e) {
		/* same as the addEntity method, its not adding directly to entitys list */
		entitysToRemove.add(e);
	}
}
