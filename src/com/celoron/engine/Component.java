package com.celoron.engine;

public abstract class Component {
    protected String id;
    protected Entity owner;
    public Game game;
    
    public Component(String id){
    	this.id=id;
    }
 
    public String getId(){
        return id;
    }
 
    public void setOwnerEntity(Entity owner){
    	this.owner = owner;
    	game= owner.game;
    }
 
    public abstract void update();
    public abstract void start();
}