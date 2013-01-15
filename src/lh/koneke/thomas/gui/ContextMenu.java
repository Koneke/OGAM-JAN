package lh.koneke.thomas.gui;

import java.util.ArrayList;
import java.util.List;

import lh.koneke.games.lwjglplatformer.TileSlot;
import lh.koneke.thomas.framework.geometry.Rectangle;
import lh.koneke.thomas.framework.geometry.Vector2f;

public class ContextMenu extends GuiBase {
	List<Button> content;
	public TileSlot tile;
	
	public ContextMenu(Vector2f position, float w) {
		content = new ArrayList<Button>();
		shape = new Rectangle(position, new Vector2f(w,0));
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		for(Button b : getItems()) {
			b.setVisible(visible);
		}
	}
	
	public Rectangle getShape() {
		return (Rectangle)shape;
	}
	
	public void addItem(Button item) {
		content.add(item);
		getShape().h += item.getShape().h;
	}
	
	public void clear() {
		content.clear();
	}
	
	public List<Button> getItems() {
		return content;
	}
}
