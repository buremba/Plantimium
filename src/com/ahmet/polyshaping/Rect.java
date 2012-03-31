package com.ahmet.polyshaping;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Rect extends Mesh2d {

	public Rect(Vector2 Pos,Vector2 Dim, Vector3 Color, boolean fill) {
		super(new Vector2[]{
				new Vector2(0,0),
				new Vector2(Dim.x,0),
				new Vector2(Dim.x,Dim.y),
				new Vector2(0,Dim.y),
		}, Pos, Color, fill);
	}

}
