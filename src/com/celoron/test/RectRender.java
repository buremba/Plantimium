package com.celoron.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.RenderComponent;

public class RectRender extends RenderComponent {
	Mesh mesh;

	public RectRender(String id, Vector2 dim) {
		super(id);

		mesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3,"a_position"), 
				new VertexAttribute(Usage.ColorPacked, 4, "a_color") );

		mesh.setVertices(new float[] {
				-dim.x/2, 	-dim.y/2, 	0, Color.toFloatBits(255, 0, 0, 255),
				dim.x/2, 	dim.y/2, 	0, Color.toFloatBits(255, 0, 0, 255),
				-dim.x/2,	dim.y/2, 	0,	Color.toFloatBits(255, 0, 0, 255),
				dim.x/2,	-dim.y/2, 	0, Color.toFloatBits(255, 0, 0, 255)
				
		});
		mesh.setIndices(new short[] { 0, 2, 1, 3 });
	}

	@Override
	public void render() {
		game.batch.disableBlending();

		Gdx.gl10.glLoadIdentity();
	    Gdx.graphics.getGL10().glDisable(GL10.GL_TEXTURE_2D);

		Gdx.gl10.glTranslatef(owner.getPosition().x, owner.getPosition().y, 0);
		Gdx.gl10.glRotatef(owner.getRotation(), 0, 0, 1);
		
		mesh.render(GL10.GL_LINE_LOOP, 0, 4);

	    Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
	    
		game.batch.enableBlending();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void start() {
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
