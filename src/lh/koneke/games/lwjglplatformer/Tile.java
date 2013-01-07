package lh.koneke.games.lwjglplatformer;

import lh.koneke.thomas.framework.Vector2f;

public class Tile {
	public int depth;
	public Vector2f tile;
	public boolean xflip;
	public boolean yflip;
	
	public Tile(Vector2f tilePosition, int drawDepth) {
		tile = tilePosition;
		depth = drawDepth;
	}
	
	public Tile(Vector2f tilePosition, boolean flipx) {
		tile = tilePosition;
		depth = 0;
		this.xflip = flipx;
	}
	
	public Tile(Vector2f tilePosition, boolean flipx, boolean flipy) {
		tile = tilePosition;
		depth = 0;
		this.xflip = flipx;
		this.yflip = flipy;
	}
	
	public Tile(Vector2f tilePosition, int drawDepth, boolean flipx) {
		tile = tilePosition;
		depth = drawDepth;
		this.xflip = flipx;
	}
	
	public Tile(Vector2f tilePosition, int drawDepth, boolean flipx, boolean flipy) {
		tile = tilePosition;
		depth = drawDepth;
		this.xflip = flipx;
		this.yflip = flipy;
	}
	
	public Tile(Vector2f tilePosition) {
		tile = tilePosition;
		depth = 0; // 0 is the standard tile layer, player is drawn at -1
	}
}
