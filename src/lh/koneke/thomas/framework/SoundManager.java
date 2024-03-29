package lh.koneke.thomas.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SoundManager {
	Map<String, SoundEffect> sounds = new HashMap<String, SoundEffect>();
	
	public void load(String path) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));
			String s;
			while((s = br.readLine()) != null) {
				String[] split = s.split("\\.");
				split = split[0].split("\\/");
				String name = split[split.length-1];
				sounds.put(name, new SoundEffect(SoundEffect.load(s)));
				System.out.println("Loaded snd "+name);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Game.die();
		}
	}
	
	public void play(String sound) {
		if(sounds.containsKey(sound)) {
		sounds.get(sound).play();}
		else {
			System.out.println("BAD SOUND");
		}
	}

	public void play(String sound, float pitch, float gain) {
		sounds.get(sound).play(pitch, gain);
	}
	
	public void play(String sound, float pitch, float gain, boolean loop) {
		sounds.get(sound).play(pitch, gain, loop);
	}
	
	public void stop(String sound) {
		sounds.get(sound).stop();
	}
	
}
