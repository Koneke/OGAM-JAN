package lh.koneke.thomas.graphics;

import lh.koneke.thomas.framework.Vector2f;

import org.newdawn.slick.opengl.Texture;

public class Texture2d implements DrawingObject{
	Texture texture;
	public String path;
	
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
	
	public Texture2d(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
