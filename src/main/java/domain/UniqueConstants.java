package domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniqueConstants {
	
	private List<String> uniqueItems;
	private static final UniqueConstants instance = new UniqueConstants();
	
	private UniqueConstants() {
		init();
	}
	
	private void init() {		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("HolyGrailUniques.txt");
		
		try (Scanner scanner = new Scanner(inputStream)) {
			uniqueItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if (line != null && !line.isEmpty()) {
					uniqueItems.add(line);
				}
			}
		}
	}
	
	public static UniqueConstants getInstance() {
		return instance;
	}
	
	public List<String> getUniqueConstants() {
		return uniqueItems;
	}
}
