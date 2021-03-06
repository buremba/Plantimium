package com.celoron.engine.core;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.celoron.engine.gui.GuiButton;
import com.celoron.engine.gui.GuiManager;
import com.celoron.engine.physic.PhysicListener;

public abstract class Game extends InputAdapter implements ApplicationListener {
	public OrthographicCamera camera;
	public SpriteBatch batch;

	/* to calculate fdt: frame delta time */
	public float deltaTime;
	public float lastFrameTime;
	
	/* you can slow down your game by setting gameSpeed to another float
	 * this will effect physic engine and particle engine 
	 * you can do this in-game realtime
	 * 
	 * exmaple: 
	 * game.gameSpeed=0.5f; 
	 * will slow down game to %50 
	 */
	public float gameSpeed=1.0f;

	/* Managers */
	public SceneManager scene; /* scene manager to manage entitys */
	public InputManager input;
	public AssetManager asset;
	public GuiManager gui;
	
	/* its for referencing to gl function */
	public GL10 gl;

	/* box2d world object */
	public World world;
	
	public BitmapFont font;

	public boolean needsGL20() {
		return false;
	}

	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0, 0, 0);
		batch = new SpriteBatch();

		scene = new SceneManager();
		asset= new AssetManager(this);
		input= new InputManager();
		gui= new GuiManager(this);
		
		/* set main input listener */
		Gdx.input.setInputProcessor(input);

		gl = Gdx.graphics.getGL10();

		/* create physic world */
		world = new World(new Vector2(0, -10), true);
		ContactListener listener= new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
			
			@Override
			public void endContact(Contact contact) {
				
			}
			
			@Override
			public void beginContact(Contact contact){
				/*
				 * this function sending information to contacted entitys
				 * */
				Object data1 =contact.getFixtureA().getUserData();
				Object data2 =contact.getFixtureB().getUserData();
				if(data1 instanceof Entity && data2 instanceof Entity){
					Entity e1= (Entity) data1;
					Entity e2= (Entity) data2;
					PhysicListener listener1= (PhysicListener) e1.getComponent(PhysicListener.class);
					PhysicListener listener2= (PhysicListener) e2.getComponent(PhysicListener.class);

					if(listener1!=null)
						listener1.onContact(e2, contact);
					if(listener2!=null)
						listener2.onContact(e1, contact);
				}
			}
		};
		world.setContactListener(listener);

		lastFrameTime = System.nanoTime();
		
		/* load default font */
		font = new BitmapFont(Gdx.files.internal("data/font/tahoma.fnt"), Gdx.files.internal("data/font/tahoma.png"), false);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		/* this actually call game logic creating, not game engine */
		onCreate();
	}
	
	public void loadScene(String file){
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList buttons = doc.getElementsByTagName("button");
			for (int i = 0; i < buttons.getLength(); i++) {
				Element button = (Element) buttons.item(i);
				
				Element epos = (Element) button.getElementsByTagName("position").item(0);
				Vector2 pos= new Vector2(Integer.parseInt(epos.getAttribute("x")), Integer.parseInt(epos.getAttribute("y")));
				
				Element img = (Element) button.getElementsByTagName("imgnormal").item(0);
				Element imgHover = (Element) button.getElementsByTagName("imghover").item(0);
				Element action = (Element) button.getElementsByTagName("action").item(0);
				
				GuiButton b= new GuiButton(
						this, /* game class reference */
						pos, /* button position */ 
						asset.getTexture(img.getAttribute("src")), /* normal img */
						asset.getTexture(imgHover.getAttribute("src")), /* hoverder img */
						action.getChildNodes().item(0).getNodeValue()
						)
				{
					/* and overriding onClick function */
					protected void onClick(){			        
				        try {
							//game.script.eval(this.action);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				};
				
				gui.addGuiObject(b);
			}
			
			NodeList entitys = doc.getElementsByTagName("entity");
			for (int i = 0; i < entitys.getLength(); i++) {
				Element eElement = (Element) entitys.item(i);
				
				Gdx.app.log("Entity", "name:"+eElement.getAttribute("name")+" type:"+eElement.getAttribute("type"));
				
				Entity entity;
				
				/* type */
				String type= eElement.getAttribute("type");
				if(type==""){
					entity= new Entity(this);
				}
				else{
					entity = asset.createEntity(type);
				}
				
				/* name */
				String name= eElement.getAttribute("name");
				if(name!="")
					entity.setName(name);
				
				/* pos x-y */
				float x=0;
				float y=0;
				String sx= eElement.getAttribute("x");
				if(sx!="")x=Float.parseFloat(sx);
				String sy= eElement.getAttribute("y");
				if(sy!="")y=Float.parseFloat(sy);
				
				entity.getPosition().set(x, y);
				
				/* scale */
				float scale=1.0f;
				String sscale= eElement.getAttribute("scale");
				if(sscale!="")scale=Float.parseFloat(sscale);
				
				entity.setScale(scale);
				
				/* rotation */
				float rotation=0.0f;
				String srotation= eElement.getAttribute("rot");
				if(srotation!="")rotation=Float.parseFloat(srotation)/2;
				
				entity.setRotation(rotation);
				
				Gdx.app.log("roration", ""+entity.getRotation());
				
				
				/* adding special component */
				NodeList components = eElement.getElementsByTagName("component");
				for (int j = 0; j < components.getLength(); j++) {
					Element component = (Element) components.item(j);
					Component c=asset.loadComponent(component);
					if(c==null){
						Gdx.app.log("Component loader", "asset manager cannot load component");
					}
					else{
						entity.addComponent(c);
					}
				}
				
				scene.addEntity(entity);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resume() {
		
	}

	public void render() {
		/* calculation of fdt */
		deltaTime = (System.nanoTime() - lastFrameTime) / 1000000000.0f;
		lastFrameTime = System.nanoTime();
		
		deltaTime*=gameSpeed;
		
		/*
		 * 1.update physic 
		 * 2.update all entity, and its components
		 * 3.update game logic (class that extends from Game)
		 * */
		world.step(deltaTime ,8,10);
		scene.updateAll(this);
		onUpdate();
		
		/* clear screen */
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		/* stupid camera methods */
		camera.update();
		camera.apply(gl);

		/* batch begin for texture rendering */
		batch.begin();
		
		batch.getProjectionMatrix().set(camera.combined);
		
		/* and finally render everything */
		scene.renderAll(this);
		
		gui.renderAll();
		onRender();
		
		
		batch.end();
	}

	public void resize(int width, int height) {
		
	}

	public void pause() {

	}

	public void dispose() {

	}

	public abstract void onCreate();

	public abstract void onUpdate();

	/* this is just for experimantel method. please dont use this */
	public void onRender(){}

	/* this method give actual position of final mouse clicked 
	 * why this method in Game class? well, because f*ck you thats why*/
	public Vector2 relativeMousePos() {
		return new Vector2(Gdx.input.getX() + camera.position.x
				- Gdx.graphics.getWidth() / 2, -Gdx.input.getY()
				+ camera.position.y + Gdx.graphics.getHeight() / 2);
	}
	
	public Vector2 convertMousePos(Vector2 v){
		return new Vector2(v.x + camera.position.x
				- Gdx.graphics.getWidth() / 2, -v.y
				+ camera.position.y + Gdx.graphics.getHeight() / 2);
	}
}
