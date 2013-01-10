package lh.koneke.thomas.gui;

import java.util.ArrayList;
import java.util.List;

public class RadioController extends GuiBase {
	private List<Radiobox> boxes;
	
	public RadioController() {
		this.setBoxes(new ArrayList<Radiobox>());
	}
	
	public void uncheckAll() {
		for(Radiobox r : getBoxes()) {
			r.uncheck();
		}
	}
	
	public void add(Radiobox r) {
		getBoxes().add(r);
	}

	public List<Radiobox> getBoxes() {
		return boxes;
	}

	public void setBoxes(List<Radiobox> boxes) {
		this.boxes = boxes;
	}
}
