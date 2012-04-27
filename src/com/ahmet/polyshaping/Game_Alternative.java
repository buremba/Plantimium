package com.ahmet.polyshaping;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
	final private int MIN_SMOOTH_SENSIVITY = 20;
	final private int MAX_SMOOTH_SENSIVITY = 25;
	ArrayList<Ellipse> more = null;
	private int active_more = -1;
	private OrthographicCamera cam;
	private Rectangle glViewport;
	private Vector2 touchpoint;
	@Override
	public void create () {

		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		e1=new Ellipse(new Vector2(60,60),new Vector2(10,10),new Vector3(155,56,55),false);
		more = new ArrayList<Ellipse>();
		errline =new Line(new Vector2(0,0),new Vector2(0,0),new Vector3(100, 0,0));
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		touchpoint = new Vector2();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	@Override
	public void render () {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_LINEAR);
		
		gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
		
		cam.update();
		cam.apply(gl);
		batch.setProjectionMatrix(cam.combined);
		
		if(ak!=null)
			ak.Draw(batch);
		if(e1!=null && mode==2)
			e1.Draw(batch);
		if(borders!=null && mode!=3) {
			for(int i=0; i<borders.size(); i++) {
				borders.get(i).Draw(batch);
			}
		}
		if(more!=null && mode == 3) {
			for(int i=0; i<more.size(); i++) {
				more.get(i).Draw(batch); 
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
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		
		e1.setPosition(touchpoint);
		if(process && first) {
			first = false;
			if(l1==null)
				l1=new Line(new Vector2(touchpoint.x,touchpoint.y),new Vector2(100,100),new Vector3(255,255,255));
			
			borders = new ArrayList<Ellipse>();
			ArrayList<Vector2> smooth_vertex = new ArrayList<Vector2>();
			smooth_vertex.add(ak.getVertex(0));
			border = new Ellipse(ak.getVertex(0),new Vector2(2,2),new Vector3(255,120,120),false);
			borders.add(border);
			Vector2 temp = null;
			Vector2 tempvector =null;
			for(int i=0; i<ak.getVertices().length; i++) {
				if(temp != null) {
						if(i+1==ak.getVertices().length)
							i = 0;
						int dist = (int) Math.hypot(ak.getVertex(i).x-temp.x, ak.getVertex(i).y-temp.y);
						if(dist>MAX_SMOOTH_SENSIVITY) {
							for(int z=1; z<dist/MAX_SMOOTH_SENSIVITY; z++) {
								tempvector = new Vector2(temp.x + (ak.getVertex(i).x-temp.x) *(z / (float) (dist/MAX_SMOOTH_SENSIVITY)), temp.y + (ak.getVertex(i).y-temp.y)*(z / (float) (dist/MAX_SMOOTH_SENSIVITY)));
								smooth_vertex.add(tempvector);
								border = new Ellipse(tempvector,new Vector2(2,2),new Vector3(255,120,120),false);
								borders.add(border);
							}
						}
						if(dist>MIN_SMOOTH_SENSIVITY && i>0) {
							Vector2 lastvector;
							if(tempvector!=null) 
								lastvector = tempvector;
							else
								lastvector = ak.getVertex(i);
							smooth_vertex.add(lastvector);
							border = new Ellipse(lastvector,new Vector2(2,2),new Vector3(255,120,120),false);
							borders.add(border);
							temp = lastvector;
						}
						if (i==0)
							break;
				}
				else {
					smooth_vertex.add(ak.getVertex(i));
					border = new Ellipse(ak.getVertex(i),new Vector2(2,2),new Vector3(255,120,120),false);
					borders.add(border);
					temp = ak.getVertex(i);
				}
				tempvector = null;
			}
			Vector2[] vArray = new Vector2[smooth_vertex.size()];
			smooth_vertex.toArray(vArray);
			ak.setVertices(vArray);
			process = false;
		}else
		if(!first) {
			if(mode==2) {
				fillallcircle(false);
				
				int[] keys = Geometry.find_closest_point_in_vertex(ak.getVertices(), touchpoint);
				borders.get(keys[0]).setFill(true);
				borders.get(keys[1]).setFill(true);
				
				Vector2 closest_point = Geometry.calculate_closest_point(ak.getVertex(keys[0]), ak.getVertex(keys[1]), touchpoint);

	    	    double distance = Math.hypot(closest_point.x-touchpoint.x, closest_point.y-touchpoint.y);
	    	    
	    	    if(distance>CLICK_SENSIVITY) {
	    	    	errline =new Line(new Vector2(touchpoint.x,touchpoint.y),new Vector2(closest_point.x,closest_point.y),new Vector3(0, 34,34));
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
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y = touchpointv3.y;
		
		if(mode==2) {
			fillallcircle(false);
			e1.setPosition(touchpoint);
			int[] keys = Geometry.find_closest_point_in_vertex(ak.getVertices(), touchpoint);
			borders.get(keys[0]).setFill(true);
			borders.get(keys[1]).setFill(true);
			
			Vector2 closest_point = Geometry.calculate_closest_point(ak.getVertex(keys[0]), ak.getVertex(keys[1]), touchpoint);

    	    double distance = Math.hypot(closest_point.x-touchpoint.x, closest_point.y-touchpoint.y);
    	    
    	    if(distance>CLICK_SENSIVITY) {
    	    	errline.setPos1(new Vector2(touchpoint.x,touchpoint.y));
    	    	errline.setPos2(new Vector2(closest_point.x,closest_point.y));
    	    	errline.setColor(new Vector3(100, 0,0));
    	    }else {
    	    	errline.setPos1(new Vector2(touchpoint.x,touchpoint.y));
    	    	errline.setPos2(new Vector2(closest_point.x,closest_point.y));
    	    	errline.setColor(new Vector3(0, 100,0));
    	    }
		}
		else
		if(mode==3) {
				fillallcircle(false);
				e1.setPosition(touchpoint);
				
				int[] keys = Geometry.find_closest_point_in_vertex(ak.getVertices(), touchpoint);
				
				Vector2 closest_point = Geometry.calculate_closest_point(ak.getVertex(keys[0]), ak.getVertex(keys[1]), touchpoint);
				
	    	    //double distance = Math.hypot(closest_point.x-point.x, closest_point.y-point.y);
	    	    
	    	    //if(distance>CLICK_SENSIVITY) {
					if(active_more!=-1)
						more.get(active_more).setPosition(new Vector2(closest_point.x,closest_point.y));
	    	    //}else {
	    	    	//ak.getAdded(closest_point.x, closest_point.y, keys[1]);
	    	    //}
	    	    
			}
		return false;
	}
	@Override
	public boolean touchDragged(int x,int y,int pointer) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;

		touchDragged = true;
		if(touchDown)
		{
			process = true;
			if(first)
				ak.getAdded(touchpoint.x, touchpoint.y);
			else {
				if(mode==2) {
					//
				}else
				if(mode==1 && choosed>-1) {
					int o = 0;
					for(int i=choosed-5; i<choosed+5; i++) {
						if(i<choosed) {
							ak.setVertex(i, (float) (ak.getVertex(choosed-5).x + (touchpoint.x-ak.getVertex(choosed-5).x) * (o / 5.0)), (float) (ak.getVertex(choosed-5).y + (touchpoint.y-ak.getVertex(choosed-5).y) * (o / 5.0)));
							//Gdx.app.log("a0", Integer.toString(i)+ " "+ak.getVertex(i).toString());
						}else
						if(i==choosed) {
							ak.setVertex(choosed, touchpoint.x, touchpoint.y);
							//Gdx.app.log("a0", Integer.toString(i)+ " "+ak.getVertex(i).toString());
						}else
						if(i>choosed) {
							ak.setVertex(i, (float) (touchpoint.x + (ak.getVertex(choosed+5).x-touchpoint.x) * ((o-5) / 5.0)), (float) (touchpoint.y + (ak.getVertex(choosed+5).y-touchpoint.y) * ((o-5) / 5.0)));
							//Gdx.app.log("a0", Integer.toString(i)+ " "+ak.getVertex(i).toString());
						}
						borders.get(i).setPosition(ak.getVertex(i));
						o++;
					}
				}
			}
		}		
		return false;
	}
	@Override
	public boolean keyDown (int keycode) {
		fillallcircle(false);
		//Gdx.app.log("keyDown", Integer.toString(keycode));
		if(keycode==9)
			mode = 2;
		if(keycode==10)
			mode = 3;
		if(keycode==8) {
			mode = 1;
			choosed = -1;
		}else
		if(keycode==Input.Keys.MINUS) {
			 cam.zoom += 0.06;
		}else
		if(keycode==Input.Keys.PLUS) {
			cam.zoom -= 0.06;
		}else
		if(keycode==Input.Keys.LEFT) {
            if (cam.position.x > 0)
                    cam.translate(-8, 0, 0);
	    }else
	    if(keycode==Input.Keys.RIGHT) {
	            if (cam.position.x < 1024)
	                    cam.translate(8, 0, 0);
	    }else
	    if(keycode==Input.Keys.DOWN) {
	            if (cam.position.y > 0)
	                    cam.translate(0, -8, 0);
	    }else
	    if(keycode==Input.Keys.UP) { // up
	            if (cam.position.y < 1024)
	                    cam.translate(0, 8, 0);
	    }
	    if(keycode==Input.Keys.Z) {
	            cam.rotate(-6, 0, 0, 1);
	    }
	    if(keycode==Input.Keys.X) {
	            cam.rotate(6, 0, 0, 1);
	    }
			
		return false;
	}
	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		cam.unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;

		if(ak==null) {
			ak=new Mesh2d(new Vector2(touchpoint.x,touchpoint.y),new Vector2(0,0),new Vector3(255,0,0),false);
			ak.setRenderMode(GL10.GL_LINE_LOOP);
		}else
		if(first)
			ak.getAdded(touchpoint.x, touchpoint.y);
		else
		if(mode==1) {
			int closest_vertex = ak.getClosestVertexIndex(touchpoint.x,touchpoint.y);
			Vector2 temp=ak.getVertex(closest_vertex);
			if(Math.hypot(temp.x-touchpoint.x, temp.y-touchpoint.y)<CLICK_SENSIVITY) {
				fillallcircle(false);
				borders.get(closest_vertex).setFill(true);
				choosed = closest_vertex;
			}else
			if(choosed>-1) {
				fillallcircle(false);
				ak.setVertex(choosed, touchpoint.x, touchpoint.y);
				borders.get(choosed).setPosition(new Vector2(touchpoint.x,touchpoint.y));
				borders.get(choosed).setFill(true);
			}
		}else
		if(mode==3) {
			fillallcircle(false);
			e1.setPosition(touchpoint);
			
			int[] keys = Geometry.find_closest_point_in_vertex(ak.getVertices(), touchpoint);
			
			Vector2 closest_point = Geometry.calculate_closest_point(ak.getVertex(keys[0]), ak.getVertex(keys[1]), touchpoint);
	    	more.add(new Ellipse(new Vector2(closest_point.x,closest_point.y),new Vector2(10,10),new Vector3(25,10,10),true));
	    	active_more  = more.size()-1;
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
		cam.setToOrtho(false, arg0, arg1);
		glViewport.width = arg0;
		glViewport.height = arg1;
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}

