package plugytools;

import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.ComplexData;
import domain.Constants;
import domain.Item;
import domain.SetItems;
import domain.Stash;
import domain.UniqueItems;

public class PlugyTools {
		
	public static void main(String ... args) throws Exception {
		//do basic stuff, try to read from sss file
		
		Path fileLocation = Paths.get(args[0]);
		
		byte[] data = Files.readAllBytes(fileLocation);
		
		//check for valid SSS file:
		
		String SSS = getHexStringFromRange(data, 0, 3);
		int nbStash = 0;
		int sharedGoldAmount = 0;
		int startStashIndex = 0;
		
		if (SSS.equalsIgnoreCase("535353")) {
			String fileVersion = getHexStringFromRange(data, 4, 6);
			if (fileVersion.equalsIgnoreCase("3031")) {
				//file is version 01, no shared gold
				nbStash = data[6];
				startStashIndex = 10;
			} else if (fileVersion.equalsIgnoreCase("3032")) {
				//file is version 02, has shared gold
				sharedGoldAmount = data[6];
				
				System.out.println("Shared Gold Amount: " + sharedGoldAmount);
				
				nbStash = data[10];
				startStashIndex = 14;
			} else {
				throw new Exception("File has invalid version set, cannot determine gold sharing indicator");
			}
			
			System.out.println("Number of stashes: " + nbStash);
			
			List<Stash> stashes = getStashesFromFile(data, nbStash, startStashIndex);
			
			try (FileWriter file = new FileWriter("teststash.json" )) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(file, stashes);
			}
			
			System.out.println("Stashes read from file: " + stashes.size());
		} else {
			throw new Exception("File does not include SSS header!");
		}
		
		
	}
	
	private static List<Stash> getStashesFromFile(byte[] data, int nbStash, int startStashIndex) {
		List<Stash> stashes = new ArrayList<>();
		int currentIndex = startStashIndex;
		for (int currentStashNum = 0; currentStashNum < nbStash; currentStashNum++) {
			Stash stash = new Stash();
			System.out.println("Current stash number: " + currentStashNum);
			int stIndex = getStartIndexOfNextHeader(Constants.ST, data, currentIndex);
			int nextStIndex = getStartIndexOfNextHeader(Constants.ST, data, stIndex+2);
			System.out.println("ST index: " + stIndex);
			int jmHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, stIndex);
			System.out.println("JM header index: " + jmHeaderIndex);
			int nbItem = data[jmHeaderIndex+2];
			currentIndex = jmHeaderIndex+2;
			System.out.println("Number of Items in stash: " + nbItem);
			for (int currentItemNum = 0; currentItemNum < nbItem; currentItemNum++) {
				int currentJmItemHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, currentIndex);
				System.out.println("Current JM Item Header Index: " + currentJmItemHeaderIndex);
				int nextJmItemHeaderIndex = getStartIndexOfNextHeader(Constants.JM, data, currentJmItemHeaderIndex + 2);
				String itemHex = "";
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
				Item item = getItemFromBinaryString(itemHex, itemArray);
				stash.getItems().add(item);
			}
			stashes.add(stash);
		}
		return stashes;
	}

	public static Item getItemFromBinaryString(String itemHex, byte[] itemArray) {
		//String binaryString = getBinaryStringFromItemArray(itemArray);
		String binaryString = getBinaryStringFromItemHex(itemHex);
		Item item = new Item();
		if (binaryString != null && !binaryString.isEmpty()) {
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
					int lowQualityId = getDecimalFromSubstring(binaryString, currentIndex, currentIndex+3, false);
					fileId = lowQualityId;
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
					itemName = UniqueItems.getInstance().getUniqueItems().get(uniqueId).getIndex();
					currentIndex += 12;
					
					break;
				}
				
				complexData = new ComplexData(itemId, itemLevel, itemQualityString, fileId, itemName);
			}
			item = new Item(itemHex, itemArray, isIdentified, isSocketed, isEar, isSimple, isEthereal, isPersonalized, isRuneword, location, colNum, rowNum, itemType, binaryString, complexData);
		}
		
		return item;
	}

	private static String getBinaryStringFromItemHex(String itemHex) {
		String binaryString = "0" + new BigInteger(itemHex, 16).toString(2);
		String reversedBinaryString = "";
		for (int i = 16; i < binaryString.length(); i+=8) {
			StringBuilder innerStringBuilder = new StringBuilder().append(binaryString.substring(i, i+8));
			innerStringBuilder = innerStringBuilder.reverse();
			reversedBinaryString += innerStringBuilder.toString();
		}
		String JM = binaryString.substring(0, 16);
		return JM + reversedBinaryString;
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

	private static String getBinaryStringFromItemArray(byte[] itemArray) {
		String binaryString = "";
		for (byte b : itemArray) {
			String binaryStringFromByte = Integer.toBinaryString(b);
			if (binaryStringFromByte.length() < 8) {
				binaryStringFromByte = StringUtils.leftPad(binaryStringFromByte, 8, '0');
			}
			binaryString += binaryStringFromByte;
		}
		return binaryString;
	}

	private static int getStartIndexOfNextHeader(String header, byte[] data, int startingIndex) {
		boolean found = false;
		int index = startingIndex;
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
