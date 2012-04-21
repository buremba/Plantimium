package com.celoron.engine.physic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.celoron.engine.core.Entity;

public interface PhysicListener {
	public void onContact(Entity other, Contact contact);
}