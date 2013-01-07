package lh.koneke.thomas.graphics;

import lh.koneke.thomas.framework.Vector2f;

public class Frame implements DrawingObject {
	public Vector2f frame;
	public SpriteSheet sheet;
	
	public Frame(Vector2f frame, SpriteSheet sheet) {
		this.frame = frame;
		this.sheet = sheet;
	}
}
