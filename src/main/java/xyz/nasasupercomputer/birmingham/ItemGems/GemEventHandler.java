package xyz.nasasupercomputer.birmingham.ItemGems;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mod.EventBusSubscriber(modid = MainRegistry.MOD_ID)
public class GemEventHandler {

	@SubscribeEvent
	public static void onEntityHit(LivingDamageEvent event){
		LivingEntity enemy = event.getEntity();
		LivingEntity attacker=(LivingEntity) event.getSource().getEntity();
		if (attacker != null){
			ItemStack item = attacker.getMainHandItem();
			if (item.hasTag()){
				CompoundTag tag = item.getTag();
				if (tag.contains("GemList")){
					float damage=event.getAmount();
					damage = GemSystem.ApplyGem(item,attacker,enemy,damage);
				}
			}
		}
	}
	

	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
    		ItemStack stack = event.getItemStack();
     		List<Component> tooltipList = event.getToolTip();
     		List<String> tooltipString = new ArrayList<String>();
     	   
      		if (true) { //No config installed yet
	        
	        	// Apply Gem Description
	        	GemSystem.ApplyGemTooltip(stack, event.getEntity(), tooltipString);
	        
	        	// Finalize any changes. by converting & adding back onto tooltipString back to tooltipList components.
	        	for (String line : tooltipString) {
	            		tooltipList.add(Component.literal(line));
	        	}
        	}
	}
}
