package com.celoron.test2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Entity;
import com.celoron.engine.Game;

public class TestGame extends Game {
	/* for dragging */
	Entity line;
	private boolean drag=false;
	
	private City firstCity;
	private City secondCity;
	
	Player player;
	Player ai;
	
	@Override
	public void onCreate() {
		player= new Player("Player", new Color(0,0,1,1));
		ai= new Player("AI", new Color(1,0,0,1));
		
		Entity city=new Entity("City A",this);
		city.setPosition(new Vector2(0,0));
		city.AddComponent(new City("City A", this, player));
		
		scene.addEntity(city);
		
		city=new Entity("City A",this);
		city.setPosition(new Vector2(200,0));
		city.AddComponent(new City("City A", this, player));
		
		scene.addEntity(city);
		
		city=new Entity("City B",this);
		city.setPosition(new Vector2(300,100));
		city.AddComponent(new City("City B", this, ai));
		
		scene.addEntity(city);
		
		line= new Entity("line",this);
		line.AddComponent(new Line("line", new Vector2(100,100), 2));
		scene.addEntity(line);
		
		line.setVisible(false);
	}

	@Override
	public void onUpdate() {
		if(!Gdx.input.isTouched() ){
			drag=false;
			if(firstCity!=null)
				firstCity.release();
			firstCity=null;
			secondCity=null;
		}
		
		if(drag){
			line.setVisible(true);
			
			Vector2 start= firstCity.getPosition().cpy().sub(relativeMousePos()).nor().mul(44);
			
			line.setPosition(firstCity.getPosition().cpy().sub(start));
			((Line)line.getComponent(Line.class)).setDim(relativeMousePos());
		}
		else{
			line.setVisible(false);
		}
	}
	
	public void onRender(){
		
	}

	public void setFirst(City city){
		firstCity=city;
		city.select();
		drag=true;
	}
	
	public void setSecond(City city){
		secondCity=city;
		if(drag)cityDrag();
	}
	
	public void cityDrag(){
		/*
		 * ERRORS:
		 * blank shoot
		 * try to send from enemy city
		 * try to send by itself
		 * 
		 * GAME:
		 * attack another city
		 * support ally city
		 * */
		
		/* Error checks */
		if(secondCity==null){
			Gdx.app.log("Drag", "Blank shoot");
			return;
		}
		if(firstCity.getPlayer()!=player){
			Gdx.app.log("Drag", "Cant send from enemy city");
			return;
		}
		if(firstCity==secondCity){
			Gdx.app.log("Drag", "You cant send yourself");
			return;
		}
		
		/* Game logic */
		if(secondCity.getPlayer()==player){
			Gdx.app.log("Drag", "Support");
			secondCity.setPopulation(secondCity.getPopulation() + firstCity.getPopulation());
			firstCity.setPopulation(0);
		}
		else{
			Gdx.app.log("Drag", "Attack");
			if(firstCity.getPopulation()>secondCity.getPopulation()){
				secondCity.setPlayer(player);
				Gdx.app.log("Drag", "Won");
			}
			secondCity.setPopulation(firstCity.getPopulation()-secondCity.getPopulation());
			firstCity.setPopulation(0);
			
		}
		
		firstCity.refreshText();
		secondCity.refreshText();
	}

	public static void main(String[] args) {
		new JoglApplication(new TestGame(), "Test Game", 800, 600, false);
	}
}