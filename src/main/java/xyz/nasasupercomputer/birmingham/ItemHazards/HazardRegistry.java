package xyz.nasasupercomputer.birmingham.ItemHazards;

import net.minecraft.world.item.Item;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardToxic;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

// Adds Hazards to items.
public class HazardRegistry {
	
	// Register each hazard (And their 'intensity')
	static final HazardToxic Hazard_Toxic_T1 = new HazardToxic(1.0);
	
	
	// =========================
	// ITEM HAZARDS
	public static void RegisterAllHazards() {
		
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), Hazard_Toxic_T1);
	}

}
