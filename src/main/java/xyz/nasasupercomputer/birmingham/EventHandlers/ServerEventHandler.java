package xyz.nasasupercomputer.birmingham.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.birmingham.ForgeConfigs;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemGems.GemRegistry;
import xyz.nasasupercomputer.birmingham.ItemGems.GemSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.Items.custom.Gem;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;

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
	
	@SubscribeEvent
	public static void onEntityHit(LivingDamageEvent event){
		DamageSource dmgSrc = event.getSource();
		
		LivingEntity victim = event.getEntity();
		Entity sourceEntity = dmgSrc.getEntity();
		
		if (sourceEntity instanceof LivingEntity attacker){
			ItemStack item = attacker.getMainHandItem();
			
			if (item.hasTag()){
				CompoundTag tag = item.getTag();
				
				if (tag.contains("GemList")){
					//float damage=event.getAmount();
					event = GemSystem.ApplyGemEffect(item, attacker, victim, event);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack left=event.getLeft();
		ItemStack right=event.getRight();
		
		
		if (ForgeConfigs.enableItemGems) {
			
			// If the right item is any kind of gem.
			if (event.getRight().getItem() instanceof Gem) {
				
				String gemTag = Gem.getTag(event.getRight().getItem());
				
				if ((left.is(ItemTags.SWORDS) || left.is(ItemTags.AXES))) {
					ItemStack output=left.copy();
					CompoundTag tag=output.getOrCreateTag();
					ListTag list=new ListTag();
					list=tag.getList("GemList", Tag.TAG_STRING);
					//if (tag.contains("GemList",Tag.TAG_COMPOUND)){
						//list=tag.getList("GemList",Tag.TAG_COMPOUND);
						//for (Tag s: gem){
						//	list.add(StringTag.valueOf(s.toString()));
						//}
					//}
					//System.out.println("DEBUG: "+list.size());
					boolean has=false;
					for (int i=0;i<list.size();i++) {
						if (list.getString(i).equals(gemTag)) {
							has=true;
							break;
						}
					}
					if (has==false) {
						list.add(StringTag.valueOf(gemTag));
						tag.put("GemList", list);
						output.setTag(tag);
						event.setOutput(output);
						event.setCost(15);
						event.setMaterialCost(1);
					}
				}
			}
		}
	}
}
