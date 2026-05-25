package xyz.nasasupercomputer.birmingham.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenRenderer;
import xyz.nasasupercomputer.birmingham.ItemGems.GemSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;

@Mod.EventBusSubscriber( modid = MainRegistry.MOD_ID)
public class ClientEventHandler {

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltipList = event.getToolTip();
        List<String> tooltipString = new ArrayList<String>();
        
        // ===================
        // ITEM HAZARDS
        if (ForgeConfigs.enableItemHazards) {
	        
	        // Apply Hazard Description
	        HazardSystem.ApplyHazardTooltip(stack, event.getEntity(), tooltipString);

        }
        
        // ===================
        // GEM ALERT!

  		if (ForgeConfigs.enableItemGems) {
        
        	// Apply Gem Description
        	GemSystem.ApplyGemTooltip(stack, event.getEntity(), tooltipString);
        
    	}
  		
    	// Finalize any changes. by converting & adding back onto tooltipString back to tooltipList components.
    	for (String line : tooltipString) {
        		tooltipList.add(Component.literal(line));
    	}
	}
}
