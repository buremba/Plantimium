package com.celoron.engine.basic;

import org.w3c.dom.Element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.celoron.engine.core.Component;
import com.celoron.engine.core.Game;
import com.celoron.engine.core.RenderComponent;

public class TextureRender extends RenderComponent {
	private Texture texture;

	public TextureRender(Texture texture) {
		this.texture = texture;
	}
	
	public static Component loadFromXml(Game game, Element data){
		String texturePath= data.getAttribute("path");
		if(texturePath==""){
			Gdx.app.log("Component loader", "TextureRender cant find path");
			return null;
		}
		return new TextureRender(game.asset.getTexture(texturePath));
	}

	@Override
	public void render() {
		Gdx.gl10.glLoadIdentity();
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
