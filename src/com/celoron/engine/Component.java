package com.celoron.engine;

public abstract class Component {
    protected String id;
    protected Entity owner;
    
    public Component(String id){
    	this.id=id;
    }
 
    public String getId(){
        return id;
    }
 
    public void setOwnerEntity(Entity owner){
    	this.owner = owner;
    }
 
    public abstract void update(Game game);
}