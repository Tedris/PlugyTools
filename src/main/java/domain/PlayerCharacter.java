package domain;

public class PlayerCharacter {
	private String fileName;
	private Inventory characterItems;
	private Inventory mercenaryItems;
	private Inventory corpseItems;
	
	public PlayerCharacter() {
		
	}

	public Inventory getCharacterItems() {
		return characterItems;
	}

	public void setCharacterItems(Inventory characterItems) {
		this.characterItems = characterItems;
	}

	public Inventory getMercenaryItems() {
		return mercenaryItems;
	}

	public void setMercenaryItems(Inventory mercenaryItems) {
		this.mercenaryItems = mercenaryItems;
	}

	public Inventory getCorpseItems() {
		return corpseItems;
	}

	public void setCorpseItems(Inventory corpseItems) {
		this.corpseItems = corpseItems;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
