package com.ahmet.polyshaping;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Ellipse extends Mesh2d {
	private Vector2 dim;
	
	Ellipse(Vector2 Pos,Vector2 Dim,Vector3 Color,boolean fill)
	{
		super(Pos,Color);
		Vector2[] vertices = new Vector2[30];
		float carpan=360/30;
		for(int i=0; i<30; i++)
		{
			vertices[i]=new Vector2(			
					(float)(Math.cos(Math.toRadians(carpan*i))*Dim.x),
					(float)(Math.sin(Math.toRadians(carpan*i))*Dim.y));
		}
		setVertices(vertices);
		setFill(fill);
		dim=Dim;
	}
}
