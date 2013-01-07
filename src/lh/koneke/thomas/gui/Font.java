package lh.koneke.thomas.gui;

import java.util.HashMap;
import java.util.Map;

import lh.koneke.thomas.graphics.SpriteSheet;

public class Font {
	public int characterWidth;
	public int characterHeight;
	public int margin;
	int spaceSize;
	public SpriteSheet sheet;
	public Map<Character, Integer> specialWidth = new HashMap<Character, Integer>();
}
