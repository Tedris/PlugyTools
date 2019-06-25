package domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		Path path = null;
		
		try {
			path = Paths.get(getClass().getClassLoader().getResource("resources/HolyGrailSets.txt").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (Scanner scanner = new Scanner(path)) {
			setItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if (line != null && !line.isEmpty()) {
					setItems.add(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SetConstants getInstance() {
		return instance;
	}
	
	public List<String> getSetConstants() {
		return setItems;
	}
}
