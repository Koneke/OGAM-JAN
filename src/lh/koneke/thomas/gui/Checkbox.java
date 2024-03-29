package lh.koneke.thomas.gui;

import lh.koneke.thomas.framework.geometry.Quad;
import lh.koneke.thomas.framework.geometry.Rectangle;
import lh.koneke.thomas.graphics.DrawQuadCall;
import lh.koneke.thomas.graphics.DrawingObject;

public class Checkbox extends GuiBase {
	boolean checked = false;
	DrawingObject ticked;
	
	public Checkbox(Rectangle r) {
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
	}
	
	public void toggle() {
		this.checked = !this.checked;
	}
	
	public void check() {
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
			/*scale,*/ depth, null
		);
	}
}
