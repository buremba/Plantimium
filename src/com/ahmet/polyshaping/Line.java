package com.ahmet.polyshaping;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Line extends Mesh2d {
	private Vector2 a1,a2;
	
	Line(Vector2 Pos1,Vector2 Pos2,Vector3 Color)
	{
		super(new Vector2[]{
				new Vector2(Pos1.x,Pos1.y),
				new Vector2(Pos2.x,Pos2.y)
		}, new Vector2(0,0), Color, false);
		a1=Pos1;
		a2=Pos2;
	}
	public void setPos1(Vector2 p)
	{
		a1=p;
		super.setVertices(new Vector2[]{
				new Vector2(a1.x,a1.y),
				new Vector2(a2.x,a2.y)});
	}
	public void setPos2(Vector2 p)
	{
		a2=p;
		super.setVertices(new Vector2[]{
				new Vector2(a1.x,a1.y),
				new Vector2(a2.x,a2.y)});
	}

	public Vector2 getPos1()
	{
		return a1;
	}
	public Vector2 getPos2()
	{
		return a2;
	}
}
