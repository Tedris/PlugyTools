package domain;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Item> items;
	private int endIndex;
	
	public Inventory() {
		setItems(new ArrayList<>());
	}

	public Inventory(List<Item> items) {
		setItems(items);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
}
