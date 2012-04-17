package com.celoron.engine.basic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.celoron.engine.core.RenderComponent;

public class TextureRender extends RenderComponent {
	private Texture texture;

	public TextureRender(Texture texture) {
		this.texture = texture;
	}

	@Override
	public void render() {
		Gdx.gl10.glLoadIdentity();
		if(game==null)
			Gdx.app.log("null", "game");
		if(game.batch==null)
			Gdx.app.log("null", "batch");
		if(texture==null)
			Gdx.app.log("null", "texture");
		game.batch.draw(
				texture,
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

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
