package lh.koneke.thomas.gui;

import lh.koneke.thomas.framework.Quad;
import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.graphics.DrawQuadCall;
import lh.koneke.thomas.graphics.DrawingObject;

public class Radiobox extends GuiBase {
	private RadioController parent;
	boolean checked = false;
	DrawingObject ticked;
	
	public Radiobox(Rectangle r) {
		shape = new Rectangle(r);
	}
	
	public DrawingObject getTicked() {
		return ticked;
	}
	
	public void setTicked(DrawingObject d) {
		this.ticked = d;
	}
	
	public Rectangle getShape() {
		return (Rectangle)shape;
	}
	
	public void set(boolean state) {
		this.checked = state;
		if(state) check();
	}
	
	public void check() {
		getParent().uncheckAll();
		this.checked = true;
	}
	
	public void uncheck() {
		this.checked = false;
	}
	
	public DrawingObject getGraphics() {
		if (checked) return ticked;
		return graphics;
	}
	
	public DrawQuadCall getCall(float scale, int depth) {
		return new DrawQuadCall(
			getGraphics(), null,
			null,
			new Quad(getShape()),
			scale, depth, null
		);
	}

	public RadioController getParent() {
		return parent;
	}

	public void setParent(RadioController parent) {
		this.parent = parent;
	}
}
