import org.junit.Test;

import plugytools.PlugyTools;

public class PlugyToolsTest {
	
	PlugyTools plugyTools;

	@Test
	public void testGetItemFromBinaryString() {
		String itemHex = "4A4D100080006500723AD626030233B85183F105C8000026004C006C00EC84500AA115422D84A67459D898524C8199E78382C27F";
		plugyTools = new PlugyTools();
		byte[] itemArray = null;
		PlugyTools.getItemFromBinaryString(itemHex, itemArray);
	}

}
