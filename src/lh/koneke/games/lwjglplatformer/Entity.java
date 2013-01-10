package lh.koneke.games.lwjglplatformer;

import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.Frame;

public class Entity {
	public Quad quad;
	
	public Frame graphics;
	public AnimationManager am;
	public Vector2f currentFrame;

	public TileSlot currentTileSlot;
	public int lifetime;
	
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
	public Vector2f size; //covers several squares
	public String texturePath;
	public String thaPath;
	
	public Entity(String name) {
		this.name = name;
		am = new AnimationManager();
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
