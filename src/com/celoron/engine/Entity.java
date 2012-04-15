package com.celoron.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	String id;

	Vector2 position;
	float scale;
	float rotation;

	boolean visible=true;
	RenderComponent renderComponent;

	List<Component> components;
	List<Component> componentsToRemove;

	public Game game;

	public Entity(String id, Game game) {
		this.id = id;

		components = new ArrayList<Component>();
		componentsToRemove = new ArrayList<Component>();

		position = new Vector2(0, 0);
		scale = 1;
		rotation = 0;

		this.game = game;
	}

	public void AddComponent(Component component) {
		if (RenderComponent.class.isInstance(component))
			renderComponent = (RenderComponent) component;
		
		/* if component need input, well then add it to input listeners list */
		if (InputProcessor.class.isInstance(component)){
			game.input.addListener( (InputProcessor)component );
		}

		component.setOwnerEntity(this);
		components.add(component);
	}

	public Component getComponent(Class<?> c) {
		for (Component comp : components) {
			if ( c.isInstance(comp))
				return comp;
		}

		return null;
	}
	
	public void removeComponent(Component comp) {
		componentsToRemove.add(comp);
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getScale() {
		return scale;
	}

	public float getRotation() {
		return rotation;
	}

	public String getId() {
		return id;
	}

	public void setPosition(Vector2 position) {
		this.position.set(position.x, position.y);
	}

	public void setRotation(float rotate) {
		rotation = rotate;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void start() {
		for (Component component : components) {
			component.start();
		}
	}

	public void update() {
		for (Component component : componentsToRemove) {
			components.remove(component);
		}
		componentsToRemove.clear();

		for (Component component : components) {
			component.update();
		}
	}

	public void render() {
		if (renderComponent != null && visible)
			renderComponent.render();
	}

	public void removeAllComponent() {
		for (Component component : components) {
			component.remove();
		}
		components.clear();
	}
	
	public void setVisible(boolean visible){
		this.visible=visible;
	}
}