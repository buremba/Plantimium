package com.celoron.engine;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Entity {
	 
    String id;
 
    Vector2 position;
    float scale;
    float rotation;
 
    RenderComponent renderComponent = null;
 
    ArrayList<Component> components = null;
 
    public Entity(String id){
        this.id = id;
 
        components = new ArrayList<Component>();
 
        position = new Vector2(0,0);
        scale = 1;
        rotation = 0;
    }
 
    public void AddComponent(Component component)
    {
        if(RenderComponent.class.isInstance(component))
            renderComponent = (RenderComponent)component;
 
        component.setOwnerEntity(this);
        components.add(component);
    }
 
    public Component getComponent(String id)
    {
        for(Component comp : components){
		    if ( comp.getId().equalsIgnoreCase(id) )
		        return comp;
	}
 
	return null;
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
 
    public void update(Game game)
    {
        for(Component component : components){
            component.update(game);
        }
    }
 
    public void render(Game game)
    {
        if(renderComponent != null)
            renderComponent.render(game);
    }
}