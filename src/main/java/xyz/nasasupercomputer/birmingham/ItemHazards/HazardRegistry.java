package xyz.nasasupercomputer.birmingham.ItemHazards;

import net.minecraft.world.item.Item;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardToxic;

// Adds Hazards to items.
public class HazardRegistry {
	
	// Register each hazard (And their 'intensity')
	static final HazardToxic Hazard_Toxic_T1 = new HazardToxic(1.0);
	
	
	public static void RegisterAllHazards() {
		
		// Put the Hazards onto an Item.
		HazardSystem.RegisterHazard(MainRegistry.TEST_ITEM.get(), Hazard_Toxic_T1);
	}

}
