package xyz.nasasupercomputer.birmingham.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID)
public class ServerEventHandler {
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
        // Run only on the server side
        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        // Only run once per tick
        // i still have no idea what this really is im ngl.
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // Iterate thru inventory & Apply Hazards
        List<ItemStack> inventoryItems = new ArrayList<ItemStack>();
        inventoryItems.addAll(player.getInventory().items);
        inventoryItems.add(player.getOffhandItem());
        
        if (inventoryItems != null && ForgeConfigs.enableItemHazards) {
            
		    for (ItemStack stack : inventoryItems) {
		        HazardSystem.ApplyHazard(stack, player);
		    }
        }
	}
}
