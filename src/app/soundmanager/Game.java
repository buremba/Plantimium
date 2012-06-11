package app.soundmanager;

import java.util.ArrayList;
import java.util.Iterator;

import app.shaping.Ellipse;
import app.shaping.Mesh2d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Game extends InputAdapter implements ApplicationListener,InputProcessor {
	private SpriteBatch batch;
	private boolean biggerCircle = false;
	float step=0;
	Thread thread;
	boolean stop = false;
	Ellipse e1;
	float stepinc = 0.1f;
	float discarpan=100;
	Mesh2d graph;
	float scale = 1;
	float samples[];
	ArrayList<Vector2> graphVertices;
	private OrthographicCamera cam;
	private Rectangle glViewport;
	private int time=0;
	Ses ses;
	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		graph = new Mesh2d(new Vector2(20,20),new Vector3(155,56,55));
		graphVertices = new ArrayList<Vector2>();
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ses=new Ses();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (thread == null) {
			thread = new Thread(ses);
			thread.start();
		}
		e1= new Ellipse(new Vector2(50,Gdx.graphics.getHeight()-100),new Vector2(50,50), new Vector3(255,0,0) , false);
	}
	@Override
	public boolean touchMoved (int x, int y) {
		//discarpan=x*100;
		stepinc=(y+90)/500.0f;
		return false;
	}
	public boolean touchUp (int x, int y, int pointer, int button) {
		this.biggerCircle = false;
		ses.sesler.add(new SesParticle(new Vector2(x,Gdx.graphics.getHeight()-y)));
		return false;
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
		
		if(e1!=null) {
			if(this.biggerCircle) {
				e1.setRadius(new Vector2(e1.getRadius().x+1,e1.getRadius().y+1));
			}
			//e1.Draw(batch);
		}
		for(int i=0; i<ses.sesler.size(); i++)
		{
			ses.sesler.get(i).Draw(batch);
		}
		if(this.time>Gdx.app.getGraphics().getWidth()) {
			graphVertices.remove(0);
			Iterator<Vector2> itr = graphVertices.iterator();
		    while (itr.hasNext()) {
		      itr.next().x--;
		    }
		}else {
			this.time++;
		}
		float y = ((0.5f * (float)Math.sin(step)*discarpan))*scale;
		if(Math.abs(y)>200) {
			scale = 400/Math.abs(y);
			
			Iterator<Vector2> itr = graphVertices.iterator();
		    while (itr.hasNext()) {
		      Vector2 current = itr.next();
		      current.y *= scale;
		    }
		    if(y>0)
		    	y = +200;
		    else
		    	y = -200;
		}
		y += 200;
		graphVertices.add(new Vector2((float) this.time, stepinc*100));
		graph.setVertices(graphVertices.toArray(new Vector2[graphVertices.size()]));
		graph.setRenderMode(GL10.GL_LINE_STRIP);
		graph.Draw(batch);
			
	}
	@Override
	public void dispose () {
		//world.dispose();
		ses.stop=true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean touchDown (int x, int y, int pointer, int newParam) {
		Vector3 touchpointv3 =new Vector3(x,y,0);
		cam.unproject(touchpointv3);
		Vector2 point = new Vector2(touchpointv3.x, touchpointv3.y);
		this.e1 = new Ellipse(new Vector2(point.x,point.y),new Vector2(20,20),new Vector3(155,56,55),false);
		this.biggerCircle = true;
		return false;	
		
	}
	@Override
	public void pause() {}
	@Override
	public void resize(int arg0, int arg1) {}
	@Override
	public void resume() {}
}