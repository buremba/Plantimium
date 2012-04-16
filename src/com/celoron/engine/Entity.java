package com.celoron.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	/* transform */
	Vector2 position;
	float scale;
	float rotation;

	/* visible staff */
	boolean visible=true;
	RenderComponent renderComponent;

	List<Component> components;
	List<Component> componentsToRemove;

	/* reference to game, so we can use scene,input,asset.. */
	public Game game;

	public Entity(Game game) {
		components = new ArrayList<Component>();
		componentsToRemove = new ArrayList<Component>();

		position = new Vector2(0, 0);
		scale = 1;
		rotation = 0;

		this.game = game;
	}

	public void addComponent(Component component) {
		if (RenderComponent.class.isInstance(component))
			renderComponent = (RenderComponent) component;
		
		/* if component need input, well then add it to input listeners list */
		if (InputProcessor.class.isInstance(component)){
			game.input.addListener( (InputProcessor)component );
		}

		component.setOwnerEntity(this);
		components.add(component);
	}

	/*
	 * example of usage:
		PhysicComp physic= (PhysicComp) owner.getComponent(PhysicComp.class);
		if(physic != null){
			physic.body.setLinearVelocity(new Vector2(10,0));
		}
	 */
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