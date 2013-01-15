package lh.koneke.games.lwjglplatformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lh.koneke.thomas.framework.geometry.Vector2f;

/* Class representing one grid slot, holding the tiles to be drawn there. */

public class TileSlot {
	public List<Entity> entities;
	public List<TileProperty> properties;
	private List<Tile> tiles; //the graphic tiles to be drawn on this tile
	public Vector2f position;
	Screen parent;
	
	public TileSlot() {
		entities = new ArrayList<Entity>();
		properties = new ArrayList<TileProperty>();
		tiles = new ArrayList<Tile>();
	}
	
	public void addTile(Tile t) {
		tiles.add(t);
		sortTiles();
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	void sortTiles() {
		Collections.sort(tiles, new Comparator<Tile>() {
			public int compare(Tile a, Tile b) {
				if (a.depth < b.depth) return -1;
				if (a.depth > b.depth) return 1;
				return 0;
			}
		});
		Collections.reverse(tiles);
	}
}
