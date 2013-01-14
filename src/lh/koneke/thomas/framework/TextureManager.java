package lh.koneke.thomas.framework;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import lh.koneke.thomas.graphics.Texture2d;

public class TextureManager {
	Map<String, Texture2d> textures;
	Map<String, Long> textureLoadTime;
	
	String interpolationMode = "none";
	
	int hotswapFrequency = 5000;
	int hotswapTimer = 0;
	
	public TextureManager() {
		textures = new HashMap<String, Texture2d>();
		textureLoadTime = new HashMap<String, Long>();
	}
	
	public void setInterpolationMode(String mode) {
		interpolationMode = mode;
		System.out.println("Interpolation mode set to "+interpolationMode);
	}
	
	public void setHotswapFrequency(int frequency) {
		this.hotswapFrequency = frequency;
	}

	public Texture2d load(String path) {
		//loads if not loaded, returns otherwise
		
		if(!textures.containsKey(path)) {
			Texture2d texture = new Texture2d(loadTexture(path));
			File f = new File(path);
			textures.put(path, texture);
			textureLoadTime.put(path, f.lastModified());
			
			if(Game.verboseLogging) {
				System.out.println("TextureManager : Loaded "+path+" at time "+f.lastModified()+".");
			}
			
			return texture;
		} else {
			return textures.get(path);
		}
	}
	
	public boolean get(String path) {
		return textures.containsKey(path);
	}
	
	Texture loadTexture(String path) {
		return loadTexture(path, "PNG");
	}
	
	Texture loadTexture(String path, String format) {
		if(path == null) return null;
		try {
			Texture t = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(path));
			
			if(interpolationMode == "none") {
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			}
			
			return t;
			
		} catch (IOException e) {
			e.printStackTrace();
			Game.die();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			Game.die();
			return null;
		}
	}
	
	public void updateHotswapTimer(int dt) {
		hotswapTimer += dt;
		
		if(hotswapTimer > hotswapFrequency) {
			while (hotswapTimer > hotswapFrequency) {
				hotswapTimer -= hotswapFrequency;
			}
			
			if(Game.verboseLogging) {
				System.out.println("TextureManager : Checking for hotswaps.");
			}
			
			File f;
			for(String path : textures.keySet()) {
				f = new File(path);
				if(f.lastModified() != textureLoadTime.get(path)) {
					if(Game.verboseLogging) {
						System.out.println("TextureManager : Hotswapping "+path+".");
						textures.get(path).setTexture(loadTexture(path));
						textureLoadTime.put(path, f.lastModified());
					}
				}
			}
		}
	}
}
