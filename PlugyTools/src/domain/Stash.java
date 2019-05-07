package domain;

import java.util.ArrayList;
import java.util.List;

public class Stash {
	private List<Item> items;
	private int height;
	private int width;
	
	public Stash() {
		items = new ArrayList<>();
	}
	
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
