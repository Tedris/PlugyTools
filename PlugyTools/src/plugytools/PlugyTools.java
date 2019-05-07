package plugytools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import domain.Constants;
import domain.Item;
import domain.Stash;

public class PlugyTools {
	public static void main(String ... args) throws Exception {
		//do basic stuff, try to read from sss file
		
		Path fileLocation = Paths.get("TestStash.sss");
		
		byte[] data = Files.readAllBytes(fileLocation);
		
		//check for valid SSS file:
		
		String SSS = getHexStringFromRange(data, 0, 3);
		int nbStash = 0;
		String sharedGoldAmount = "";
		int startStashIndex = 0;
		
		if (SSS.equalsIgnoreCase("535353")) {
			String fileVersion = getHexStringFromRange(data, 4, 6);
			if (fileVersion.equalsIgnoreCase("3031")) {
				//file is version 01, no shared gold
				nbStash = data[6];
				startStashIndex = 10;
			} else if (fileVersion.equalsIgnoreCase("3032")) {
				//file is version 02, has shared gold
				sharedGoldAmount = Integer.toString(data[6]);
				nbStash = data[10];
				startStashIndex = 14;
			} else {
				throw new Exception("File has invalid version set, cannot determine gold sharing indicator");
			}
			
			System.out.println("Number of stashes: " + nbStash);
			
			List<Stash> stashes = getStashesFromFile(data, nbStash, startStashIndex);
			
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
				if (nextJmItemHeaderIndex == -1) {
					//End of File!
					itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex+2, data.length);
				} else if (nextJmItemHeaderIndex > nextStIndex) {
					//Next JM header is in next stash, get itemHex from JM to ST
					itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex+2, nextStIndex);
					currentIndex = nextStIndex;
				} else {
					itemHex = getHexStringFromRange(data, currentJmItemHeaderIndex+2, nextJmItemHeaderIndex);
					currentIndex = nextJmItemHeaderIndex;
				}
				Item item = new Item();
				item.setHexString(itemHex);
				stash.getItems().add(item);
			}
			stashes.add(stash);
		}
		return stashes;
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
			result += Integer.toHexString(data[i]);
		}
		return result.toUpperCase();
	}
}
