package lh.koneke.thomas.graphics;

import lh.koneke.thomas.framework.geometry.Vector2f;

public class AnimationFrame {
	private Vector2f frame;
	private int displayTime;
	private String sound;
	
	public AnimationFrame(Vector2f frame, int displayTime, String sound) {
		this.frame = frame;
		this.displayTime = displayTime;
		this.sound = sound;
	}
	
	public Vector2f getFrame() {
		return frame;
	}

	public int getDisplayTime() {
		return displayTime;
	}
	
	public String getSound() {
		return sound;
	}
}
