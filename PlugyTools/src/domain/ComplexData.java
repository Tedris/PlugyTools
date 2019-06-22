package domain;

public class ComplexData {
	
	private String itemId;
	private int itemLevel;
	private String itemQualityString;

	public ComplexData(String itemId, int itemLevel, String itemQualityString) {
		super();
		this.itemId = itemId;
		this.itemLevel = itemLevel;
		this.itemQualityString = itemQualityString;
	}

	public ComplexData() {
		// TODO Auto-generated constructor stub
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public String getItemQualityString() {
		return itemQualityString;
	}

	public void setItemQualityString(String itemQualityString) {
		this.itemQualityString = itemQualityString;
	}
}
