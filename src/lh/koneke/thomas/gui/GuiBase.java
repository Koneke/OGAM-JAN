package lh.koneke.thomas.gui;

import lh.koneke.thomas.framework.Shape;
import lh.koneke.thomas.graphics.DrawingObject;

public class GuiBase {
	Shape shape;
	DrawingObject graphics;
	boolean visible;
	
	public Shape getShape() {
		return shape;
	}

	public DrawingObject getGraphics() {
		return graphics;
	}
	
	public void setGraphics(DrawingObject d) {
		graphics = d;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
