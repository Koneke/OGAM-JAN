package lh.koneke.thomas.framework;

import org.lwjgl.Sys;

public class Time {

	private long lastFrame; //what time we drew the last frame at
	public long getTime() { return (Sys.getTime() * 1000) / Sys.getTimerResolution(); } //sys time in ms
	public int getDT() { long t = getTime(); int dt = (int)(t-lastFrame); lastFrame=t; return dt; } //returns time in ms since last frame
}
