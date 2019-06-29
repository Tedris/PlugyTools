package domain;

import java.util.List;

public class Stash extends Inventory {
	private int height;
	private int width;
	private boolean shared;
	
	public Stash() {
		super();
	}
	
	public Stash(List<Item> items) {
		super(items);
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

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}
}
