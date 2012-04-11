package com.celoron.test2;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.celoron.engine.Component;
import com.celoron.engine.Entity;
import com.celoron.test.TextureRender;

public class City extends Component implements InputProcessor {
	private Entity city;
	private TextRender text;
	
	private int population;
	private float timeToRefresh=0;
	
	public TestGame testGame;
	
	private Player player;
	
	public City(String id, TestGame game, Player player){
		super(id);
		
		this.testGame=game;
		
		city=new Entity("City",game);
		city.setPosition(new Vector2(0,0));
		city.AddComponent(new TextureRender("render", game.asset.getTexture("data/city.png")));
		
		game.scene.addEntity(city);
		
		Entity textEntity=new Entity("text",game);
		text=new TextRender("render", "0", new Color(0,0,1,1) );
		textEntity.AddComponent(text);
		textEntity.AddComponent(new FollowParent("follow", city, new Vector2(-15,15)));
		
		game.scene.addEntity(textEntity);
		
		setPlayer(player);
	}
	
	public int getPopulation(){
		return population;
	}
	
	public void setPopulation(int pop){
		population=pop;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player player){
		this.player=player;
		
		text.setColor(player.getColor());
	}

	@Override
	public void update() {
		timeToRefresh-=game.deltaTime;
		if(timeToRefresh<0){
			refresh();
			timeToRefresh=1.0f;
		}
		
		city.setPosition(owner.getPosition());
	}

	@Override
	public void start() {
		population=0;
	}

	@Override
	public void remove() {
		
	}
	
	private void refresh(){
		population+=1;
		
		refreshText();
	}
	
	public void refreshText(){
		text.setText(""+population);
	}
	
	public Vector2 getPosition(){
		return city.getPosition();
	}

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		Vector2 v=game.convertMousePos(new Vector2(x, y));
		
		if(v.sub(owner.getPosition()).len() < 64){
			testGame.setFirst(this);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int arg2, int arg3) {
		Vector2 v=game.convertMousePos(new Vector2(x, y));
		
		if(v.sub(owner.getPosition()).len() < 64){
			testGame.setSecond(this);
		}
		return false;
	}
	
	public void select(){
		city.AddComponent(new TextureRender("render", game.asset.getTexture("data/citySelected.png")));
	}
	
	public void release(){
		city.AddComponent(new TextureRender("render", game.asset.getTexture("data/city.png")));
	}
}
