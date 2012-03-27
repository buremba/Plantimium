package com.celoron.engine;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

public class Entity {
    String id;
 
    Vector2 position;
    float scale;
    float rotation;
 
    RenderComponent renderComponent = null;
 
    LinkedList<Component> components = null;
    LinkedList<Component> componentsToRemove = new LinkedList<Component>();
    
    public Game game;
 
    public Entity(String id, Game game){
        this.id = id;
 
        components = new LinkedList<Component>();
 
        position = new Vector2(0,0);
        scale = 1;
        rotation = 0;
        
        this.game=game;
    }
 
    public void AddComponent(Component component){
        if(RenderComponent.class.isInstance(component))
            renderComponent = (RenderComponent)component;
 
        component.setOwnerEntity(this);
        components.add(component);
    }
 
    public Component getComponent(String id){
        for(Component comp : components){
		    if ( comp.getId().equalsIgnoreCase(id) )
		        return comp;
        }
 
        return null;
    }
    
    public void removeComponent(Component comp){
    	componentsToRemove.add(comp);
    }
 
    public Vector2 getPosition(){
    	return position;
    }
 
    public float getScale(){
    	return scale;
    }
 
    public float getRotation(){
    	return rotation;
    }
 
    public String getId(){
    	return id;
    }
 
    public void setPosition(Vector2 position) {
    	this.position = position;
    }
 
    public void setRotation(float rotate) {
        rotation = rotate;
    }
 
    public void setScale(float scale) {
    	this.scale = scale;
    }
    
    public void start(){
        for(Component component : components){
            component.start();
        }
    }
    
    public void update(){
    	for(Component component : componentsToRemove){
    		components.remove(component);
    	}
    	componentsToRemove.clear();
    	
        for(Component component : components){
            component.update();
        }
    }
 
    public void render(){
        if(renderComponent != null)
            renderComponent.render();
    }
    
    public void removeAllComponent(){
    	components.clear();
    }
}