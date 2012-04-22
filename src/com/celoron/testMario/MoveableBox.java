package com.celoron.testMario;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.basic.Line;
import com.celoron.engine.core.Entity;
import com.celoron.engine.physic.PhysicComp;

public class MoveableBox extends PhysicComp implements InputProcessor {
	public static Entity player;
	public static Entity dragLine;
	private boolean dragging=false;
	private static float MAX_DRAGGING_DISTANCE= 100.0f;
	
	public MoveableBox(Vector2 dim, BodyType btype) {
		super(dim, btype);
	}
	
	private void dragToPosition(Vector2 pos){
		body.setLinearVelocity(pos.sub(body.getPosition()).mul(5) );
	}
	
	private void releaseDragging(){
		dragging=false;
		dragLine.setVisible(false);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(dragging){
			Vector2 mpos=game.relativeMousePos();
			float h=mpos.dst(player.getPosition());
			Vector2 dVec= mpos.sub(player.getPosition()).nor();
			if(h>MAX_DRAGGING_DISTANCE)h=MAX_DRAGGING_DISTANCE;
			
			Vector2 sendPos=dVec.mul(h).add(player.getPosition());
			Vector2 v=sendPos.mul(1/BOX2D_SCALE);
			dragToPosition(v);
			
			dragLine.setVisible(true);
			Line l= (Line)dragLine.getRenderComponent();
			l.setDim(owner.getPosition().cpy());
			dragLine.setPosition(player.getPosition());
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
				float h=game.convertMousePos(new Vector2(x,y)).dst(player.getPosition());
				if(h<MAX_DRAGGING_DISTANCE)dragging=true;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		/*Vector2 v=game.convertMousePos(new Vector2(x,y)).mul(1/BOX2D_SCALE);
		if(dragging){
			dragToPosition(v);
			return true;
		}*/
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		releaseDragging();
		return false;
	}

}
