package com.celoron.engine.core;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
	private Map<String, Class<? extends Component>> components=new HashMap<String, Class<? extends Component>>();
	private Map<String, Element> types=new HashMap<String, Element>();
	
	Map<String, Texture> textures;
	public Game game;
	public AssetManager(Game game){
		textures=new HashMap<String, Texture>();
		
		this.game=game;
	}
	
	/*
	 * This method return texture of given file path,
	 * if texture has been loaded, return texture
	 * else first load texture then return it
	 * */
	public Texture getTexture(String file){
		if(textures.containsKey(file)){
			return textures.get(file);
		}
		else{
			Texture t=new Texture(file);
			textures.put(file, t);
			return t;
		}
	}
	
	public void registerComponent(Class<? extends Component> class1, String name){
		components.put(name, class1);
	}
	
	public Component loadComponent(Element data){
		String name = data.getAttribute("name");
		
		Class<? extends Component> c= components.get(name);
		if(c==null){
			Gdx.app.log("Component loader", "can find component: "+name);
			return null;
		}
		for(Method m: c.getMethods()){
			if(m.getName()=="loadFromXml"){
				try {
					return (Component)m.invoke(null, game, data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public void loadTypes(String file){
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList ntypes = doc.getElementsByTagName("type");
			for (int i = 0; i < ntypes.getLength(); i++) {
				Element component = (Element) ntypes.item(i);
				types.put(component.getAttribute("name"), component);
				Gdx.app.log("Type load", component.getAttribute("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Entity createEntity(String name){
		Entity entity=new Entity(game);
		try {
			Element type = types.get(name);

			NodeList components = type.getElementsByTagName("component");
			for (int i = 0; i < components.getLength(); i++) {
				Element component = (Element) components.item(i);
				Component c=loadComponent(component);
				if(c==null){
					Gdx.app.log("Component loader", "asset manager cannot load component");
				}
				else{
					entity.addComponent(c);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
}