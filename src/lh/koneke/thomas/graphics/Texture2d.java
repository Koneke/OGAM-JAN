package lh.koneke.thomas.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lh.koneke.thomas.framework.Vector2f;

import org.newdawn.slick.opengl.Texture;

public class Texture2d implements DrawingObject{
	private static Map<String, Texture> loadedTextures = new HashMap<String, Texture>();
	public static List<TextureInformation> information = new ArrayList<TextureInformation>();
	Texture texture;
	public String path;
	public long hotswapChanged;
	
	public void Bind() {
		texture.bind();
	}
	
	public void Release() {
		texture.release();
	}
	
	public int getWidth() {
		return (int)texture.getTextureWidth();
	}
	
	public int getHeight() {
		return (int)texture.getTextureHeight();
	}
	
	public Vector2f getSize() {
		return new Vector2f(getWidth(), getHeight());
	}
	
	public Texture2d(Texture texture, String path) {
		this.texture = texture;
		this.path = path;
		this.hotswapChanged = getLoaded(path).lastModified;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public void checkHotswap() {
		if (this.hotswapChanged != getLoaded(path).lastModified) {
			this.texture = getLoaded(path).texture;
		}
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public static Map<String, Texture> getLoadedTextures() {
		return loadedTextures;
	}
	
	public static boolean loaded(String path, Texture texture) {
		for(TextureInformation ti : information) {
			if (ti.path == path) {
				return false; //was already loaded
			}
		}
		information.add(new TextureInformation(path, texture));
		return true;/*
		if(loadedTextures.containsValue(texture)) return false;
		loadedTextures.put(path, texture);
		return true;*/
	}
	
	public static TextureInformation getLoaded(String path) {
		for(TextureInformation ti : information) {
			if (ti.path == path) {
				return ti;
			}
		}
		//if(loadedTextures.containsKey(path)) return loadedTextures.get(path);
		return null;
	}
}
