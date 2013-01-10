package lh.koneke.thomas.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lh.koneke.thomas.framework.Font;
import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.framework.Vector2f;
import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.DrawQuadCall;

public class Text {
	String string;
	List<DrawQuadCall> graphics; //save drawn text in here in the future
	
	public Text(String string) {
		this.string = string;
	}
	
	public static TextDrawCalls renderString(String string, Font font, Vector2f position, float scale, int depth, Colour C) {
		TextDrawCalls tdc = new TextDrawCalls();
		List<DrawQuadCall> calls = new ArrayList<DrawQuadCall>();
		
		int w = 0;
		for(char c : string.toCharArray()) {
			if(c == ' ') {
				w += 4;
				continue;
			}
			char cc = 0;
			/* period 46
			 * comma  44
			 * apost  39
			 * exclm  33
			 * quest  63
			 */
			Map<Character, Character> exchangeMap = new HashMap<Character, Character>();
			exchangeMap.put('.', (char) 52);
			exchangeMap.put(',', (char) 53);
			exchangeMap.put('\'',(char) 54);
			exchangeMap.put('!', (char) 55);
			exchangeMap.put('?', (char) 56);
			
			if(exchangeMap.containsKey(c)) {
				cc = exchangeMap.get(c);
			}
			
			if(c>=65 && c<123) {
				cc = (char)((c-65)*2);
				//A = 0, B = 2, a = 1, b = 3
				if(c >= 97 && c < 123) cc = (char)((c-97)*2+1);
			}
			
			Vector2f charPosition = new Vector2f(cc*(font.characterWidth+font.margin)+font.margin, 0);
			Vector2f charSize = new Vector2f(
				(font.specialWidth.containsKey(c) ? font.specialWidth.get(c) : font.characterWidth),
				font.characterHeight);
			
			calls.add(new DrawQuadCall(
				font.sheet, null,
				font.sheet.getAt(
					charPosition,
					charSize),
				new Quad(new Rectangle(position.add(new Vector2f(w,0)), charSize)),
				scale,
				depth, C));
			
			w+=charSize.x+1;
		}
		tdc.size = new Vector2f(w, font.characterHeight);
		tdc.calls = calls;
		return tdc;
	}
}
