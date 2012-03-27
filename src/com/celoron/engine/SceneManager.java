package com.celoron.engine;

import java.util.LinkedList;
import java.util.List;

public class SceneManager {
	private List<Entity> entitys= new LinkedList<Entity>();
	
	public void updateAll(Game game){
		for(Entity entity : entitys){
			entity.update(game);
		}
	}
	
	public void renderAll(Game game){
		for(Entity entity : entitys){
			entity.render(game);
		}
	}
	
	public void addEntity(Entity e){
		entitys.add(e);
	}
}
