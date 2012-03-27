package com.celoron.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;
import com.celoron.engine.Game;

public class WASD_ControllerComponent extends Component {
	public WASD_ControllerComponent(String id) {
		super(id);
	}

	@Override
	public void update(Game game) {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            owner.setPosition( owner.getPosition().add(new Vector2(-1,0)) );
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            owner.setPosition( owner.getPosition().add(new Vector2(0,-1)) );
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            owner.setPosition( owner.getPosition().add(new Vector2(0,1)) );
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            owner.setPosition( owner.getPosition().add(new Vector2(1,0)) );
		}
	}

}
