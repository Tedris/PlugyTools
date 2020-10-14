package domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SetItems {
	
	private List<SetItem> setItems;
	private static final SetItems instance = new SetItems();
	
	private SetItems() {
		init();
	}
	
	private void init() {		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("SetItems.txt");
		
		try (Scanner scanner = new Scanner(inputStream)) {
			//skip first line
			scanner.nextLine();
			setItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tabbedLine = line.split("\t");
				
				if (tabbedLine != null && tabbedLine.length > 0 && !tabbedLine[0].equals("Expansion")) {
					SetItem setItem = new SetItem();
					try {
						setItem.setIndex(tabbedLine[0]);
						setItem.setSetItem(tabbedLine[1]);
						setItem.setItem(tabbedLine[2]);
						setItem.setRarity(tabbedLine[3]);
						setItem.setLevel(tabbedLine[4]);
						setItem.setLevelRequirement(tabbedLine[5]);
						setItem.setChrTransform(tabbedLine[6]);
						setItem.setInvTransform(tabbedLine[7]);
						setItem.setInvFile(tabbedLine[8]);
						setItem.setFlippyFile(tabbedLine[9]);
						setItem.setDropSound(tabbedLine[10]);
						setItem.setDropSfxFrame(tabbedLine[11]);
						setItem.setUseSound(tabbedLine[12]);
						setItem.setCostMult(tabbedLine[13]);
						setItem.setCostAdd(tabbedLine[14]);
						setItem.setAddFunc(tabbedLine[15]);
						setItem.setProp1(tabbedLine[16]);
						setItem.setPar1(tabbedLine[17]);
						setItem.setMin1(tabbedLine[18]);
						setItem.setMax1(tabbedLine[19]);
						setItem.setProp2(tabbedLine[20]);
						setItem.setPar2(tabbedLine[21]);
						setItem.setMin2(tabbedLine[22]);
						setItem.setMax2(tabbedLine[23]);
						setItem.setProp3(tabbedLine[24]);
						setItem.setPar3(tabbedLine[25]);
						setItem.setMin3(tabbedLine[26]);
						setItem.setMax3(tabbedLine[27]);
						setItem.setProp4(tabbedLine[28]);
						setItem.setPar4(tabbedLine[29]);
						setItem.setMin4(tabbedLine[30]);
						setItem.setMax4(tabbedLine[31]);
						setItem.setProp5(tabbedLine[32]);
						setItem.setPar5(tabbedLine[33]);
						setItem.setMin5(tabbedLine[34]);
						setItem.setMax5(tabbedLine[35]);
						setItem.setProp6(tabbedLine[36]);
						setItem.setPar6(tabbedLine[37]);
						setItem.setMin6(tabbedLine[38]);
						setItem.setMax6(tabbedLine[39]);
						setItem.setProp7(tabbedLine[40]);
						setItem.setPar7(tabbedLine[41]);
						setItem.setMin7(tabbedLine[42]);
						setItem.setMax7(tabbedLine[43]);
						setItem.setProp8(tabbedLine[44]);
						setItem.setPar8(tabbedLine[45]);
						setItem.setMin8(tabbedLine[46]);
						setItem.setMax8(tabbedLine[47]);
						setItem.setProp9(tabbedLine[48]);
						setItem.setPar9(tabbedLine[49]);
						setItem.setMin9(tabbedLine[50]);
						setItem.setMax9(tabbedLine[51]);
						setItem.setAprop1a(tabbedLine[52]);
						setItem.setApar1a(tabbedLine[53]);
						setItem.setAmin1a(tabbedLine[59]);
						setItem.setAmax1a(tabbedLine[60]);
						setItem.setAprop1b(tabbedLine[61]);
						setItem.setApar1b(tabbedLine[62]);
						setItem.setAmin1b(tabbedLine[63]);
						setItem.setAmax1b(tabbedLine[64]);
						setItem.setAprop2a(tabbedLine[65]);
						setItem.setApar2a(tabbedLine[66]);
						setItem.setAmin2a(tabbedLine[67]);
						setItem.setAmax2a(tabbedLine[68]);
						setItem.setAprop2b(tabbedLine[69]);
						setItem.setApar2b(tabbedLine[70]);
						setItem.setAmin2b(tabbedLine[71]);
						setItem.setAmax2b(tabbedLine[72]);
						setItem.setAprop3a(tabbedLine[73]);
						setItem.setApar3a(tabbedLine[74]);
						setItem.setAmin3a(tabbedLine[75]);
						setItem.setAmax3a(tabbedLine[76]);
						setItem.setAprop3b(tabbedLine[77]);
						setItem.setApar3b(tabbedLine[78]);
						setItem.setAmin3b(tabbedLine[79]);
						setItem.setAmax3b(tabbedLine[80]);
						setItem.setAprop4a(tabbedLine[81]);
						setItem.setApar4a(tabbedLine[82]);
						setItem.setAmin4a(tabbedLine[83]);
						setItem.setAmax4a(tabbedLine[84]);
						setItem.setAprop4b(tabbedLine[85]);
						setItem.setApar4b(tabbedLine[86]);
						setItem.setAmin4b(tabbedLine[87]);
						setItem.setAmax4b(tabbedLine[88]);
						setItem.setAprop5a(tabbedLine[89]);
						setItem.setApar5a(tabbedLine[90]);
						setItem.setAmin5a(tabbedLine[91]);
						setItem.setAmax5a(tabbedLine[92]);
						setItem.setAprop5b(tabbedLine[93]);
						setItem.setApar5b(tabbedLine[94]);
						setItem.setAmin5b(tabbedLine[95]);
						setItem.setAmax5b(tabbedLine[96]);
					} catch (ArrayIndexOutOfBoundsException e) {
						
					}
					setItems.add(setItem);
				}
			}
		}
	}
	
	public static SetItems getInstance() {
		return instance;
	}
	
	public List<SetItem> getSetItems() {
		return setItems;
	}
}
