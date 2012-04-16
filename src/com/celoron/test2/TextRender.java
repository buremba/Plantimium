package com.celoron.test2;

import com.badlogic.gdx.graphics.Color;
import com.celoron.engine.RenderComponent;

public class TextRender extends RenderComponent {
	private String str;
	private Color color;
	public TextRender(String id, String str, Color color) {
		super();
		
		setColor(color);
		setText(str);
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
	
	public void setText(String str){
		this.str=str;
	}
	
	public String getText(){
		return str;
	}
	
	public void setColor(Color color){
		this.color=color;
	}
	
	public Color getColor(){
		return color;
	}

}
