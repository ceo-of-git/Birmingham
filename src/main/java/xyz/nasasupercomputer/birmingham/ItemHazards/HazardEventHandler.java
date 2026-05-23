package xyz.nasasupercomputer.birmingham.ItemHazards;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.MainRegistry;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID)
public class HazardEventHandler {

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
        for (ItemStack stack : player.getInventory().items) {

            HazardSystem.ApplyHazard(stack, player);
        }
	}
	

	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltipList = event.getToolTip();
        List<String> tooltipString = new ArrayList<String>();
        
        // Apply Hazard Description
        HazardSystem.ApplyHazardTooltip(stack, event.getEntity(), tooltipString);
        
        // Finalize any changes. by converting & adding back onto tooltipString back to tooltipList components.
        for (String line : tooltipString) {
            tooltipList.add(Component.literal(line));
        }
	}
}
