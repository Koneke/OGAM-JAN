package lh.koneke.games.lwjglplatformer;

import java.util.ArrayList;
import java.util.List;

import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.Frame;
import lh.koneke.thomas.graphics.Texture2d;

public class Screen {
	TileSlot[][] map;
	Vector2f screenSize;
	Vector2f mapSize;
	Texture2d levelBackground;
	private List<TileSlot> activeTiles; //needed?
	Vector2f tileSize;
	Frame tileSheet;
	
	public Screen(int w, int h, Frame tileSheet, Vector2f tileSize, Texture2d levelBackground) {
		this.tileSheet = tileSheet;
		this.levelBackground = levelBackground;
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
	
	public Frame getFrame() {
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
