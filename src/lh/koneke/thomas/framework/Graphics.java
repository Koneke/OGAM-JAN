package lh.koneke.thomas.framework;

import java.io.IOException;

import lh.koneke.thomas.graphics.Texture2d;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Graphics {
	static String interpolationMode = "";
	public static float scale;

	public static void setInterpolationMode(String mode) {
		interpolationMode = mode;
		System.out.println("Interpolation mode set to "+interpolationMode);
	}
	
	public static Texture loadTexture(String path) {
		return loadTexture(path, "PNG", false);
	}
	
	public static Texture loadTexture(String path, boolean hotswap) {
		return loadTexture(path, "PNG", hotswap);
	}
	
	public static Texture loadTexture(String path, String format, boolean hotswap) {
		try {
			Texture t = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(path));
			if(interpolationMode == "none") {
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			}
			
			if(!hotswap) {
				if (Texture2d.loaded(path, t)) { //if the texture wasn't loaded already, return it, else, null
					return t;
				}
				else { 
					return null;
				}
			} else {
				return t;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
