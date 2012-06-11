package com.ahmet.b2d;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Tools {
	static EarClippingTriangulator ect=new EarClippingTriangulator();
	public static Vector2[] Triangulate(Vector2[] path)
	{
		
		List<Vector2> alist=new ArrayList<Vector2>();
		for(int i=0; i<path.length; i++)
		{
			alist.add(path[i]);
		}
		alist=ect.computeTriangles(alist);
		if(alist!=null)
		{
		Vector2[] result = new Vector2[alist.size()];
		for(int i=0; i<alist.size(); i++)
		{
			result[i]=alist.get(i);
		}
		return result;	
		}
		else
		{
			return null;
		}
	}
}
