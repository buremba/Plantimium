package com.ahmet.b2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Drawable {
	public Vector2 position,dim;
	public float angle;
	public boolean sprite;
	public Drawable() {};
	public Drawable(Vector2 position,Vector2 dim,float angle,boolean sprite)
	{
		this.position=position;
		this.dim=dim;
		this.angle=angle;
		this.sprite=sprite;
	}
	public abstract void Draw(SpriteBatch s);
}
