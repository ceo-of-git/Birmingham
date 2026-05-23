package xyz.nasasupercomputer.birmingham.ItemGems;

import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.Map;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemGems.Types.GemFlame;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

// Adds Hazards to items.
public class GemRegistry{
	
	// Register each hazard (And their 'intensity')
	//static final GemFlame fireGem = new GemFlame();
	
	static HashMap<String,IGemType> gemList = new HashMap<>();
	public GemRegistry() {
		gemList.put("Flame", new GemFlame());
	}
	//EXTREMELY SCUFFED WAY OF CODING THIS but i'm not a pro coder so idc
	
	// =========================
	// ITEM HAZARDS
	public static void RegisterAllGems() {
		
		//GemSystem.RegisterGem(ItemRegistry.TEST_ITEM.get(), fireGem);
	}

}
