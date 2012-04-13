package com.ahmet.polyshaping;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Misc {
	public static <E> ArrayList<E> lineup(ArrayList<E> arraylist, int order, E object) {
		ArrayList<E> newlist = new ArrayList<E>();
		for(int i=0; i<arraylist.size(); i++) {
			if(order==i) {
				newlist.add(object);
			}else
			if(order<i) {
				newlist.add(arraylist.get(i));
			}else {
				newlist.add(arraylist.get(i));
			}
				
		}
		
		return newlist;
	}
}
