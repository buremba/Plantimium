package com.celoron.engine;


public abstract class RenderComponent extends Component {
	 
    public RenderComponent(String id){
    	super(id);
    }
 
    public abstract void render(Game game);
}