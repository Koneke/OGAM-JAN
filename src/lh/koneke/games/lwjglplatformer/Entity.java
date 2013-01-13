package lh.koneke.games.lwjglplatformer;

import lh.koneke.thomas.framework.AnimationManager;
import lh.koneke.thomas.framework.Game;
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
	
	public Entity(Entity e) {
		if(e == null) {
			Game.die(); }
		
		this.quad = e.quad;
		this.graphics = e.graphics;
		this.am = e.am;
		this.currentFrame = e.currentFrame;
		this.currentTileSlot = e.currentTileSlot;
		this.lifetime = e.lifetime;
		this.name = e.getName();
		this.logicalPosition = e.logicalPosition;
		this.depth = e.depth;
		this.size = e.size;
		this.texturePath = e.texturePath;
		this.thaPath = e.thaPath;
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
