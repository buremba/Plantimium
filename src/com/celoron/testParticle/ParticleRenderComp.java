package com.celoron.testParticle;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.celoron.engine.core.RenderComponent;

public class ParticleRenderComp extends RenderComponent{
	ParticleEffect pe;
	boolean removeAfter;
	
	public ParticleRenderComp(String partcileFile, String textureFolder, boolean auotoStart, boolean removeAfter){
		pe= new ParticleEffect();
		pe.load(Gdx.files.getFileHandle(partcileFile, FileType.Internal), Gdx.files.getFileHandle(textureFolder, FileType.Internal));
		
		if(auotoStart) pe.start();
		
		this.removeAfter=removeAfter;
	}
	
	public ParticleRenderComp(String partcileFile, String textureFolder, boolean start){
		this(partcileFile,textureFolder, start, true);
	}
	
	public ParticleRenderComp(String partcileFile, String textureFolder){
		this(partcileFile,textureFolder, true, true);
	}
	
	
	@Override
	public void render() {
		pe.setPosition(owner.getPosition().x, owner.getPosition().y);
		pe.draw(game.batch, game.deltaTime);
		
		if(removeAfter && pe.isComplete()){
			pe.dispose();
			game.scene.removeEntity(owner);
			return;
		}
	}

	@Override
	public void remove() {
		
	}

	@Override
	public void start() {
		
	}
	
	public ParticleEffect getParticle(){
		return pe;
	}
}
