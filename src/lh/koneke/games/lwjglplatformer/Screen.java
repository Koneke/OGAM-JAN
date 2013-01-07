package lh.koneke.games.lwjglplatformer;

import java.util.ArrayList;
import java.util.List;

import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.SpriteSheet;
import lh.koneke.thomas.graphics.Texture2d;

public class Screen {
	TileSlot[][] map;
	Vector2f screenSize;
	Vector2f mapSize;
	Texture2d levelBackground;
	List<TileSlot> activeTiles; //needed?
	Vector2f tileSize;
	SpriteSheet tileSheet;
	
	public Screen(int w, int h, SpriteSheet tileSheet, Vector2f tileSize, Texture2d levelBackground) {
		this.tileSheet = tileSheet;
		this.levelBackground = levelBackground;
		this.tileSize = tileSize;
		
		mapSize = new Vector2f(w, h);
		screenSize = mapSize.scale(tileSize.x, tileSize.y);
		map = new TileSlot[w][h];
		activeTiles = new ArrayList<TileSlot>();
		
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[x].length; y++) {
				map[x][y] = new TileSlot();
				map[x][y].position = new Vector2f(x, y);
				map[x][y].setSpriteSheet(tileSheet);
			}
		}
	}
}
