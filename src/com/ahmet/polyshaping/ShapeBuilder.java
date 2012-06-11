package com.ahmet.polyshaping;

import java.util.ArrayList;
import java.util.List;

import com.ahmet.b2d.Drawable;
import com.ahmet.b2d.RenderStack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ShapeBuilder extends Drawable implements InputProcessor {

	boolean allowEdit=true,first=true;
	boolean touchDown;
	boolean touchDragged;
	boolean inDragStarted;
	boolean showSmooth=false;
	Ellipse Indicator;
	Mesh2d shape = null,smoothPol=null;
	Vector2 touchpoint,dragStart=new Vector2();;
	Game3 game;
	ArrayList<Integer> lockedVertices=new ArrayList();
	ArrayList<Vector2> lockedFrom=new ArrayList();
	
	ArrayList<com.ahmet.b2d.Rect> borders = new ArrayList<com.ahmet.b2d.Rect>();
	final private int CLICK_SENSIVITY = 25;
	final private int MIN_SMOOTH_SENSIVITY = 20;
	final private int MAX_SMOOTH_SENSIVITY = 25;
	RenderStack rs;
	Texture borderTexture;
	TextureRegion bregion;
	ShapeBuilder(Game3 g)
	{
		Gdx.input.setInputProcessor(this);	
		Indicator=new Ellipse(new Vector2(100,100),new Vector2(25,25),new Vector3(155,56,55),true);
		touchpoint = new Vector2();
		Gdx.input.setInputProcessor(this);
		game=g;
		borderTexture=new Texture(Gdx.files.internal("data/dis.png"));
		bregion=new TextureRegion(borderTexture, 0, 0, 64, 64);
		rs=new RenderStack();
	}
	@Override
	public void Draw(SpriteBatch s) {
		rs.Draw();
		if(shape!=null)
		{
			shape.Draw(s);
		}
		Indicator.Draw(s);
		if(borders!=null) {
			for(int i=0; i<borders.size(); i++) {
				borders.get(i).Draw(s);
			}
		}
		
	}
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		game.getCamera().unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		
		if(shape==null) {
			shape=new Mesh2d(new Vector2(touchpoint.x,touchpoint.y),new Vector2(0,0),new Vector3(0,0,255),false);
			shape.setRenderMode(GL10.GL_TRIANGLES);
		}
		if(!allowEdit && shape!=null)
		{
			Vector2[] vlist2=shape.getVertices();
			List<Vector2> vertices=new ArrayList<Vector2>();
			for(int i=0; i<vlist2.length; i++)
			{
				vertices.add(vlist2[i]);
			}
			if(Intersector.isPointInPolygon(vertices,touchpoint))
			{
				//System.out.println("IGNORED EDIT");
			}
			//shape.smoothRender(false);
			closeCapture();
		}
		dragStart.x = touchpoint.x;
		dragStart.y	= touchpoint.y;
		touchDown=true;
		return false;
	}
	public void closeCapture()
	{
		for(int i=0; i<shape.getVertices().length; i++)
		{
			Vector2 vertex=shape.getVertex(i);
			if(Math.hypot(vertex.x-touchpoint.x, vertex.y-touchpoint.y)<25)
			{
				lockedVertices.add(i);
				lockedFrom.add(vertex);
			}
		}		
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		game.getCamera().unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;

		touchDragged = true;
		if(touchDown && allowEdit)
		{
			allowEdit = true;
			if(first && allowEdit){
				
				if(shape.getVertices().length==0)
				{
					shape.getAdded(touchpoint.x, touchpoint.y);
				}
				else 
				{
					Vector2 pre=shape.getVertex(shape.getVertices().length-1);
					if(Math.hypot(pre.x-touchpoint.x, pre.y-touchpoint.y)>25)
					{
						shape.getAdded(touchpoint.x, touchpoint.y);
					}
				}
			}
			Vector2[] vlist=shape.getVertices();
			rs.stack.removeAllElements();
			for(int i=0; i<vlist.length; i++)
			{				
				rs.stack.add(new com.ahmet.b2d.Rect(new Vector2(vlist[i].x-25,vlist[i].y-25), new Vector2(50,50), bregion));
			}	
		}
		if(!allowEdit && shape!=null)
		{
			int[] keys = Geometry.find_closest_point_in_vertex(shape.getVertices(), dragStart);
			Vector2 closest_point = Geometry.calculate_closest_point(shape.getVertex(keys[0]), shape.getVertex(keys[1]), touchpoint);
			Indicator.setPosition(closest_point);

			for(int i=0; i<lockedVertices.size(); i++)
			{
				int indis=lockedVertices.get(i);
				Vector2 temp=lockedFrom.get(i);
				shape.setVertex(indis, temp.x+(touchpoint.x-dragStart.x), temp.y+(touchpoint.y-dragStart.y));
			}
			if(lockedVertices.size()==0)
			{
				shape.getAdded(touchpoint.x, touchpoint.y,keys[1]);
				closeCapture();
			}
			
		}
		
		return false;
	}
	@Override
	public boolean touchMoved(int x, int y) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		game.getCamera().unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y = touchpointv3.y;
		if(!allowEdit && shape!=null)
		{
			int[] keys = Geometry.find_closest_point_in_vertex(shape.getVertices(), touchpoint);
			Vector2 closest_point = Geometry.calculate_closest_point(shape.getVertex(keys[0]), shape.getVertex(keys[1]), touchpoint);
			Indicator.setPosition(closest_point);
		}
		return false;
	}
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		Vector3 touchpointv3 =new Vector3(x,y,0); //where x and y are tap inputs
		game.getCamera().unproject(touchpointv3);
		touchpoint.x = touchpointv3.x;
		touchpoint.y	= touchpointv3.y;
		lockedVertices=new ArrayList();
		lockedFrom=new ArrayList();
		reVertex();
		Indicator.setPosition(touchpoint);
		allowEdit=false;
		touchDown=false;
		touchDragged=false;
		shape.smoothRender(true);
		Vector2[] vlist=shape.getVertices();
		rs.stack.removeAllElements();
		for(int i=0; i<vlist.length; i++)
		{			
			rs.stack.add(new com.ahmet.b2d.Rect(new Vector2(vlist[i].x-25,vlist[i].y-25), new Vector2(50,50), bregion));
		}
		return false;
	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==62)
		{

		}
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	public void reVertex()
	{
		ArrayList<Ellipse> borders = new ArrayList<Ellipse>();
		ArrayList<Vector2> smooth_vertex = new ArrayList<Vector2>();
		smooth_vertex.add(shape.getVertex(0));
		Ellipse border = new Ellipse(shape.getVertex(0),new Vector2(2,2),new Vector3(255,120,120),false);
		borders.add(border);
		Vector2 temp = null;
		Vector2 tempvector =null;
		for(int i=0; i<shape.getVertices().length; i++) {
			if(temp != null) {
					if(i+1==shape.getVertices().length)
						i = 0;
					int dist = (int) Math.hypot(shape.getVertex(i).x-temp.x, shape.getVertex(i).y-temp.y);
					if(dist>MAX_SMOOTH_SENSIVITY) {
						for(int z=1; z<dist/MAX_SMOOTH_SENSIVITY; z++) {
							tempvector = new Vector2(
									temp.x + (shape.getVertex(i).x-temp.x) *(z / (float) (dist/MAX_SMOOTH_SENSIVITY)), 
									temp.y + (shape.getVertex(i).y-temp.y)*(z / (float) (dist/MAX_SMOOTH_SENSIVITY)));
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
							lastvector = shape.getVertex(i);
						smooth_vertex.add(lastvector);
						border = new Ellipse(lastvector,new Vector2(2,2),new Vector3(255,120,120),false);
						borders.add(border);
						temp = lastvector;
					}
					if (i==0)
						break;
			}
			else {
				smooth_vertex.add(shape.getVertex(i));
				border = new Ellipse(shape.getVertex(i),new Vector2(2,2),new Vector3(255,120,120),false);
				borders.add(border);
				temp = shape.getVertex(i);
			}
			tempvector = null;
		}
		Vector2[] vArray = new Vector2[smooth_vertex.size()];
		smooth_vertex.toArray(vArray);
		shape.setVertices(vArray);
	}

}
