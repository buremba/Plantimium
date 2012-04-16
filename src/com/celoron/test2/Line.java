package com.celoron.test2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.RenderComponent;

public class Line extends RenderComponent {
	Mesh mesh;
	float width;
	Vector2 dim; /* second point position */
	
	public Line(String id, Vector2 dim, float width) {
		super();
		this.width=width;
		this.dim=dim;
	}

	@Override
	public void render() {
		game.batch.disableBlending();

		Gdx.gl10.glLoadIdentity();
	    Gdx.graphics.getGL10().glDisable(GL10.GL_TEXTURE_2D);

		Gdx.gl10.glTranslatef(owner.getPosition().x, owner.getPosition().y, 0);
		Gdx.gl10.glRotatef(owner.getRotation(), 0, 0, 1);
		
		Gdx.gl10.glLineWidth(width);
		Gdx.gl10.glEnable(GL10.GL_LINE_SMOOTH);
		mesh.render(GL10.GL_LINES, 0, 2);

	    Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
	    
		game.batch.enableBlending();
	}
	
	public void setDim(Vector2 dim){
		this.dim=dim.sub(owner.getPosition());
		mesh = new Mesh(true, 2, 2, new VertexAttribute(Usage.Position, 3,"a_position"), 
				new VertexAttribute(Usage.ColorPacked, 4, "a_color") );

		mesh.setVertices(new float[] {
				0, 		0, 		0, Color.toFloatBits(0, 0, 255, 255),
				dim.x, 	dim.y, 	0, Color.toFloatBits(0, 0, 255, 255)
				
		});
		mesh.setIndices(new short[] { 0, 1 });
	}

	@Override
	public void start() {
		setDim(dim);
	}

	@Override
	public void remove() {

	}

}
