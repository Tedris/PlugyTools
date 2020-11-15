package domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MiscItems {
	private List<MiscItem> miscItems;
	private static final MiscItems instance = new MiscItems();
	
	private MiscItems() {
		init();
	}
	
	private void init() {		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Misc.txt");
		
		try (Scanner scanner = new Scanner(inputStream)) {
			//skip first line
			scanner.nextLine();
			miscItems = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tabbedLine = line.split("\t");
				if (tabbedLine != null && tabbedLine.length > 0 && !tabbedLine[0].equals("Expansion")) {
					MiscItem miscItem = new MiscItem();
					miscItem.setName(tabbedLine[0]);
					miscItem.setActualName(tabbedLine[1]);
					miscItem.setSzFlavorText(tabbedLine[2]);
					miscItem.setCompactSave(tabbedLine[3]);
					miscItem.setVersion(tabbedLine[4]);
					miscItem.setLevel(tabbedLine[5]);
					miscItem.setLevelRequirement(tabbedLine[6]);
					miscItem.setRarity(tabbedLine[7]);
					miscItem.setSpawnable(tabbedLine[8]);
					miscItem.setSpeed(tabbedLine[9]);
					miscItem.setNoDurability(tabbedLine[10]);
					miscItem.setCost(tabbedLine[11]);
					miscItem.setGambleCost(tabbedLine[12]);
					miscItem.setAutoPrefixCode(tabbedLine[13]);
					miscItem.setAlternateGraphics(tabbedLine[14]);
					miscItem.setNameStrComponent(tabbedLine[15]);
					miscItem.setInvWidth(tabbedLine[16]);
					miscItem.setInvHeight(tabbedLine[17]);
					miscItem.setHasInv(tabbedLine[18]);
					miscItem.setGemSockets(tabbedLine[19]);
					miscItem.setGemApplyType(tabbedLine[20]);
					miscItem.setFlippyFile(tabbedLine[21]);
					miscItem.setInvFile(tabbedLine[22]);
					miscItem.setUniqueInvFile(tabbedLine[23]);
					miscItem.setSpecial(tabbedLine[24]);
					miscItem.setTransmogrify(tabbedLine[25]);
					miscItem.setTmogType(tabbedLine[26]);
					miscItem.setTmogMin(tabbedLine[27]);
					miscItem.setTmogMax(tabbedLine[28]);
					miscItem.setUsable(tabbedLine[29]);
					miscItem.setThrowable(tabbedLine[30]);
					miscItem.setType(tabbedLine[31]);
					miscItem.setType2(tabbedLine[32]);
					miscItem.setDropSound(tabbedLine[33]);
					miscItem.setDropSfxFrame(tabbedLine[34]);
					miscItem.setUseSound(tabbedLine[35]);
					miscItem.setUnique(tabbedLine[36]);
					miscItem.setTransparent(tabbedLine[37]);
					miscItem.setTransTbl(tabbedLine[38]);
					miscItem.setLightRadius(tabbedLine[39]);
					miscItem.setBelt(tabbedLine[40]);
					miscItem.setAutoBelt(tabbedLine[41]);
					miscItem.setStackable(tabbedLine[42]);
					miscItem.setMinStack(tabbedLine[43]);
					miscItem.setMaxStack(tabbedLine[44]);
					miscItem.setSpawnStack(tabbedLine[45]);
					miscItem.setQuest(tabbedLine[46]);
					miscItem.setQuestDiffCheck(tabbedLine[47]);
					miscItem.setMissleType(tabbedLine[48]);
					miscItem.setSpellIcon(tabbedLine[49]);
					miscItem.setpSpell(tabbedLine[50]);
					miscItem.setState(tabbedLine[51]);
					miscItem.setcState1(tabbedLine[52]);
					miscItem.setcState2(tabbedLine[53]);
					miscItem.setLength(tabbedLine[54]);
					miscItem.setStat1(tabbedLine[55]);
					miscItem.setCalc1(tabbedLine[56]);
					miscItem.setStat2(tabbedLine[57]);
					miscItem.setCalc2(tabbedLine[58]);
					miscItem.setStat3(tabbedLine[59]);
					miscItem.setCalc3(tabbedLine[60]);
					miscItem.setSpellDesc(tabbedLine[61]);
					miscItem.setSpellDescStr(tabbedLine[62]);
					miscItem.setSpellDescCalc(tabbedLine[63]);
					miscItem.setDurWarning(tabbedLine[64]);
					miscItem.setQntWarning(tabbedLine[65]);
					miscItem.setGemOffset(tabbedLine[66]);
					miscItem.setBetterGem(tabbedLine[67]);
					miscItem.setBitField1(tabbedLine[68]);
					miscItem.setCharsiMin(tabbedLine[69]);
					miscItem.setCharsiMax(tabbedLine[70]);
					miscItem.setCharsiMagicMin(tabbedLine[71]);
					miscItem.setCharsiMagicMax(tabbedLine[72]);
					miscItem.setCharsiMagicLvl(tabbedLine[73]);
					miscItem.setGheedMin(tabbedLine[74]);
					miscItem.setGheedMax(tabbedLine[75]);
					miscItem.setGheedMagicMin(tabbedLine[76]);
					miscItem.setGheedMagicMax(tabbedLine[77]);
					miscItem.setGheedMagicLvl(tabbedLine[78]);
					miscItem.setAkaraMin(tabbedLine[79]);
					miscItem.setAkaraMax(tabbedLine[80]);
					miscItem.setAkaraMagicMin(tabbedLine[81]);
					miscItem.setAkaraMagicMax(tabbedLine[82]);
					miscItem.setAkaraMagicLvl(tabbedLine[83]);
					miscItem.setFaraMin(tabbedLine[84]);
					miscItem.setFaraMax(tabbedLine[85]);
					miscItem.setFaraMagicMin(tabbedLine[86]);
					miscItem.setFaraMagicMax(tabbedLine[87]);
					miscItem.setFaraMagicLvl(tabbedLine[88]);
					miscItem.setLysanderMin(tabbedLine[89]);
					miscItem.setLysanderMax(tabbedLine[90]);
					miscItem.setLysanderMagicMin(tabbedLine[91]);
					miscItem.setLysanderMagicMax(tabbedLine[92]);
					miscItem.setLysanderMagicLvl(tabbedLine[93]);
					miscItem.setDrognanMin(tabbedLine[94]);
					miscItem.setDrognanMax(tabbedLine[95]);
					miscItem.setDrognanMagicMin(tabbedLine[96]);
					miscItem.setDrognanMagicMax(tabbedLine[97]);
					miscItem.setDrognanMagicLvl(tabbedLine[98]);
					miscItem.setHraltiMin(tabbedLine[99]);
					miscItem.setHraltiMax(tabbedLine[100]);
					miscItem.setHraltiMagicMin(tabbedLine[101]);
					miscItem.setHraltiMagicMax(tabbedLine[102]);
					miscItem.setHraltiMagicLvl(tabbedLine[103]);
					miscItem.setAlkorMin(tabbedLine[104]);
					miscItems.add(miscItem);
				}
			}
		}	
	}
	
	public static MiscItems getInstance() {
		return instance;
	}
	
	public List<MiscItem> getMiscItems() {
		return miscItems;
	}
}
