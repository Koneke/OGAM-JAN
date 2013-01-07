package lh.koneke.thomas.framework;

import java.io.File;

import org.newdawn.slick.opengl.Texture;

public class TextureInformation {
	public String path;
	public Texture texture;
	public long lastModified;
	
	public TextureInformation(String path, Texture texture) {
		this.path = path;
		this.texture = texture;
		File f = new File(path);
		lastModified = f.lastModified();
		System.out.println("ti "+path);
	}
	
	public void checkHotswap() {
		File f = new File(path);
		if (lastModified != f.lastModified()) {
			System.out.println("Hotswap wanted for "+path);
			texture.release();
			texture = Graphics.loadTexture(path, true);
			this.lastModified = f.lastModified();
			System.out.println("Loaded "+path+", last modified "+this.lastModified);
		}
	}
}
