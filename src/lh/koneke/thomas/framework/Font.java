package lh.koneke.thomas.framework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import lh.koneke.thomas.graphics.Frame;

public class Font {
	public int characterWidth;
	public int characterHeight;
	public int margin;
	int spaceSize;
	String path;
	public Frame sheet;
	public Map<Character, Integer> specialWidth = new HashMap<Character, Integer>();
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public void load(String path) {
		try {
			InputStream fis = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			
			this.setPath(br.readLine());
			
			char[] file = br.readLine().toCharArray();
			int ptr = 0;
			this.characterWidth = (file[ptr]-48)*10+(file[ptr+1]-48); ptr+=2;
			this.characterHeight = (file[ptr]-48)*10+(file[ptr+1]-48); ptr+=2;
			this.margin = (file[ptr]-48); ptr+=1;
	
			while(ptr < file.length) {
				this.specialWidth.put((char)file[ptr], file[ptr+1]-48);
				ptr += 2; 
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			Game.die();
		}
		catch (IOException e) {
			e.printStackTrace();
			Game.die();
		}
	}
}
