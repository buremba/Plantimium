package com.celoron.engine.core;

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
	List<Component> componentsToAdd;

	/* reference to game, so we can use scene,input,asset.. */
	public Game game;
	
	/* optional: you can give name to entity */
	private String name;

	public Entity(Game game) {
		components = new ArrayList<Component>();
		componentsToRemove = new ArrayList<Component>();
		componentsToAdd = new ArrayList<Component>();

		position = new Vector2(0, 0);
		scale = 1;
		rotation = 0;

		this.game = game;
	}

	public void addComponent(Component component) {
		if (RenderComponent.class.isInstance(component)){
			renderComponent = (RenderComponent) component;
			renderComponent.setOwnerEntity(this);
		}

		componentsToAdd.add(component);
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
		/* removing components */
		for (Component component : componentsToRemove) {
			if(component instanceof InputProcessor ){
				game.input.removeListener(component);
			}
			components.remove(component);
		}
		componentsToRemove.clear();
		
		/* adding components */
		for (Component component : componentsToAdd) {
			if(!component.canStart(this)) continue;
			
			components.add(component);
			
			/* if component need input, well then add it to input listeners list */
			if (InputProcessor.class.isInstance(component)){
				game.input.addListener( (InputProcessor)component );
			}
			
			component.setOwnerEntity(this);
			component.start();
		}
		componentsToAdd.clear();

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
			if(component instanceof InputProcessor ){
				game.input.removeListener(component);
			}
			component.remove();
		}
		components.clear();
	}
	
	public void setVisible(boolean visible){
		this.visible=visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public RenderComponent getRenderComponent() {
		return renderComponent;
	}
}