package com.celoron.engine.core;


public abstract class Component {
	protected Entity owner;
	public Game game;

	public Component() {
		
	}

	public void setOwnerEntity(Entity owner) {
		this.owner = owner;
		game = owner.game;
	}

	public abstract void update();

	public abstract void start();

	public abstract void remove();
}