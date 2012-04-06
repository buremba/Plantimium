package com.celoron.test2;

import com.badlogic.gdx.graphics.Color;
import com.celoron.engine.RenderComponent;

public class TextRender extends RenderComponent {
	private String str;
	private Color color;
	public TextRender(String id, String str, Color color) {
		super(id);
		
		this.color=color;
		this.str=str;
	}

	@Override
	public void render() {
		game.font.setColor(color.r, color.g, color.b, color.a);
		game.font.draw(game.batch, str, owner.getPosition().x, owner.getPosition().y);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
