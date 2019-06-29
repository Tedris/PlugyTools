import org.junit.Test;

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

}
