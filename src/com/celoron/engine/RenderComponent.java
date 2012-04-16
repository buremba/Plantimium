package com.celoron.engine;


public abstract class RenderComponent extends Component { 
    public abstract void render();
    
    /* well, because render component not needed update usually */
    public void update(){}
}