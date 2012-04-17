package com.celoron.engine.basic;

import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.core.Component;
import com.celoron.engine.core.Entity;

public class FollowParent extends Component {
	private Entity parent;
	private Vector2 pos;
	public FollowParent(Entity parent, Vector2 pos) {		
		this.parent=parent;
		this.pos=pos;
	}
	
	public FollowParent(String id, Entity parent) {
		this(parent, new Vector2());
	}

	@Override
	public void update() {
		owner.setPosition(parent.getPosition().cpy().add(pos));
	}

	@Override
	public void start() {

	}

	@Override
	public void remove() {

	}

}
