package com.celoron.testAngry;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.celoron.engine.physic.PhysicComp;

public class PhysicExtra extends PhysicComp implements InputProcessor {
	
	private boolean dragging=false;
	public PhysicExtra(Vector2 dim, BodyType btype) {
		super(dim, btype);
	}
	
	private void dragToPosition(Vector2 pos){
		body.setLinearVelocity(pos.sub(body.getPosition()).mul(5) );
	}
	
	/* radius is meter!! if you want to send pixel then use ( meter=pixel/BOX2D_SCALE ) */
	public void explode(float radius){
		Vector2 pos=body.getPosition();
		game.world.QueryAABB(
				new QueryCallback(){

					@Override
					public boolean reportFixture(Fixture fixture) {
						if(fixture.getBody()==body)return true;
						
						Vector2 force= fixture.getBody().getPosition().sub(body.getPosition());
						force.nor();
						int len=(int)force.len2();
						if(len>0)len=1;
						
						force.mul(5000);
						fixture.getBody().applyForceToCenter(force);
						
						return true;
					}
				},
				pos.x-radius, 
				pos.y-radius, 
				pos.x+radius, 
				pos.y+radius
				);

		game.scene.removeEntity(owner);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(dragging){
			Vector2 v=game.relativeMousePos().mul(1/BOX2D_SCALE);
			dragToPosition(v);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(body!=null){
			if(body.getFixtureList().get(0).testPoint(game.convertMousePos(new Vector2(x,y)).mul(1/BOX2D_SCALE))){
				if(button==0)
					dragging=true;
				else
					explode(10); /* this is meter. not pixel!! */
				
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Vector2 v=game.convertMousePos(new Vector2(x,y)).mul(1/BOX2D_SCALE);
		if(dragging){
			dragToPosition(v);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		dragging=false;
		return false;
	}

}
