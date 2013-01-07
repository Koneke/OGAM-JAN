package lh.koneke.thomas.graphics;

import lh.koneke.thomas.framework.Vector2f;

public class AnimationFrame {
	public Vector2f frame;
	public int displayTime;
	
	public AnimationFrame(Vector2f frame, int displayTime) {
		this.frame = frame;
		this.displayTime = displayTime;
	}
	
	public Vector2f getFrame() {
		return frame;
	}

	public int getDisplayTime() {
		return displayTime;
	}
}
