package com.celoron.guiTest;

import java.io.FileNotFoundException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Game;
import com.celoron.engine.GuiButton;

public class GuiTest extends Game {
	@Override
	public void onCreate() {
		
		GuiButton b= new GuiButton(
				this, /* game class reference */
				new Vector2(-64,-32), /* button position */ 
				asset.getTexture("data/bNormal.png"), /* normal img */
				asset.getTexture("data/bHover.png") /* hoverder img */
				)
		{
			/* and overriding onClick function */
			protected void onClick(){
				
				/* create script engine */
				ScriptEngineManager manager = new ScriptEngineManager();
		        ScriptEngine engine = manager.getEngineByName("JavaScript");
		        
		        /* scrit can use game object */
		        engine.put("game", game);
		        
		        try {
					engine.eval(new java.io.FileReader("data/click.js"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		};
		
		gui.addGuiObject(b); /* button is ready, lets put into gui */
	}

	@Override
	public void onUpdate() {
		
	}

	public static void main(String[] args) {
		new JoglApplication(new GuiTest(), "Gui Test", 800, 600, false);
	}
}