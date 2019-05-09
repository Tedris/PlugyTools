package domain;

public class Item {
	private String hexString;
	private byte[] itemArray;
	
	public Item() {
		
	}

	public Item(String hexString, byte[] itemArray) {
		this.hexString = hexString;
		this.itemArray = itemArray;
	}

	public String getHexString() {
		return hexString;
	}

	public void setHexString(String hexString) {
		this.hexString = hexString;
	}

	public byte[] getItemArray() {
		return itemArray;
	}

	public void setItemArray(byte[] itemArray) {
		this.itemArray = itemArray;
	}
}
