import java.io.File;

import org.junit.Test;

import domain.PlayerCharacter;
import plugytools.PlugyTools;

public class PlugyToolsTest {
	
	PlugyTools plugyTools;

	@Test
	public void testGetItemFromBinaryString() {
		String itemHex = "4A4D612F806C884082C40541468040178F9E2C7C5EF90F";
		plugyTools = new PlugyTools();
		byte[] itemArray = null;
		PlugyTools.getItemFromBinaryString(itemHex, itemArray, "000");
	}
	
	@Test
	public void testCharacterData() {
		plugyTools = new PlugyTools();
		File character = new File("C:\\Games\\Diablo II\\D2SE\\CORES\\1.13c\\save\\HammerTime.d2s");
		PlayerCharacter hammerTest = PlugyTools.getCharacterData(character);
	}

}
