package com.celoron.engine;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
	Map<String, Texture> textures;
	
	public AssetManager(){
		textures=new HashMap<String, Texture>();
	}
	
	/*
	 * This method return texture of given file path,
	 * if texture has been loaded, return texture
	 * else first load texture then return it
	 * */
	public Texture getTexture(String file){
		if(textures.containsKey(file)){
			return textures.get(file);
		}
		else{
			Texture t=new Texture(file);
			textures.put(file, t);
			return t;
		}
	}
}
