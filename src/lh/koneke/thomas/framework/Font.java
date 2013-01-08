package lh.koneke.thomas.framework;

import java.util.HashMap;
import java.util.Map;

import lh.koneke.thomas.graphics.SpriteSheet;

public class Font {
	public int characterWidth;
	public int characterHeight;
	public int margin;
	int spaceSize;
	String path;
	public SpriteSheet sheet;
	public Map<Character, Integer> specialWidth = new HashMap<Character, Integer>();
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
