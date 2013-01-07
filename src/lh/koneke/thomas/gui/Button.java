package lh.koneke.thomas.gui;

import lh.koneke.thomas.framework.Rectangle;

public class Button extends GuiBase {
	public Button(Rectangle r) {
		shape = new Rectangle(r);
	}
	
	public Rectangle getShape() {
		return (Rectangle)shape;
	}
}
