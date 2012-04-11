package com.celoron.guiTest;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;
import com.celoron.engine.GuiButton;
import com.celoron.test.PhysicComp;
import com.celoron.test.TextureRender;

public class GuiTest extends Game {
	/* for dragging */

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
				Gdx.app.log("button", "clicked");

				/* create box that falling with gravity */
				Random generator = new Random();
				Texture t=asset.getTexture("data/box.jpg");
				float scale= (float) (generator.nextDouble()+1)/10;
				
				Entity e = new Entity("falling box", game);
				
				e.setScale(scale);
				e.getPosition().x = -Gdx.graphics.getWidth()/4+generator.nextInt(Gdx.graphics.getWidth()/2);
				e.getPosition().y = Gdx.graphics.getHeight()/4;
				
				e.AddComponent(new TextureRender("render", t));
				e.AddComponent(new PhysicComp("phy", new Vector2(256, 256).mul(scale), BodyType.DynamicBody));
				game.scene.addEntity(e);
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