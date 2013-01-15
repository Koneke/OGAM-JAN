package lh.koneke.thomas.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


import lh.koneke.thomas.framework.Font;
import lh.koneke.thomas.framework.geometry.Quad;
import lh.koneke.thomas.framework.geometry.Rectangle;
import lh.koneke.thomas.framework.geometry.Vector2f;
import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.DrawQuadCall;

public class Text {
	static Queue<String> renderedStrings
		= new LinkedList<String>();
	static Map<String, TextDrawCalls> renderedStringsMap
		= new HashMap<String, TextDrawCalls>();;
	
	String string;
	List<DrawQuadCall> graphics; //save drawn text in here in the future
	
	public Text(String string) {
		this.string = string;
	}
	
	public static TextDrawCalls renderString(String string, Font font, Vector2f position,/* float scale,*/ int depth, Colour C) {
		
		if(renderedStrings.contains(string)) {
			renderedStrings.remove(string);
			renderedStrings.add(string); //move it back
			//return renderedStringsMap.get(string);
			//doesn't work, why?
		}
		
		TextDrawCalls tdc = new TextDrawCalls();
		List<DrawQuadCall> calls = new ArrayList<DrawQuadCall>();
		
		Colour currentDrawingColour = C;
		boolean colourSetup = false;
		int setup = 0;
		
		int w = 0;
		for(char c : string.toCharArray()) {
			if(c == '@') {
				colourSetup = true;
				setup = 0;
				continue;
			}
			if(colourSetup) {
				if((c >= 48 && c <=57) || (c >= 97 && c <= 102) || (c >= 65 && c <= 70)) {
					int value;
					if(c >= 97) {
						value = c - 87;
					} else if (c >= 65) {
						value = c - 55;
					}
					else { value = c - 48; }
					
					switch(setup) {
					case 0:
						currentDrawingColour.setRed(value/15f);
						break;
					case 1:
						currentDrawingColour.setGreen(value/15f);
						break;
					case 2:
						currentDrawingColour.setBlue(value/15f);
						break;
					case 3:
						currentDrawingColour.setAlpha(value/15f);
						colourSetup = false;
						break;
					}

					setup += 1;
				}
				else {
					System.exit(0);
					//bad read, die
				}
				continue;
			}
			
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
			 * slash  47
			 */
			Map<Character, Character> exchangeMap = new HashMap<Character, Character>();
			exchangeMap.put('.', (char) 52);
			exchangeMap.put(',', (char) 53);
			exchangeMap.put('\'',(char) 54);
			exchangeMap.put('!', (char) 55);
			exchangeMap.put('?', (char) 56);
			exchangeMap.put('/', (char) 57);
			
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
				/*scale,*/
				depth, new Colour(currentDrawingColour)));
			
			w+=charSize.x+1;
		}
		tdc.size = new Vector2f(w, font.characterHeight);
		tdc.calls = calls;

		renderedStrings.add(string);
		renderedStringsMap.put(string, tdc);
		
		if(renderedStrings.size() >= 20) {
			String s = renderedStrings.poll();
			renderedStringsMap.remove(s); //remove eldest
		}
		
		return tdc;
	}
}
