package com.ahmet.polyshaping;

import java.util.ArrayList;
import java.util.TreeMap;

import com.ahmet.b2d.GIcombin;
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

public class Game_Alternative extends ApplicationListener implements InputProcessor, InputAdapter {
	private SpriteBatch batch;
	Mesh2d ak = null;
	Ellipse e1;
	Line l1;
	Line errline = null;
	ArrayList<Ellipse> borders = null;
	boolean first = true;
	boolean choosed;
	int mode = 0;
	boolean process = false;
	boolean touchDown;
	boolean touchDragged;
	int activated_vertex = 0;
	boolean vertexLock=false;
	final private int CLICK_SENSIVITY = 5;
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
		//gl.glEnable(GL10.GL_LINEAR);
		if(ak!=null)
			ak.Draw(batch);
		if(e1!=null && mode==2)
			e1.Draw(batch);
		if(borders!=null) {
			for(int i=0; i<borders.size(); i++) {
				borders.get(i).Draw(batch); 
			}
		}
		if(errline!=null)
			errline.Draw(batch);
			
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
			Ellipse border = new Ellipse(ak.getVertex(0),new Vector2(5,5),new Vector3(255,120,120),false);
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
				for(int i=0; i<borders.size(); i++) {
					borders.get(i).setFill(false);
				}
				TreeMap<Integer,Float> vertexlist  = ak.getSortedClosestVertex(x, Gdx.graphics.getHeight()-y);
				Vector2[] closests = new Vector2[2];
				int[] keys = new int[2];
				int z=0;
				int order = 0;
				for (Integer key :vertexlist.keySet()) {
					if(z>1)
						break;
					closests[z] = borders.get(key).getPosition();
					keys[z] = key;
					borders.get(key).setFill(true);
					order = key;
					Gdx.app.log("vertex", "key/value: " + key + "/"+vertexlist.get(key));
				    z++;
				}
				//closests[1].sub(closests[0]).dot(new Vector2(x,y).sub(v));
				Vector2 point = new Vector2(x,Gdx.graphics.getHeight()-y);
				
				Gdx.app.log("point1", "x: "+Double.toString(closests[0].x)+" y:"+Double.toString(closests[0].y)+" key:"+Integer.toString(keys[0]));
				Gdx.app.log("point2", "x: "+Double.toString(closests[1].x)+" y:"+Double.toString(closests[1].y)+" key:"+Integer.toString(keys[1]));
				Gdx.app.log("point3", "x: "+Double.toString(point.x)+" y:"+Double.toString(point.y));
				
				double xDelta = closests[1].x - closests[0].x;
				double yDelta = closests[1].y - closests[0].y;

				double something = xDelta*xDelta + yDelta*yDelta;

	    	    double u =  ((point.x - closests[0].x) * xDelta + (point.y - closests[0].y) * yDelta) / something;
	    	    Gdx.app.log("u", Double.toString(u));
	    	    if(u > 1) {
	    	        u = 1;
	    	    }else
	    	    if (u < 0) {
	    	    	u = 0;
	    	    }

	    	    int pointx = (int) (closests[0].x + u * xDelta);
	    	    int pointy = (int) (closests[0].y + u * yDelta);

	    	    double dpointx = pointx - point.x;
	    	    double dpointy = pointy - point.y;

	    	    double distance = Math.sqrt(dpointx*dpointx + dpointy*dpointy);
	    	    Gdx.app.log("distance", Double.toString(distance));
	    	    if(distance>CLICK_SENSIVITY) {
	    	    	errline =new Line(new Vector2(point.x,point.y),new Vector2(pointx,pointy),new Vector3(255, 155,255));
	    	    }else {
	    	    	ak.getAdded(pointx, pointy, order+1);
	    	    }
	    	    
			}else
			if(mode==1) {
				activated_vertex=ak.getClosestVertexIndex(x,Gdx.graphics.getHeight()-y);
				Vector2 temp=ak.getVertex(activated_vertex);
				if(Math.hypot(temp.x-x, temp.y-Gdx.graphics.getHeight()+y)<CLICK_SENSIVITY) {
					for(int i=0; i<borders.size(); i++) {
						borders.get(i).setFill(false);
					}
					borders.get(activated_vertex).setFill(true);
					choosed = true;
				}else
				if(choosed) {
					ak.setVertex(activated_vertex, x, Gdx.graphics.getHeight()-y);
					borders.get(activated_vertex).setPosition(new Vector2(x, Gdx.graphics.getHeight()-y));
				}
			}
		}
		touchDown=false;
		touchDragged=false;
		return false;
	}
	@Override
	public boolean touchMoved (int x, int y) {
		e1.setPosition(new Vector2(x,Gdx.graphics.getHeight()-y));
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
					TreeMap<Integer,Float> vertexlist  = ak.getSortedClosestVertex(x, Gdx.graphics.getHeight()-y);
					int z=0;
					for (Integer key :vertexlist.keySet()) {
						if(z>1)
							break;
						borders.get(key).setFill(true);
						Gdx.app.log("vertex", "key/value: " + key + "/"+vertexlist.get(key));
					    z++;
					}
				}else
				if(mode==1 && choosed) {
					ak.setVertex(activated_vertex, x, Gdx.graphics.getHeight()-y);
					borders.get(activated_vertex).setPosition(new Vector2(x, Gdx.graphics.getHeight()-y));
				}
			}
		}		
		return false;
	}
	@Override
	public boolean keyDown (int keycode) {
		Gdx.app.log("sad", Integer.toString(keycode));
		if(keycode==9)
			mode = 2;
		if(keycode==8)
			mode = 1;
		return false;
	}
	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {
		//Gdx.app.log("events", "touchDown");
		if(ak==null) {
			ak=new Mesh2d(new Vector2(x,Gdx.graphics.getHeight()-y),new Vector2(0,0),new Vector3(255,0,0),false);
			//ak.setRenderMode(GL10.GL_LINE_LOOP);
		}else
		if(first)
			ak.getAdded(x, Gdx.graphics.getHeight()-y);
		else {
			if(mode==10) {
				activated_vertex=ak.getClosestVertexIndex(x,Gdx.graphics.getHeight()-y);
				for(int i=0; i<borders.size(); i++) {
					borders.get(i).setFill(false); 
				}
				borders.get(activated_vertex).setFill(true);
			}else
			if(mode==20) {
				
			}
		}
		touchDown=true;
		return false;
	}
}

