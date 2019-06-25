package domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		Path path = null;
		
		try {
			path = Paths.get(getClass().getClassLoader().getResource("resources/HolyGrailUniques.txt").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (Scanner scanner = new Scanner(path)) {
			uniqueItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if (line != null && !line.isEmpty()) {
					uniqueItems.add(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static UniqueConstants getInstance() {
		return instance;
	}
	
	public List<String> getUniqueConstants() {
		return uniqueItems;
	}
}
