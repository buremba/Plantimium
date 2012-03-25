package com.ahmet.b2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Mesh2d {
	private boolean fill=false;
	private Mesh mesh;
	public int nvertex=4;
	private Vector2[] vertexlist;
	public Vector2 position;
	public float angle;
	public Mesh2d(Vector2[] vertexlist,Vector2 pos,Vector2 dim,Vector3 color,boolean fill)
	{
		this.position=pos;
		this.angle=0;
		//super(pos,dim,0,false);
		nvertex=vertexlist.length;
		this.fill=fill;
		this.vertexlist=vertexlist;
        if (mesh == null) {
            mesh = new Mesh(false, vertexlist.length,vertexlist.length,
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                    new VertexAttribute(Usage.TextureCoordinates,2, "a_texCoords"));
            float[] vertices=new float[vertexlist.length*6];
            short[] indices=new short[vertexlist.length];
            for(int i=0; i<vertexlist.length; i++)
            {
            	Vector2 t1=vertexlist[i];
            	vertices[i*6]=t1.x;
            	vertices[i*6+1]=t1.y;
            	vertices[i*6+2]=0;
            	vertices[i*6+3]=Color.toFloatBits(color.x, color.y, color.z, 255);
            	vertices[i*6+4]=1;
            	vertices[i*6+5]=1;
            	indices[i]=(short) i;
            }
            
            mesh.setVertices(vertices);  
            mesh.setIndices(indices);
        }
	}
	
	public void Draw(SpriteBatch s) {
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glTranslatef(position.x,position.y, 0);
		Gdx.gl10.glRotatef(angle, 0, 0, 1);		
		if(fill)
		{
			mesh.render(GL10.GL_TRIANGLES, 0, nvertex);
		}
		else
		{
			mesh.render(GL10.GL_LINE_LOOP, 0, nvertex);
		}
		Gdx.gl10.glPushMatrix();
		
	}
	public void addVertex(int x,int y)
	{
		Vector2[] vertexlist2=new Vector2[vertexlist.length+1];
		for(int i=0; i<vertexlist.length; i++)
		{
			vertexlist2[i]=vertexlist[i];
		}
		vertexlist2[vertexlist.length]=new Vector2(x,y);
		nvertex=vertexlist2.length;
		this.vertexlist=vertexlist2;
            mesh = new Mesh(false, vertexlist.length,vertexlist.length,
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                    new VertexAttribute(Usage.TextureCoordinates,2, "a_texCoords"));
            float[] vertices=new float[vertexlist.length*6];
            short[] indices=new short[vertexlist.length];
            for(int i=0; i<vertexlist.length; i++)
            {
            	Vector2 t1=vertexlist[i];
            	vertices[i*6]=t1.x;
            	vertices[i*6+1]=t1.y;
            	vertices[i*6+2]=0;
            	vertices[i*6+3]=Color.toFloatBits(255, 0,0, 255);
            	vertices[i*6+4]=1;
            	vertices[i*6+5]=1;
            	indices[i]=(short) i;
            }
            
            mesh.setVertices(vertices);  
            mesh.setIndices(indices);
	}
	public Vector2[] getVertices()
	{
		return this.vertexlist;
	}

}
