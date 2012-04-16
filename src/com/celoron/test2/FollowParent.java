package com.celoron.test2;

import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;

public class FollowParent extends Component {
	private Entity parent;
	private Vector2 pos;
	public FollowParent(String id, Entity parent, Vector2 pos) {
		super();
		
		this.parent=parent;
		this.pos=pos;
	}
	
	public FollowParent(String id, Entity parent) {
		this(id, parent, new Vector2());
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
