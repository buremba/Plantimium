package com.ahmet.b2d;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderStack {
	private SpriteBatch s;
	public Stack<Drawable> stack;
	public RenderStack()
	{
		stack=new Stack<Drawable>();
		s=new SpriteBatch();
	}
	public void Draw()
	{
			//s.begin();
			for(int i=0; i<stack.size(); i++)
			{
				if(stack.get(i).sprite)
				{
					stack.get(i).Draw(s);
				}
			}
			//s.end();
			for(int i=0; i<stack.size(); i++)
			{
				if(!stack.get(i).sprite)
				{
					stack.get(i).Draw(s);
				}
			}
	}
}
