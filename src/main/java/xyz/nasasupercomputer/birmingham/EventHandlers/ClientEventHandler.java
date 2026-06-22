package xyz.nasasupercomputer.birmingham.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.IBigBlockType;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.ItemGems.GemSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltipList = event.getToolTip();
        List<String> tooltipString = new ArrayList<String>();
        
        // ===================
        // SHOW BIGBLOCK SIZE
        if (Block.byItem(stack.getItem()) instanceof IBigBlockType bigBlock) {
        	int sizeX = bigBlock.GetSizeX();
        	int sizeY = bigBlock.GetSizeY();
        	int sizeZ = bigBlock.GetSizeZ();
        	
        	tooltipList.add(1, Component.literal("[" + sizeX + "x" + sizeY + "x" + sizeZ + "]").withStyle(ChatFormatting.GRAY));
        }
        
        // ===================
        // ITEM HAZARDS
        if (ForgeConfigs.enableItemHazards) {
	        
	        // Apply Hazard Description
	        HazardSystem.ApplyHazardTooltip(stack, event.getEntity(), tooltipString);

        }
        
        // ===================
        // GEM ALERT!
  		if (ForgeConfigs.enableItemGems && (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES))) {
        	// Apply Gem Description
        	GemSystem.ApplyGemTooltip(stack, event.getEntity(), tooltipString);
    	}
  		
  		
    	// Finalize any changes. by converting & adding back onto tooltipString back to tooltipList components.
    	for (String line : tooltipString) {
        		tooltipList.add(Component.literal(line));
    	}
	}
	
}
