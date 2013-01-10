package lh.koneke.thomas.framework;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundEffect {
	private Audio file;
	
	public SoundEffect(Audio file) {
		this.file = file;
	}
	
	public void play() {
		file.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void play(float volume, float panning) {
		file.playAsSoundEffect(volume, panning, false);
	}

	public static Audio load(String path) {
		try {
			String[] splitpath = path.split("\\.");
			String format = splitpath[splitpath.length-1];
			return AudioLoader.getAudio(format.toUpperCase(), ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
