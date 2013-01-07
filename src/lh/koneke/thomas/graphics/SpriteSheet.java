package lh.koneke.thomas.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.Vector2f;

public class SpriteSheet implements DrawingObject {
	Texture2d texture;
	Map<String, List<AnimationFrame>> animations = new HashMap<String, List<AnimationFrame>>();
	
	String currentAnimation;
	List<AnimationFrame> currentFrames;
	int currentFrame;
	Vector2f frameSize;
	//int displayTime; //in ms, -1 is nonanimated
	int timer; //ms passed
	
	public boolean xflip = false;
	public boolean yflip = false;
	
	public void startAnimation(String animation) {
		currentAnimation = animation;
		currentFrames = animations.get(animation);
		currentFrame = 0;
		timer = 0;
	}
	
	public String getAnimation() {
		return currentAnimation;
	}
	
	public void addFrameToAnimation(String animation, Vector2f framePosition ,int displayTime) {
		if(!animations.containsKey(animation)) {
			animations.put(animation, new ArrayList<AnimationFrame>());
		}
		animations.get(animation).add(new AnimationFrame(framePosition, displayTime));
		if(animation == currentAnimation) { startAnimation(currentAnimation); }
	}
	
	public SpriteSheet(Texture2d texture, Vector2f frameSize/*, int displayTime*/) {
		this.texture = texture;
		this.frameSize = new Vector2f(frameSize);
		//this.displayTime = displayTime;
	}
	
	public void setTexture(Texture2d texture) {
		this.texture = texture;
	}
	
	public Rectangle getTexCoords() {
		Vector2f v = new Vector2f(
				currentFrames.get(currentFrame).getFrame().x,
				currentFrames.get(currentFrame).getFrame().y);
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
	
	public void Update(int dt) {
		if (currentFrames.get(currentFrame).getDisplayTime() == -1) return;
		timer += dt;
		while (timer > currentFrames.get(currentFrame).getDisplayTime()) {
			timer -= currentFrames.get(currentFrame).getDisplayTime();
			advance();
		}
	}
	
	void advance() {
		currentFrame = (currentFrame + 1) % currentFrames.size();
	}

	public Texture2d getTexture() {
		return texture;
	}
}
