package lh.koneke.games.lwjglplatformer;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
	Map<String, SoundEffect> sounds = new HashMap<String, SoundEffect>();
	
	public void load(String path) {
		String[] split = path.split("\\.");
		split = split[0].split("\\/");
		String name = split[split.length-1];
		sounds.put(name, new SoundEffect(SoundEffect.load(path)));
	}
	
	public void play(String sound) {
		sounds.get(sound).play();
	}

	public void play(String sound, float volume) {
		sounds.get(sound).play(volume, 1.0f);
	}
	
}
