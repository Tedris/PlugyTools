package domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SetConstants {
	
	private List<String> setItems;
	private static final SetConstants instance = new SetConstants();
	
	private SetConstants() {
		init();
	}
	
	private void init() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("HolyGrailSets.txt");
		
		try (Scanner scanner = new Scanner(inputStream)) {
			setItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if (line != null && !line.isEmpty()) {
					setItems.add(line);
				}
			}
		}
	}
	
	public static SetConstants getInstance() {
		return instance;
	}
	
	public List<String> getSetConstants() {
		return setItems;
	}
}
