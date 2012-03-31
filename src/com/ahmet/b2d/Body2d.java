package com.ahmet.b2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Body2d extends Drawable {

	private Mesh2d mesh;
	private World world;
	public Body body;
	Body2d(Mesh2d mesh,World world)
	{
		this.mesh=mesh;
		this.world=world;
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;
		boxBodyDef.position.x = mesh.position.x;
		boxBodyDef.position.y =mesh.position.y;
		body = world.createBody(boxBodyDef);
		
		PolygonShape ps;
		Vector2[] vv=mesh.getVertices();
		for(int i=0; i<vv.length; i+=3)
		{
			ps = new PolygonShape();
			Vector2[] temp=new Vector2[] {
				new Vector2(vv[i+2].x,vv[i+2].y),
				new Vector2(vv[i+1].x,vv[i+1].y),
				new Vector2(vv[i].x,vv[i].y)
			};
			ps.set(temp);
			body.createFixture(ps, 1);
		}
	}
	@Override
	public void Draw(SpriteBatch s) {
		mesh.angle=(float) Math.toDegrees(body.getAngle());
		mesh.position=body.getPosition();
		mesh.Draw(s);
	}
}
