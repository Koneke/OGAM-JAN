package lh.koneke.thomas.graphics;

import lh.koneke.games.lwjglplatformer.AnimationManager;
import lh.koneke.thomas.framework.Rectangle;

public interface FrameGenerator {
	public Rectangle getTexCoord(Texture2d texture, AnimationManager am);
	public Texture2d getTexture();
	public void setTexture(Texture2d texture);
	public boolean getxflip();
	public boolean getyflip();
	public void setxflip(boolean b);
	public void setyflip(boolean b);
}
