package lh.koneke.thomas.graphics;

import lh.koneke.games.lwjglplatformer.AnimationManager;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.Vector2f;

public class SpriteSheet implements DrawingObject {
	Texture2d texture;
	Vector2f frameSize;
	
	public boolean xflip = false;
	public boolean yflip = false;
	public SpriteSheet(Texture2d texture, Vector2f frameSize) {
		this.texture = texture;
		this.frameSize = new Vector2f(frameSize);
	}
	
	public void setTexture(Texture2d texture) {
		this.texture = texture;
	}
	
	public Rectangle getTexCoords(AnimationManager am) {
		//am can be null
		Vector2f v = new Vector2f(
				am.getCurrentFrames().get(am.getCurrentFrame()).getFrame().x,
				am.getCurrentFrames().get(am.getCurrentFrame()).getFrame().y);
		Rectangle r = (new Rectangle(v, frameSize));
		r = r.scale(1f/texture.getWidth(),1f/texture.getHeight());
		if (xflip && yflip) { return r.flip(); }
		if (xflip) { return r.xflip(); }
		if (yflip) { return r.yflip(); }
		return r;
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
	
	public Texture2d getTexture() {
		return texture;
	}
}
