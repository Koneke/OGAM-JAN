package lh.koneke.thomas.graphics;

import lh.koneke.thomas.framework.AnimationManager;
import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;

public class DrawQuadCall {
	private DrawingObject graphics;
	private Rectangle source;
	private Quad quad;
	//private float scale;
	private int depth;
	private Colour colour;
	private AnimationManager am;
	
	public DrawQuadCall(DrawingObject d, AnimationManager am, Rectangle source, Quad q, /*float scale,*/ int depth, Colour c) {
		this.setGraphics(d);
		this.setSource(source);
		this.setQuad(q);
		//this.setScale(scale);
		this.setDepth(depth);
		this.setColour(c);
		this.setAm(am);
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour c) {
		this.colour = c;
	}

	public Rectangle getSource() {
		return source;
	}

	public void setSource(Rectangle source) {
		this.source = source;
	}
/*
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}*/

	public Quad getQuad() {
		return quad;
	}

	public void setQuad(Quad q) {
		this.quad = q;
	}

	public AnimationManager getAm() {
		return am;
	}

	public void setAm(AnimationManager am) {
		this.am = am;
	}

	public DrawingObject getGraphics() {
		return graphics;
	}

	public void setGraphics(DrawingObject d) {
		this.graphics = d;
	}
}
