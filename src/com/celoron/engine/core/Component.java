package com.celoron.engine.core;

import org.w3c.dom.Element;


public abstract class Component {
	protected Entity owner;
	public Game game;

	public void setOwnerEntity(Entity owner) {
		this.owner = owner;
		game = owner.game;
	}

	public abstract void update();

	public abstract void start();

	public abstract void remove();
	
	/* some components need other components to work.
	 * example player control component maybe needing physic component
	 * then you can override this method in player component and check if
	 * owner have physic component */
	public boolean canStart(Entity testOwner){
		return true;
	}
	
	public static Component loadFromXml(Game game, Element data){
		return null;
	}
}