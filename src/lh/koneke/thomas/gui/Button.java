package lh.koneke.thomas.gui;

import lh.koneke.thomas.framework.Rectangle;
import lh.koneke.thomas.graphics.DrawingObject;

public class Button extends GuiBase {
	public Button(Rectangle r) {
		shape = new Rectangle(r);
	}
	
	public Button(Rectangle r, DrawingObject d) {
		shape = new Rectangle(r);
		this.graphics = d;
	}
	
	public Rectangle getShape() {
		return (Rectangle)shape;
	}
}
