package com.celoron.engine;

import java.util.LinkedList;
import java.util.List;

public class SceneManager {
	private List<Entity> entitys = new LinkedList<Entity>();
	private List<Entity> entitysToAdd = new LinkedList<Entity>();
	private List<Entity> entitysToRemove = new LinkedList<Entity>();

	/*
	 * why there is entitysToAdd? because if you add entity on update, its cause
	 * to crash on loop.
	 */

	public void updateAll(Game game) {
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
		entitysToAdd.add(e);
	}

	public void removeEntity(Entity e) {
		entitysToRemove.add(e);
	}
}
