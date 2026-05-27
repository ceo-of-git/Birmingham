package xyz.nasasupercomputer.birmingham.ItemGems;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.HashMap;
import java.util.Map;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemGems.Types.GemFlame;
import xyz.nasasupercomputer.birmingham.ItemGems.Types.GemVampiric;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

// Adds Hazards to items.
public class GemRegistry{
	
	// Register each hazard (And their 'intensity')
	//static final GemFlame fireGem = new GemFlame();
	
	public static HashMap<String,IGemType> gemList = new HashMap<>();
	
	public static void register(IEventBus modEventBus) {
		gemList.put("Flame", new GemFlame());
		gemList.put("Vampiric", new GemVampiric());
	}

}
