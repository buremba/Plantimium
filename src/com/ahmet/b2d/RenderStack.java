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
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
			s.begin();
			for(int i=0; i<stack.size(); i++)
			{
				if(stack.get(i).sprite)
				{
					stack.get(i).Draw(s);
				}
			}
			s.end();
			for(int i=0; i<stack.size(); i++)
			{
				if(!stack.get(i).sprite)
				{
					stack.get(i).Draw(s);
				}
			}
	}
}
