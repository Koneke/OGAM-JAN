package lh.koneke.games.lwjglplatformer;

import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.graphics.Colour;
import lh.koneke.thomas.graphics.DrawingObject;

public class DrawQuadCall {
	DrawingObject d;
	Rectangle source;
	Quad q;
	float scale;
	int depth;
	Colour c;
	AnimationManager am;
	
	public DrawQuadCall(DrawingObject d, AnimationManager am, Rectangle source, Quad q, float scale, int depth, Colour c) {
		this.d = d;
		this.source = source;
		this.q = q;
		this.scale = scale;
		this.depth = depth;
		this.c = c;
		this.am = am;
	}
}
