package xyz.nasasupercomputer.birmingham.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopChair;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.DesktopBlockEntityBase;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.IBigBlockType;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopProperties;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.IDesktopType;
import xyz.nasasupercomputer.birmingham.ItemGems.GemSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.Items.custom.PillItem;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
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
  		
        // ===================
        // Display Desktop Stats
        if (Block.byItem(stack.getItem()) instanceof IDesktopType desktopType) {

			// TODO: Localize
			if (!Screen.hasShiftDown()) {
				tooltipList.add(1, Component.literal("§8Hold §e<SHIFT> §8To view Computational Stats"));
			} else {
				DesktopProperties desktopProperties = desktopType.GetProperties();
				tooltipList.add(1, Component.literal("§2> Supports GUI: §a" + desktopProperties.hasGuiSupport() + "§r"));
				tooltipList.add(1, Component.literal("§2> Power Efficiency: §a" + desktopProperties.powerEfficiency() * 100 + "% §r"));
				tooltipList.add(1, Component.literal("§2> Compute Speed: §a" + desktopProperties.computeSpeed() * 100 + "% §r"));
				tooltipList.add(1, Component.literal("§2> Compute Power: §a" + desktopProperties.computePower() + "§r"));
				tooltipList.add(1, Component.literal("§aDesktop Stats:"));

			}

			tooltipList.add(1, Component.translatable("tooltip.birmingham.desktop").withStyle(ChatFormatting.DARK_GRAY));
		}

		// ===================
		// Gaming Chairs
		if (Block.byItem(stack.getItem()) instanceof DesktopChair chair) {

			// TODO: Localize
			tooltipList.add(1, Component.translatable("tooltip.birmingham.chair.dyeable").withStyle(ChatFormatting.DARK_GRAY));
			if (!Screen.hasShiftDown()) {
				tooltipList.add(1, Component.literal("§8Hold §e<SHIFT> §8To view Booster Stats"));
			} else {
				tooltipList.add(1, Component.literal("§2> Power Efficiency Boost: §a" + chair.getEfficiencyBoostPercent() * 100 + "% §r"));
				tooltipList.add(1, Component.literal("§2> Compute Speed Boost: §a" + chair.getSpeedBoostPercent() * 100 + "% §r"));
				tooltipList.add(1, Component.literal("§2> Compute Power Boost: §a" + chair.getPowerBoostPercent() * 100 + "% §r"));
				tooltipList.add(1, Component.literal("§aDesktop Stats:"));

			}

			tooltipList.add(1, Component.translatable("tooltip.birmingham.chair").withStyle(ChatFormatting.DARK_GRAY));
			tooltipList.add(1, Component.translatable("tooltip.birmingham.desktop.optional").withStyle(ChatFormatting.DARK_GRAY));
		}
  		
        // ===================
        // Easter Eggs
  		if (stack.is(ItemRegistry.RAW_TIN.get()) && Screen.hasShiftDown()) {
  			tooltipList.add(Component.literal("Say that again...").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
  		}
  		
  		// ===================
  		// Display pill effects if in creative
  		if (stack.getItem() instanceof PillItem pill) {
  			if (player != null && player.isCreative()) {
  				tooltipList.add(1, Component.literal("Note that pills gotten from creative have set textures/effects and AREN'T randomized.").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
  				// tooltipList.add(1, Component.literal("Effect #" + PillItem.getPillType(stack)).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
  			}
  		}
  		
  		
    	// Finalize any changes. by converting & adding back onto tooltipString back to tooltipList components.
    	for (String line : tooltipString) {
        		tooltipList.add(Component.literal(line));
    	}
	}

	
}
