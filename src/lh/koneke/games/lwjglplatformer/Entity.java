package lh.koneke.games.lwjglplatformer;

import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.Frame;
import lh.koneke.thomas.graphics.SpriteSheet;

public class Entity {
	public Quad quad;
	
	public SpriteSheet spriteSheet;
	public AnimationManager am;
	public Vector2f currentFrame;

	public TileSlot currentTileSlot;
	public int lifetime;
	
	public Frame nameTexture;
	
	/*
	 * name
	 * x,y,z
	 * look
	 * size
	 */

	String name;
	public Vector2f logicalPosition; //position in grid
	public int depth;
	private String look;
	public Vector2f size; //covers several squares?
	
	
	public Entity(String name) {
		this.name = name;
		am = new AnimationManager();
	}
	
	public String lookAt() {
		return getLook();
	}

	public String getName() {
		return name;
	}

	public String getLook() {
		return look;
	}

	public void setLook(String look) {
		this.look = look;
	}
}
