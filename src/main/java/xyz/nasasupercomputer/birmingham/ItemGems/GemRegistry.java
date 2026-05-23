package xyz.nasasupercomputer.birmingham.ItemGems;

import net.minecraft.world.item.Item;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemGems.Types.GemFlame;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

// Adds Hazards to items.
public class GemRegistry{
	
	// Register each hazard (And their 'intensity')
	static final GemFlame fireGem = new GemFlame();
	
	
	// =========================
	// ITEM HAZARDS
	public static void RegisterAllGems() {
		
		//GemSystem.RegisterGem(ItemRegistry.TEST_ITEM.get(), fireGem);
	}

}
