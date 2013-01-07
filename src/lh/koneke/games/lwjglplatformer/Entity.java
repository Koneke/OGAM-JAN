package lh.koneke.games.lwjglplatformer;

import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.SpriteSheet;

public class Entity {
	public Vector2f logicalPosition; //position in grid
	public Quad quad;
	
	public SpriteSheet spriteSheet;
	public Vector2f currentFrame;
	public int depth;

	public TileSlot currentTileSlot;
	public int lifetime;
	
	String look;
	String name;
	
	public Entity(String name) {
		this.name = name;
	}
	
	public String lookAt() {
		return look;
	}

	public String getName() {
		return name;
	}
}
