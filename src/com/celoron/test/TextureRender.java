package com.celoron.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.celoron.engine.RenderComponent;

public class TextureRender extends RenderComponent {
	private Texture texture;

	public TextureRender(String id, Texture texture) {
		super(id);

		this.texture = texture;
	}

	@Override
	public void render() {			
		Gdx.gl10.glLoadIdentity();	
		game.batch.draw(texture,
				owner.getPosition().x - texture.getWidth() / 2,
				owner.getPosition().y - texture.getHeight() / 2,

				texture.getWidth() / 2, texture.getHeight() / 2,

				texture.getWidth(), /* strech */
				texture.getHeight(),

				owner.getScale(), owner.getScale(),

				owner.getRotation(),

				0, 0,

				texture.getWidth(), texture.getHeight(),

				false, /* flip x, y */
				false);
	}

	@Override
	public void update() {
		/* this empty for now */
	}

	@Override
	public void start() {
	}

}
