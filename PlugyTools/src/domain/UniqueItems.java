package domain;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniqueItems {
	
	private List<UniqueItem> uniqueItems;
	
	public UniqueItems() {
		init();
	}
	
	public void init() {
		Path path = null;
		
		try {
			path = Paths.get(getClass().getClassLoader().getResource("\\UniqueItems.txt").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (Scanner scanner = new Scanner(path)) {
			//skip first line
			scanner.nextLine();
			uniqueItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tabbedLine = line.split("\t");
				
				if (tabbedLine != null && tabbedLine.length > 0 && !tabbedLine[0].equals("Expansion")) {
					UniqueItem uniqueItem = new UniqueItem();
					uniqueItem.setIndex(tabbedLine[0]);
					uniqueItem.setVersion(tabbedLine[1]);
					uniqueItem.setEnabled(tabbedLine[2]);
					uniqueItem.setLadder(tabbedLine[3]);
					uniqueItem.setRarity(tabbedLine[4]);
					uniqueItem.setNolimit(tabbedLine[5]);
					uniqueItem.setLevel(tabbedLine[6]);
					uniqueItem.setLevelReq(tabbedLine[7]);
					uniqueItem.setCode(tabbedLine[8]);
					uniqueItem.setType(tabbedLine[9]);
					uniqueItem.setUber(tabbedLine[10]);
					uniqueItem.setCarry1(tabbedLine[11]);
					uniqueItem.setCostMult(tabbedLine[12]);
					uniqueItem.setCostAdd(tabbedLine[13]);
					uniqueItem.setChrTransform(tabbedLine[14]);
					uniqueItem.setInvTransform(tabbedLine[15]);
					uniqueItem.setFlippyFile(tabbedLine[16]);
					uniqueItem.setInvFile(tabbedLine[17]);
					uniqueItem.setDropSound(tabbedLine[18]);
					uniqueItem.setDropsFxFrame(tabbedLine[19]);
					uniqueItem.setUseSound(tabbedLine[20]);
					uniqueItem.setProp1(tabbedLine[21]);
					uniqueItem.setPar1(tabbedLine[22]);
					uniqueItem.setMin1(tabbedLine[23]);
					uniqueItem.setMax1(tabbedLine[24]);
					uniqueItem.setProp2(tabbedLine[25]);
					uniqueItem.setPar2(tabbedLine[26]);
					uniqueItem.setMin2(tabbedLine[27]);
					uniqueItem.setMax2(tabbedLine[28]);
					uniqueItem.setProp3(tabbedLine[29]);
					uniqueItem.setPar3(tabbedLine[30]);
					uniqueItem.setMin3(tabbedLine[31]);
					uniqueItem.setMax3(tabbedLine[32]);
					uniqueItem.setProp4(tabbedLine[33]);
					uniqueItem.setPar4(tabbedLine[34]);
					uniqueItem.setMin4(tabbedLine[35]);
					uniqueItem.setMax4(tabbedLine[36]);
					uniqueItem.setProp5(tabbedLine[37]);
					uniqueItem.setPar5(tabbedLine[38]);
					uniqueItem.setMin5(tabbedLine[39]);
					uniqueItem.setMax5(tabbedLine[40]);
					uniqueItem.setProp6(tabbedLine[41]);
					uniqueItem.setPar6(tabbedLine[42]);
					uniqueItem.setMin6(tabbedLine[43]);
					uniqueItem.setMax6(tabbedLine[44]);
					uniqueItem.setProp7(tabbedLine[45]);
					uniqueItem.setPar7(tabbedLine[46]);
					uniqueItem.setMin7(tabbedLine[47]);
					uniqueItem.setMax7(tabbedLine[48]);
					uniqueItem.setProp8(tabbedLine[49]);
					uniqueItem.setPar8(tabbedLine[50]);
					uniqueItem.setMin8(tabbedLine[51]);
					uniqueItem.setMax8(tabbedLine[52]);
					uniqueItem.setProp9(tabbedLine[53]);
					uniqueItem.setPar9(tabbedLine[54]);
					uniqueItem.setMin9(tabbedLine[55]);
					uniqueItem.setMax9(tabbedLine[56]);
					uniqueItem.setProp10(tabbedLine[57]);
					uniqueItem.setPar10(tabbedLine[58]);
					uniqueItem.setMin10(tabbedLine[59]);
					uniqueItem.setMax10(tabbedLine[60]);
					uniqueItem.setProp11(tabbedLine[61]);
					uniqueItem.setPar11(tabbedLine[62]);
					uniqueItem.setMin11(tabbedLine[63]);
					uniqueItem.setMax11(tabbedLine[64]);
					uniqueItem.setProp12(tabbedLine[65]);
					uniqueItem.setPar12(tabbedLine[66]);
					uniqueItem.setMin12(tabbedLine[67]);
					uniqueItem.setMax12(tabbedLine[68]);
					uniqueItems.add(uniqueItem);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<UniqueItem> getUniqueItems() {
		return uniqueItems;
	}
}
