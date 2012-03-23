package com.ahmet.b2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Rect extends Drawable {
	private Vector3 color;
	private boolean fill;
	private Mesh mesh=null;
	
	private TextureRegion texture;
	public Rect(Vector2 position,Vector2 dim,Vector3 color,boolean fill)
	{
		super(position, dim, 0, false);
		this.color=color;
		this.fill=fill;
		this.angle=0;
        if (mesh == null) {
            mesh = new Mesh(false, 4, 4,
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                    new VertexAttribute(Usage.TextureCoordinates,2, "a_texCoords"));

            mesh.setVertices(new float[] { 
            		position.x, position.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1,
            		position.x+dim.x, position.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1,
            		position.x+dim.x, position.y-dim.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1,
            		position.x, position.y-dim.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1
            });
                                          
            mesh.setIndices(new short[] { 0, 1,2,3 });
        }

	}
	public Rect(Vector2 position,Vector2 dim,TextureRegion texture)
	{
		super(position, dim, 0, true);
		this.texture=texture;
	}
	@Override
	public void Draw(SpriteBatch s) {
		if(sprite)
		{
			s.draw(texture,position.x,position.y, dim.x, dim.y);	
		}
		else
		{
			if(fill)
			{
				mesh.render(GL10.GL_TRIANGLES, 0, 4);
			}
			else
			{
				mesh.render(GL10.GL_LINE_LOOP, 0, 4);
			}
		}
		
	}
}
