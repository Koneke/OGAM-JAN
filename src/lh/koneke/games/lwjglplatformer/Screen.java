package lh.koneke.games.lwjglplatformer;

import java.util.ArrayList;
import java.util.List;

import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.DrawingObject;
import lh.koneke.thomas.graphics.Frame;

public class Screen {
	TileSlot[][] map;
	Vector2f screenSize;
	Vector2f mapSize;
	private List<TileSlot> activeTiles; //needed?
	Vector2f tileSize;
	DrawingObject tileSheet;
	Frame background;
	
	public Screen(int w, int h, DrawingObject tileSheet, Vector2f tileSize) {
		this.tileSheet = tileSheet;
		this.tileSize = tileSize;
		
		mapSize = new Vector2f(w, h);
		screenSize = mapSize.scale(tileSize.x, tileSize.y);
		map = new TileSlot[w][h];
		setActiveTiles(new ArrayList<TileSlot>());
		
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[x].length; y++) {
				map[x][y] = new TileSlot();
				map[x][y].position = new Vector2f(x, y);
				map[x][y].parent = this;
			}
		}
	}
	
	public void setBackground(Frame f) {
		this.background = f;
	}
	
	public Frame getBackground() {
		return background;
	}
	
	public DrawingObject getFrame() {
		return tileSheet;
	}
	
	public TileSlot getAt(Vector2f position) {
		return map[position.intx()][position.inty()];
	}
	
	public Vector2f getTileSize() {
		return tileSize;
	}

	public List<TileSlot> getActiveTiles() {
		return activeTiles;
	}

	public void setActiveTiles(List<TileSlot> activeTiles) {
		this.activeTiles = activeTiles;
	}
}
