package com.celoron.engine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public abstract class GuiObject implements InputProcessor {
	public Game game;
	
	protected Vector2 pos;
	protected Vector2 dim;
	protected boolean hovered=false;

	public GuiObject(Game game){
		this.game=game;
		
		game.input.addListener(this);
	}
	
	public void setPos(Vector2 pos){this.pos=pos;}
	public Vector2 getPos(){return pos;}
	
	public void setDim(Vector2 dim){this.dim=dim;}
	public Vector2 getDim(){return dim;}
	
	public abstract void render();
	
	protected abstract void onHover();
	protected abstract void onUnHover();
	protected abstract void onClick();
	
	/* input process */
	public boolean keyDown(int arg0) {return false;}
	public boolean keyTyped(char arg0) {return false;}
	public boolean keyUp(int arg0) {return false;}
	public boolean scrolled(int arg0) {return false;}

	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		return false;
	}

	public boolean touchDragged(int x, int y, int arg2) {return false;}

	@Override
	public boolean touchMoved(int x, int y) {
		Vector2 m=GuiManager.convertMousePos(new Vector2(x,y));
		if((m.x > pos.x && m.x < pos.x+dim.x) && (m.y > pos.y && m.y < pos.y+dim.y) ){
			if(!hovered){
				onHover();
				hovered=true;
			}
		}
		else{
			if(hovered){
				onUnHover();
				hovered=false;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int arg2, int arg3) {
		Vector2 m=GuiManager.convertMousePos(new Vector2(x,y));
		if((m.x > pos.x && m.x < pos.x+dim.x) && (m.y > pos.y && m.y < pos.y+dim.y) ){
			onClick();
		}
		return false;
	}
}