package com.celoron.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GuiButton extends GuiObject {
	private Texture img;
	private Texture normalImg;
	private Texture hoverImg;
	public GuiButton(Game game, Vector2 pos, Texture normalImg, Texture hoverImg) {
		super(game);
		
		setPos(pos);
		setDim(new Vector2(normalImg.getWidth(), normalImg.getHeight()));
		this.normalImg=normalImg;
		this.hoverImg=hoverImg;
		
		img=normalImg;
	}

	@Override
	public void render() {
		Gdx.gl10.glLoadIdentity();
		game.batch.draw(img, pos.x, pos.y);
	}

	@Override
	protected void onHover() {
		Gdx.app.log("button", "hover");
		img=hoverImg;
		
	}

	@Override
	protected void onUnHover() {
		Gdx.app.log("button", "unHover");
		img=normalImg;
	}

	@Override
	protected void onClick() {
		Gdx.app.log("button", "clicked");
	}

}
