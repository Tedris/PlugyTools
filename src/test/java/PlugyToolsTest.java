import org.junit.Test;

import plugytools.PlugyTools;

public class PlugyToolsTest {
	
	PlugyTools plugyTools;

	@Test
	public void testGetItemFromBinaryString() {
		String itemHex = "4A4D10088000650012AA169303828550054ECA514F5C2127574478FA690B0C58B107C30026C0460134000050B2008A0162047F0A8811332A60C9C47F";
		plugyTools = new PlugyTools();
		byte[] itemArray = null;
		PlugyTools.getItemFromBinaryString(itemHex, itemArray);
	}

}
