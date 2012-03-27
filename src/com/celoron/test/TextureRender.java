package com.celoron.test;

import com.badlogic.gdx.graphics.Texture;
import com.celoron.engine.Game;
import com.celoron.engine.RenderComponent;

public class TextureRender extends RenderComponent {
	private Texture texture;
	public TextureRender(String id, Texture texture) {
		super(id);
		
		this.texture = texture;
	}

	@Override
	public void render(Game game) {
		game.batch.draw(texture, owner.getPosition().x, owner.getPosition().y);
	}

	@Override
	public void update(Game game) {
		/* this empty for now */
	}

}
