package domain;

public class ComplexData {
	
	private String itemId;
	private int itemLevel;
	private String itemQualityString;
	private int fileId;
	private String itemName;

	public ComplexData(String itemId, int itemLevel, String itemQualityString, int fileId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemLevel = itemLevel;
		this.itemQualityString = itemQualityString;
		this.fileId = fileId;
		this.setItemName(itemName);
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

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
