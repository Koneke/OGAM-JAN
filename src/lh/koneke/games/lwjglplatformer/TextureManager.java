package lh.koneke.games.lwjglplatformer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import lh.koneke.thomas.framework.Game;
import lh.koneke.thomas.graphics.Texture2d;

public class TextureManager {
	Map<String, Texture2d> textures;
	Map<String, Long> textureLoadTime;
	Queue<String> unloadedTextures;
	
	String interpolationMode = "none";
	
	int hotswapFrequency = 5000;
	int hotswapTimer;
	
	public TextureManager() {
		textures = new HashMap<String, Texture2d>();
		textureLoadTime = new HashMap<String, Long>();
	}
	
	public void setInterpolationMode(String mode) {
		interpolationMode = mode;
		System.out.println("Interpolation mode set to "+interpolationMode);
	}
	
	public void loadLate(String path) {
		unloadedTextures.add(path);
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
	
	Texture loadTexture(String path) {
		return loadTexture(path, "PNG");
	}
	
	Texture loadTexture(String path, String format) {
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
			
			//check the unloadedTextures stack for new to load as well
			//in case we want to load a texture in the middle of the game or something
			String s;
			while((s = unloadedTextures.poll()) != null) {
				load(s);
				
				if(Game.verboseLogging) {
					System.out.println("TextureManager : Late load performed on "+s);
				}
			}
		}
	}
}
