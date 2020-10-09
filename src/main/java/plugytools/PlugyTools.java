package plugytools;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlugyTools {
		
	public static void main(String ... args) throws IOException {
		//do basic stuff, try to read from sss file
		
		String saveDirectory = getSaveDirectoryFromProperties();
		System.out.println("Save Directory: " + saveDirectory);
		System.out.println("1. Scan Save Directory");
		System.out.println("2. Change Save Directory");

		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		if (input != null && input.contains("1")) {
			scanSaveDirectory(saveDirectory);
		}

		if (input != null && input.contains("2")) {
			createSaveProperties();
		}
	}

	private static void scanSaveDirectory(String saveDirectory) throws IOException {
		Library library = new Library();
		List<StashCollection> stashes = getStashesFromDirectory(saveDirectory);
		List<PlayerCharacter> playerCharacters = getCharactersFromDirectory(saveDirectory);
		library.setStashes(stashes);
		library.setPlayerCharacters(playerCharacters);

		String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

		try (FileWriter file = new FileWriter("stashdebug-"+timestamp+".json" )) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(file, library);
		}

		System.out.println("Stashes read from file: " + stashes.size());

		//Read from Holy Grail text files, write what you have, write what you need
		try (FileWriter file = new FileWriter("holygrail-"+timestamp+".txt")) {
			List<String> uniqueConstants = UniqueConstants.getInstance().getUniqueConstants();
			List<String> setConstants = SetConstants.getInstance().getSetConstants();

			List<String> foundUniques = new ArrayList<>();
			List<String> neededUniques = new ArrayList<>();

			List<String> foundSets = new ArrayList<>();
			List<String> neededSets = new ArrayList<>();

			for (String uniqueConstant : uniqueConstants) {
				if (isUniqueInLibrary(uniqueConstant, library)) {
					foundUniques.add(uniqueConstant);
				} else {
					neededUniques.add(uniqueConstant);
				}
			}

			for (String setConstant : setConstants) {
				if (isSetInStash(setConstant, stashes)) {
					foundSets.add(setConstant);
				} else {
					neededSets.add(setConstant);
				}
			}

			file.write("Uniques you have: \n");
			for (String foundUnique : foundUniques) {
				file.write(foundUnique + "\n");
			}

			file.write("\n");

			file.write("Uniques you need: \n");
			for (String neededUnique : neededUniques) {
				file.write(neededUnique + "\n");
			}

			file.write("\n");

			file.write("Sets you have: \n");
			for (String foundSet : foundSets) {
				file.write(foundSet + "\n");
			}

			file.write("\n");

			file.write("Sets you need: \n");
			for (String neededSet : neededSets) {
				file.write(neededSet + "\n");
			}
		}
	}

	private static List<StashCollection> getStashesFromDirectory(String saveDirectory) {
		List<StashCollection> stashCollections = new ArrayList<>();
		StashCollection sharedStashCollection = new StashCollection();
		List<Stash> sharedStash = getStashesFromFile(saveDirectory + "/_LOD_SharedStashSave.sss");
		sharedStashCollection.setStashes(sharedStash);
		sharedStashCollection.setFileName("_LOD_SharedStashSave.sss");
		stashCollections.add(sharedStashCollection);
		File directory = new File(saveDirectory);
		File[] files = directory.listFiles((d, name) -> name.endsWith(".d2x"));
		if (files != null) {
            for (File stash : files) {
                System.out.println("Parsing " + stash.getName());
                List<Stash> personalStash = getStashesFromFile(stash.getAbsolutePath());
                StashCollection personalStashCollection = new StashCollection();
                personalStashCollection.setStashes(personalStash);
                personalStashCollection.setFileName(stash.getName());
                stashCollections.add(personalStashCollection);
            }
        }
		
		return stashCollections;
	}

	private static List<PlayerCharacter> getCharactersFromDirectory(String saveDirectory) {
		List<PlayerCharacter> playerCharacters = new ArrayList<>();
		File directory = new File(saveDirectory);
		File[] files = directory.listFiles((d, name) -> name.endsWith(".d2s"));
		if (files != null) {
			for (File character : files) {
				System.out.println("Parsing " + character.getName());
				PlayerCharacter playerCharacter = getCharacterData(character);
				playerCharacter.setFileName(character.getName());
				playerCharacters.add(playerCharacter);
			}
		}
		return playerCharacters;
	}

	public static PlayerCharacter getCharacterData(File character) {
		PlayerCharacter playerCharacter = new PlayerCharacter();
		try {
			byte[] data = Files.readAllBytes(character.toPath());
			playerCharacter = getD2sItems(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return playerCharacter;
	}

	private static PlayerCharacter getD2sItems(byte[] data) {
		PlayerCharacter playerCharacter = new PlayerCharacter();
		Inventory characterInventory = parseInventoryFromData(data, 0, "character");
		Inventory corpseInventory = parseInventoryFromData(data, characterInventory.getEndIndex(), "corpse");
		Inventory mercenaryInventory = parseInventoryFromData(data, corpseInventory.getEndIndex(), "mercenary");
		playerCharacter.setCharacterItems(characterInventory);
		playerCharacter.setCorpseItems(corpseInventory);
		playerCharacter.setMercenaryItems(mercenaryInventory);
		return playerCharacter;
	}
	
	private static Inventory parseInventoryFromData(byte[] data, int currentIndex, String inventoryType) {
		Inventory inventory = new Inventory();
		//character data starts with a JM
		int firstJMHeader = getStartIndexOfNextHeader(Constants.JM, data, currentIndex);
		//number of items should only be needed once
		int nbItem = data[firstJMHeader+2];
		currentIndex = firstJMHeader+2;

		System.out.println("Number of Items in " + inventoryType + ": " + nbItem);
		for (int currentItemNum = 0; currentItemNum < nbItem; currentItemNum++) {
			int currentJmItemHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, currentIndex);
			String hexIndex = getHexStringFromInt(currentJmItemHeaderIndex);
			System.out.println("Current JM Item Header Index: " + currentJmItemHeaderIndex + "; Hex: " + hexIndex);
			int nextJmItemHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, currentJmItemHeaderIndex + 2);
			String itemHex;
			byte[] itemArray;
			if (nextJmItemHeaderIndex == -1) {
				//End of File!
				itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex, data.length);
				itemArray = Arrays.copyOfRange(data, currentJmItemHeaderIndex, data.length-1);
			} else {
				itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex, nextJmItemHeaderIndex);
				itemArray = Arrays.copyOfRange(data, currentJmItemHeaderIndex, nextJmItemHeaderIndex);
				currentIndex = nextJmItemHeaderIndex;
			}
			Item item = getItemFromBinaryString(itemHex, itemArray, hexIndex);
			if (item.getLocation() == null) {
				nbItem++;
			}
			if (item.getNumOfItemsInSockets() > 0 && !item.isSimple()) {
				nbItem += item.getNumOfItemsInSockets();
			}
			inventory.getItems().add(item);
		}
		inventory.setEndIndex(currentIndex);
		return inventory;
	}

	private static List<Stash> getStashesFromFile(String filePath) {
		Path fileLocation = Paths.get(filePath);
		
		try {
			byte[] data = Files.readAllBytes(fileLocation);
			if (filePath.contains(".sss")) {
				//check for valid SSS file:
				
				String SSS = getHexStringFromRange(data, 0, 3);
				int nbStash = 0;
				int startStashIndex = 0;

				if (SSS.equalsIgnoreCase("535353")) {
					String fileVersion = getHexStringFromRange(data, 4, 6);
					if (fileVersion.equalsIgnoreCase("3031")) {
						//file is version 01, no shared gold
						nbStash = Integer.parseInt(reverseHex(getHexStringFromRange(data, 6, 10)), 16);
						startStashIndex = 10;
					} else if (fileVersion.equalsIgnoreCase("3032")) {
						//file is version 02, has shared gold
						int sharedGoldAmount = data[6];

						System.out.println("Shared Gold Amount: " + sharedGoldAmount);

						nbStash = Integer.parseInt(reverseHex(getHexStringFromRange(data, 10, 14)), 16);
						startStashIndex = 14;
					}
					System.out.println("Number of stashes: " + nbStash);
					
					return getStashesFromData(data, nbStash, startStashIndex);
				}
			} else if (filePath.contains(".d2x")) {
				int nbStash = Integer.parseInt(reverseHex(getHexStringFromRange(data, 10, 14)), 16);
				int startStashIndex = 14;
				
				System.out.println("Number of stashes: " + nbStash);
				
				return getStashesFromData(data, nbStash, startStashIndex);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static String reverseHex(String hex) {
		if (hex.length() % 2 != 0) {
			System.err.println("Hex strings to be reversed should always be of even length");
			return null; // TODO
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			int start = hex.length() - i;
			sb.append(hex.substring(start - 2, start));
		}
		return sb.toString();
	}

	private static String getSaveDirectoryFromProperties() {
		try (FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "\\plugytools.properties")) {
			Properties properties = new Properties();
			properties.load(in);
			return properties.getProperty("saveDirectory");
		} catch (FileNotFoundException fnf) {
			//prompt to create save file property
			return createSaveProperties();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private static String createSaveProperties() {
		System.out.println("Please enter the path to your save directory: ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		Properties properties = new Properties();
		properties.setProperty("saveDirectory", input);
		scanner.close();

		try (FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\plugytools.properties")) {
			properties.store(fileOut, "Save Files Location");
			return getSaveDirectoryFromProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static boolean isSetInStash(String setConstant, List<StashCollection> stashCollections) {
		for (StashCollection stashCollection : stashCollections) {
			List<Stash> stashes = stashCollection.getStashes();
			if (stashes != null) {
				for (Stash stash : stashes) {
					for (Item item : stash.getItems()) {
						if (!item.isSimple() && item.getComplexData() != null && "SET".equalsIgnoreCase(item.getComplexData().getItemQualityString()) && setConstant.equalsIgnoreCase(item.getComplexData().getItemName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private static boolean isUniqueInLibrary(String uniqueConstant, Library library) {
		if (isUniqueInStash(uniqueConstant, library.getStashes())) {
			return true;
		}

		for (PlayerCharacter playerCharacter : library.getPlayerCharacters()) {
			if (isUniqueInPlayerCharacter(uniqueConstant, playerCharacter)) {
				return true;
			}
		}

		return false;
	}
	
	private static boolean isUniqueInPlayerCharacter(String uniqueConstant, PlayerCharacter playerCharacter) {
		for (Item item : playerCharacter.getCharacterItems().getItems()) {
			if (!item.isSimple() && item.getComplexData() != null && "UNIQUE".equalsIgnoreCase(item.getComplexData().getItemQualityString()) && uniqueConstant.equalsIgnoreCase(item.getComplexData().getItemName())) {
				return true;
			}
		}
		
		for (Item item : playerCharacter.getCorpseItems().getItems()) {
			if (!item.isSimple() && item.getComplexData() != null && "UNIQUE".equalsIgnoreCase(item.getComplexData().getItemQualityString()) && uniqueConstant.equalsIgnoreCase(item.getComplexData().getItemName())) {
				return true;
			}
		}
		
		for (Item item : playerCharacter.getMercenaryItems().getItems()) {
			if (!item.isSimple() && item.getComplexData() != null && "UNIQUE".equalsIgnoreCase(item.getComplexData().getItemQualityString()) && uniqueConstant.equalsIgnoreCase(item.getComplexData().getItemName())) {
				return true;
			}
		}
		
		return false;
	}

	private static boolean isUniqueInStash(String uniqueConstant, List<StashCollection> stashCollections) {
		for (StashCollection stashCollection : stashCollections) {
			List<Stash> stashes = stashCollection.getStashes();
			if (stashes != null) {
				for (Stash stash : stashes) {
					for (Item item : stash.getItems()) {
						if (!item.isSimple() && item.getComplexData() != null && "UNIQUE".equalsIgnoreCase(item.getComplexData().getItemQualityString()) && uniqueConstant.equalsIgnoreCase(item.getComplexData().getItemName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static List<Stash> getStashesFromData(byte[] data, int nbStash, int startStashIndex) {
		List<Stash> stashes = new ArrayList<>();
		int currentIndex = startStashIndex;
		for (int currentStashNum = 0; currentStashNum < nbStash; currentStashNum++) {
			Stash stash = new Stash();
			System.out.println("Current stash number: " + currentStashNum);
			int stIndex = getStartIndexOfNextHeader(Constants.ST, data, currentIndex);
			int nextStIndex = getStartIndexOfNextHeader(Constants.ST, data, stIndex+2);
			System.out.println("ST index: " + stIndex);
			System.out.println("Next ST Index: " + nextStIndex);
			if (stIndex == -1) {
				System.err.println("ST index was not found, setting nbStash to 1");
				nbStash = 1;
			}
			int jmHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, stIndex);
			System.out.println("JM header index: " + jmHeaderIndex);
			int nbItem = data[jmHeaderIndex+2];
			currentIndex = jmHeaderIndex+2;
			System.out.println("Number of Items in stash: " + nbItem);
			for (int currentItemNum = 0; currentItemNum < nbItem; currentItemNum++) {
				int currentJmItemHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, currentIndex);
				String hexIndex = getHexStringFromInt(currentJmItemHeaderIndex);
				System.out.println("Current JM Item Header Index: " + currentJmItemHeaderIndex + "; Hex: " + hexIndex);
				int nextJmItemHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, currentJmItemHeaderIndex + 2);
				String itemHex;
				byte[] itemArray;
				if (nextJmItemHeaderIndex == -1) {
					//End of File!
					itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex, data.length);
					itemArray = Arrays.copyOfRange(data, currentJmItemHeaderIndex, data.length-1);
				} else if ((nextJmItemHeaderIndex > nextStIndex) && (nextStIndex != -1)) {
					//Next JM header is in next stash, get itemHex from JM to ST (exclusive)
					itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex, nextStIndex);
					itemArray = Arrays.copyOfRange(data, currentJmItemHeaderIndex, nextStIndex);
					currentIndex = nextStIndex;
				} else {
					itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex, nextJmItemHeaderIndex);
					itemArray = Arrays.copyOfRange(data, currentJmItemHeaderIndex, nextJmItemHeaderIndex);
					currentIndex = nextJmItemHeaderIndex;
				}
				Item item = getItemFromBinaryString(itemHex, itemArray, hexIndex);
				if (item.getLocation() == null) {
					nbItem++;
				}
				
				if (item.getNumOfItemsInSockets() > 0 && !item.isSimple()) {
					nbItem += item.getNumOfItemsInSockets();
				}
				stash.getItems().add(item);
			}
			stashes.add(stash);
		}
		return stashes;
	}

	public static Item getItemFromBinaryString(String itemHex, byte[] itemArray, String hexIndex) {
		//String binaryString = getBinaryStringFromItemArray(itemArray);
		String binaryString = getBinaryStringFromItemHex(itemHex);
		Item item = new Item();
		try {
			if (!binaryString.isEmpty()) {
				//Identified, offset 20
				boolean isIdentified = getBooleanFromChar(binaryString, 20);
				//Socketed, offset 27
				boolean isSocketed = getBooleanFromChar(binaryString, 27);
				//Ear, offset 32
				boolean isEar = getBooleanFromChar(binaryString,32);
				//Simple Item, offset 37
				boolean isSimple = getBooleanFromChar(binaryString,37);
				//Ethereal, offset 38
				boolean isEthereal = getBooleanFromChar(binaryString,38);
				//Personalized, offset 40
				boolean isPersonalized = getBooleanFromChar(binaryString, 40);
				//Runeword, offset 42
				boolean isRuneword = getBooleanFromChar(binaryString, 42);
				String location = getItemLocation(binaryString);
				//getting decimal number requires another reverse
				int colNum = getDecimalFromSubstring(binaryString, 65, 69, true);
				int rowNum = getDecimalFromSubstring(binaryString, 69, 73, true);
				char typeOne = (char)getDecimalFromSubstring(binaryString, 76, 84, true);
				char typeTwo = (char)getDecimalFromSubstring(binaryString, 84, 92, true);
				char typeThree = (char)getDecimalFromSubstring(binaryString, 92, 100, true);
				char typeFour = (char)getDecimalFromSubstring(binaryString, 100, 108, true);
				String itemType = "" + typeOne + typeTwo + typeThree + typeFour;
				itemType = itemType.trim();
				
				int numOfItemsInSockets = 0;
				if (isSocketed) {
					//get number of sockets
					numOfItemsInSockets = getDecimalFromSubstring(binaryString, 108, 111, true);
				}
				
				ComplexData complexData = new ComplexData();
				//if not simple, get more item info
				if (!isSimple) {
					//start at offset 111
					String itemId = binaryString.substring(111, 143);
					
					int itemLevel = getDecimalFromSubstring(binaryString, 143, 150, true);
					
					int itemQuality = getDecimalFromSubstring(binaryString, 150, 154, true);
					
					boolean hasMultiplePictures = getBooleanFromChar(binaryString, 154);
					
					int currentIndex = 155;
					
					if (hasMultiplePictures) {
						//the next 3 bits are a picture ID
						int pictureId = getDecimalFromSubstring(binaryString, currentIndex, currentIndex+3, true);
						currentIndex += 3;
					}
					
					//check the next bit for class specific
					boolean isClassSpecific = getBooleanFromChar(binaryString, currentIndex);
					currentIndex += 1;
					
					if (isClassSpecific) {
						//next 11 bits are class specific data
						
						//what do I do with this?
						
						currentIndex += 11;
					}
					
					String itemQualityString = "";
					int fileId = 0;
					String itemName = "";
					switch (itemQuality) {
					case 1:
						//low quality
						itemQualityString = "LOW";
						//id is next 3 bits
						fileId = getDecimalFromSubstring(binaryString, currentIndex, currentIndex+3, false);
						currentIndex+=3;
						break;
					case 2:
						//normal
						itemQualityString = "NORMAL";
						break;
					case 3:
						//high quality
						//no idea what to do here
						itemQualityString = "HIGH";
						currentIndex+=3;
						break;
					case 4:
						//magical
						itemQualityString = "MAGIC";
						int magicPrefixId = getDecimalFromSubstring(binaryString, currentIndex, currentIndex+11, true);
						
						//get prefix name from id
						
						int magicSuffixId = getDecimalFromSubstring(binaryString, currentIndex+11, currentIndex+22, true);
						
						//get suffix name from id
						currentIndex += 22;
						break;
					case 5:
						//set
						itemQualityString = "SET";
						int setId = getDecimalFromSubstring(binaryString, currentIndex, currentIndex+12, true);
						fileId = setId;
						
						//get set name from id
						itemName = SetItems.getInstance().getSetItems().get(setId).getIndex();
						currentIndex += 12;
						break;
					case 6:
					case 8:
						//rare or crafted
						break;
						
					case 7:
						//unique
						itemQualityString = "UNIQUE";
						int uniqueId = getDecimalFromSubstring(binaryString, currentIndex, currentIndex+12, true);
						fileId = uniqueId;
						
						//get unique name from id
						try {
							itemName = UniqueItems.getInstance().getUniqueItems().get(uniqueId).getIndex();
						} catch (IndexOutOfBoundsException idx) {
							System.err.println("Wrong uniqueId parsed, no index found");
						}
						currentIndex += 12;
						
						break;
					}
					
					if (isRuneword) {
						
					}
					
					complexData = new ComplexData(itemId, itemLevel, itemQualityString, fileId, itemName);
				}
				item = new Item(itemHex, itemArray, isIdentified, isSocketed, isEar, isSimple, isEthereal, isPersonalized, isRuneword, location, colNum, rowNum, itemType, binaryString, complexData, hexIndex, numOfItemsInSockets);
			}
		} catch (StringIndexOutOfBoundsException sIdx) {
			System.err.println("Error parsing item: " + itemHex);
		}
		
		return item;
	}

	private static String getBinaryStringFromItemHex(String itemHex) {
		String binaryString = "0" + new BigInteger(itemHex, 16).toString(2);
		StringBuilder reversedBinaryString = new StringBuilder();
		for (int i = 16; i < binaryString.length(); i+=8) {
			StringBuilder innerStringBuilder = new StringBuilder().append(binaryString.substring(i, i+8));
			innerStringBuilder = innerStringBuilder.reverse();
			reversedBinaryString.append(innerStringBuilder);
		}
		String JM = binaryString.substring(0, 16);
		return JM + reversedBinaryString.toString();
	}

	private static int getDecimalFromSubstring(String binaryString, int beginIndex, int endIndex, boolean reverse) {
		String binarySubString = binaryString.substring(beginIndex, endIndex);
		if (reverse) {
			StringBuilder reverseSubString = new StringBuilder().append(binarySubString);
			reverseSubString = reverseSubString.reverse();
			binarySubString = reverseSubString.toString();
		}
		int result = Integer.parseInt(binarySubString, 2);
		return result;
	}

	private static String getItemLocation(String binaryString) {
		int locationValue = getDecimalFromSubstring(binaryString, 58, 61, true);
		switch (locationValue) {
		case 0:
			//Found in Inventory, Cube, or Stash from bit 73
			locationValue = getDecimalFromSubstring(binaryString, 73, 76, true);
			switch (locationValue) {
			case 1:
				return Constants.INVENTORY;
			case 4:
				return Constants.CUBE;
			case 5:
				return Constants.STASH;
			}
			break;
		case 1:
			//Found on Body, get equip from bit 61
			locationValue = getDecimalFromSubstring(binaryString, 61, 65, true);
			switch (locationValue) {
			case 1:
				return Constants.HELMET;
			case 2:
				return Constants.AMULET;
			case 3:
				return Constants.ARMOR;
			case 4:
				return Constants.RHAND;
			case 5:
				return Constants.LHAND;
			case 6:
				return Constants.RRING;
			case 7:
				return Constants.LRING;
			case 8:
				return Constants.EQUIP_BELT;
			case 9:
				return Constants.BOOTS;
			case 10:
				return Constants.GLOVES;
			case 11:
				return Constants.ALT_RHAND;
			case 12:
				return Constants.ALT_LHAND;
			}
		case 2:
			return Constants.BELT;
		case 4:
			return Constants.MOVED;
		case 6:
			return Constants.SOCKET;
		}
		return null;
	}

	private static boolean getBooleanFromChar(String binaryString, int binaryIndex) {
		char charFromBinaryString = binaryString.charAt(binaryIndex);
		return ((charFromBinaryString == '0') ? false : true);
	}

	private static int getStartIndexOfNextHeader(String header, byte[] data, int startingIndex) {
		boolean found = false;
		int index = startingIndex;
		if (index == -1) {
			index = 0;
		}
		while (!found) {
			if (index + 2 > data.length) {
				return -1;
			}
			String stringFromData = getHexStringFromRange(data, index, index+2);
			if (header.equals(stringFromData)) {
				found = true;
			} else {
				index += 1;
			}
		}
		return index;
	}
	
	private static String getHexStringFromRange(byte[] data, int start, int end) {
		String result = "";
		for (int i = start; i < end; i++) {
			result += getHexStringFromInt(data[i] & 0xff);
		}
		return result.toUpperCase();
	}

	private static String getHexStringFromInt(int b) {
		String hexString = Integer.toHexString(b);
		if (hexString.length() == 1) {
			return "0" + hexString;
		} else {
			return hexString;
		}
	}
}
