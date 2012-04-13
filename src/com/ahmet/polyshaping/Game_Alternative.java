package com.ahmet.polyshaping;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;
import com.ahmet.b2d.Rect;
import com.ahmet.b2d.Tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game_Alternative extends InputAdapter implements ApplicationListener, InputProcessor {
	private SpriteBatch batch;
	Mesh2d ak = null;
	Ellipse e1;
	Ellipse border;
	Line l1;
	Line errline = null;
	ArrayList<Ellipse> borders = null;
	boolean first = true;
	int choosed = -1;
	int mode = 0;
	boolean process = false;
	boolean touchDown;
	boolean touchDragged;
	boolean vertexLock=false;
	final private int CLICK_SENSIVITY = 25;
	final private int SMOOTH_SENSIVITY = 10;
	@Override
	public void create () {

		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		e1=new Ellipse(new Vector2(60,60),new Vector2(10,10),new Vector3(155,56,55),false);
	}
	@Override
	public void render () {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_LINEAR);
		if(ak!=null)
			ak.Draw(batch);
		if(e1!=null && mode==2)
			e1.Draw(batch);
		if(borders!=null) {
			for(int i=0; i<borders.size(); i++) {
				borders.get(i).Draw(batch); 
			}
		}
		if(errline!=null && mode==2)
			errline.Draw(batch);
			
	}
	public void fillallcircle(boolean fill) {
		for(int i=0; i<borders.size(); i++) {
			borders.get(i).setFill(fill);
		}
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		//Gdx.app.log("events", "touchUp");
		e1.setPosition(new Vector2(x,Gdx.graphics.getHeight()-y));
		if(process && first) {
			first = false;
			if(l1==null)
				l1=new Line(new Vector2(x,y),new Vector2(100,100),new Vector3(255,255,255));
			
			borders = new ArrayList<Ellipse>();
			ArrayList<Vector2> smooth_vertex = new ArrayList<Vector2>();
			smooth_vertex.add(ak.getVertex(0));
			border = new Ellipse(ak.getVertex(0),new Vector2(5,5),new Vector3(255,120,120),false);
			borders.add(border);
			int passed=0;
			for(int i=1; i<ak.getVertices().length; i++) {
				int dist = (int) Math.hypot(ak.getVertex(i).x-ak.getVertex(i-1).x , ak.getVertex(i).y-ak.getVertex(i-1).y);
				if(dist>SMOOTH_SENSIVITY || passed>SMOOTH_SENSIVITY || i==ak.getVertices().length) {
					passed = 0;
					smooth_vertex.add(ak.getVertex(i));
					border = new Ellipse(ak.getVertex(i),new Vector2(5,5),new Vector3(255,120,120),false);
					borders.add(border);
				}else {
					passed++;
				}
			}
			Vector2[] vArray = new Vector2[smooth_vertex.size()];
			smooth_vertex.toArray(vArray);
			ak.setVertices(vArray);
			process = false;
		}else
		if(!first) {
			if(mode==2) {
				fillallcircle(false);
				Vector2 point =  new Vector2(x,Gdx.graphics.getHeight()-y);
				
				int[] keys = Geometry.find_closest_point_in_vertex(ak.getVertices(), point);
				borders.get(keys[0]).setFill(true);
				borders.get(keys[1]).setFill(true);
				
				Vector2 closest_point = Geometry.calculate_closest_point(ak.getVertex(keys[0]), ak.getVertex(keys[1]), point);

	    	    double distance = Math.hypot(closest_point.x-point.x, closest_point.y-point.y);
	    	    
	    	    if(distance>CLICK_SENSIVITY) {
	    	    	errline =new Line(new Vector2(point.x,point.y),new Vector2(closest_point.x,closest_point.y),new Vector3(0, 34,34));
	    	    }else {
	    	    	ak.getAdded(closest_point.x, closest_point.y, keys[1]);
	    	    	border = new Ellipse(ak.getVertex(keys[1]),new Vector2(5,5),new Vector3(255,120,120),false);
	    	    	borders = Misc.lineup(borders, keys[1], border);
	    	    }
	    	    
			}
		}
		touchDown=false;
		touchDragged=false;
		return false;
	}
	@Override
	public boolean touchMoved (int x, int y) {
		if(mode==2) {
			fillallcircle(false);
			e1.setPosition(new Vector2(x,Gdx.graphics.getHeight()-y));
			Vector2 point =  new Vector2(x,Gdx.graphics.getHeight()-y);
			
			int[] keys = Geometry.find_closest_point_in_vertex(ak.getVertices(), point);
			borders.get(keys[0]).setFill(true);
			borders.get(keys[1]).setFill(true);
			
			Vector2 closest_point = Geometry.calculate_closest_point(ak.getVertex(keys[0]), ak.getVertex(keys[1]), point);

    	    double distance = Math.hypot(closest_point.x-point.x, closest_point.y-point.y);
    	    
    	    if(distance>CLICK_SENSIVITY) {
    	    	errline =new Line(new Vector2(point.x,point.y),new Vector2(closest_point.x,closest_point.y),new Vector3(100, 0,0));
    	    }else {
    	    	errline =new Line(new Vector2(point.x,point.y),new Vector2(closest_point.x,closest_point.y),new Vector3(0, 100,0));
    	    	//ak.getAdded(closest_point.x, closest_point.y, keys[1]);
    	    }
    	    
		}
		return false;
	}
	@Override
	public boolean touchDragged(int x,int y,int pointer)
	{
		//Gdx.app.log("events", "touchDragged");
		touchDragged = true;
		if(touchDown)
		{
			process = true;
			if(first)
				ak.getAdded(x, Gdx.graphics.getHeight()-y);
			else {
				if(mode==2) {
					//
				}else
				if(mode==1 && choosed>-1) {
					ak.setVertex(choosed, x, Gdx.graphics.getHeight()-y);
					borders.get(choosed).setPosition(new Vector2(x, Gdx.graphics.getHeight()-y));
				}
			}
		}		
		return false;
	}
	@Override
	public boolean keyDown (int keycode) {
		fillallcircle(false);
		if(keycode==9)
			mode = 2;
		if(keycode==8) {
			mode = 1;
			choosed = -1;
		}
		return false;
	}
	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {
		//Gdx.app.log("events", "touchDown");
		if(ak==null) {
			ak=new Mesh2d(new Vector2(x,Gdx.graphics.getHeight()-y),new Vector2(0,0),new Vector3(255,0,0),false);
			ak.setRenderMode(GL10.GL_LINE_LOOP);
		}else
		if(first)
			ak.getAdded(x, Gdx.graphics.getHeight()-y);
		else
		if(mode==1) {
			int closest_vertex = ak.getClosestVertexIndex(x,Gdx.graphics.getHeight()-y);
			Vector2 temp=ak.getVertex(closest_vertex);
			if(Math.hypot(temp.x-x, temp.y-Gdx.graphics.getHeight()+y)<CLICK_SENSIVITY) {
				fillallcircle(false);
				borders.get(closest_vertex).setFill(true);
				choosed = closest_vertex;
			}else
			if(choosed>-1) {
				fillallcircle(false);
				ak.setVertex(choosed, x, Gdx.graphics.getHeight()-y);
				borders.get(choosed).setPosition(new Vector2(x, Gdx.graphics.getHeight()-y));
				borders.get(choosed).setFill(true);
			}
		}
		touchDown=true;
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}

