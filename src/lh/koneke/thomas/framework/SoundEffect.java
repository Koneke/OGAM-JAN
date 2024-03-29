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

	public void play(float pitch, float gain) {
		file.playAsSoundEffect(pitch, gain, false);
	}
	
	public void play(float pitch, float gain, boolean loop) {
		file.playAsMusic(pitch, gain, loop);
	}

	public static Audio load(String path) {
		try {
			String[] splitpath = path.split("\\.");
			String format = splitpath[splitpath.length-1];
			return AudioLoader.getAudio(format.toUpperCase(), ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			Game.die();
		} catch (RuntimeException e) {
			e.printStackTrace();
			Game.die();
		}
		return null;
	}

	public void stop() {
		file.stop();
	}
	
}
