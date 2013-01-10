package lh.koneke.thomas.graphics;

import lh.koneke.games.lwjglplatformer.AnimationManager;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.Vector2f;

public class Frame implements DrawingObject, FrameGenerator {
	public Vector2f frame;
	public Vector2f frameSize = new Vector2f(32,32);
	
	Texture2d texture;
	public String texturePath;

	public boolean xflip = false;
	public boolean yflip = false;
	
	public Frame(Vector2f frame, Vector2f frameSize) {
		this.frame = frame;
		this.frameSize = frameSize;
	}

	public Rectangle getAt(Vector2f position) {
		Rectangle r = new Rectangle(
				position,
				frameSize
		).scale(1f/texture.getWidth(), 1f/texture.getHeight());
		
		return r;
	}
	
	public Rectangle getAt(Vector2f position, Vector2f size) {
		Rectangle r = new Rectangle(
				position,
				size
		).scale(1f/texture.getWidth(), 1f/texture.getHeight());
		
		return r;
	}
	
	public Rectangle getTexCoord(Texture2d texture, AnimationManager am) {
		Vector2f v = (am == null ? frame : am.getFrame().getPosition());
		Rectangle r = (new Rectangle(v, frameSize));
		r = r.scale(1f/texture.getWidth(),1f/texture.getHeight());
		if (xflip && yflip) { return r.flip(); }
		if (xflip) { return r.xflip(); }
		if (yflip) { return r.yflip(); }
		return r;
	}

	public Texture2d getTexture() {
		return texture;
	}
	
	public void setTexture(Texture2d texture) {
		this.texture = texture;
	}

	public boolean getxflip() {
		return xflip;
	}

	public boolean getyflip() {
		return yflip;
	}
	
	public void setxflip(boolean b) {
		this.xflip = b;
	}
	
	public void setyflip(boolean b) {
		this.yflip = b;
	}
}
