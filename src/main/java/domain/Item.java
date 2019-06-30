package domain;

public class Item {
	private String hexString;
	private byte[] itemArray;
	private boolean isIdentified;
	private boolean isSocketed;
	private boolean isEar;
	private boolean isSimple;
	private boolean isEthereal;
	private boolean isPersonalized;
	private boolean isRuneword;
	private String location;
	private int colNum;
	private int rowNum;
	private String itemType;
	private String binaryString;
	private ComplexData complexData;
	private String hexIndex;
	private int numOfItemsInSockets;
	
	public Item() {
		
	}

	public Item(String hexString, byte[] itemArray, boolean isIdentified, boolean isSocketed, boolean isEar,
			boolean isSimple, boolean isEthereal, boolean isPersonalized, boolean isRuneword, String location,
			int colNum, int rowNum, String itemType, String binaryString, ComplexData complexData, String hexIndex,
			int numOfItemsInSockets) {
		super();
		this.hexString = hexString;
		this.itemArray = itemArray;
		this.isIdentified = isIdentified;
		this.isSocketed = isSocketed;
		this.isEar = isEar;
		this.isSimple = isSimple;
		this.isEthereal = isEthereal;
		this.isPersonalized = isPersonalized;
		this.isRuneword = isRuneword;
		this.location = location;
		this.colNum = colNum;
		this.rowNum = rowNum;
		this.itemType = itemType;
		this.binaryString = binaryString;
		this.setComplexData(complexData);
		this.hexIndex = hexIndex;
		this.setNumOfItemsInSockets(numOfItemsInSockets);
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

	public boolean isIdentified() {
		return isIdentified;
	}

	public void setIdentified(boolean isIdentified) {
		this.isIdentified = isIdentified;
	}

	public boolean isSocketed() {
		return isSocketed;
	}

	public void setSocketed(boolean isSocketed) {
		this.isSocketed = isSocketed;
	}

	public boolean isEar() {
		return isEar;
	}

	public void setEar(boolean isEar) {
		this.isEar = isEar;
	}

	public boolean isSimple() {
		return isSimple;
	}

	public void setSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}

	public boolean isEthereal() {
		return isEthereal;
	}

	public void setEthereal(boolean isEthereal) {
		this.isEthereal = isEthereal;
	}

	public boolean isPersonalized() {
		return isPersonalized;
	}

	public void setPersonalized(boolean isPersonalized) {
		this.isPersonalized = isPersonalized;
	}

	public boolean isRuneword() {
		return isRuneword;
	}

	public void setRuneword(boolean isRuneword) {
		this.isRuneword = isRuneword;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getBinaryString() {
		return binaryString;
	}

	public void setBinaryString(String binaryString) {
		this.binaryString = binaryString;
	}

	public ComplexData getComplexData() {
		return complexData;
	}

	public void setComplexData(ComplexData complexData) {
		this.complexData = complexData;
	}

	public String getHexIndex() {
		return hexIndex;
	}

	public void setHexIndex(String hexIndex) {
		this.hexIndex = hexIndex;
	}

	public int getNumOfItemsInSockets() {
		return numOfItemsInSockets;
	}

	public void setNumOfItemsInSockets(int numOfItemsInSockets) {
		this.numOfItemsInSockets = numOfItemsInSockets;
	}
}
