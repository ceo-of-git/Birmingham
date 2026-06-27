package xyz.nasasupercomputer.birmingham.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.telemetry.TelemetryProperty.GameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
import xyz.nasasupercomputer.birmingham.Radiation.PlayerRadiationProvider;
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

		// radiation warnings & effects
		player.getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(playerRadiation -> {
			long currentTick = player.level().getGameTime();
			double rads = playerRadiation.getRadiation();
			
			if (player.gameMode.getGameModeForPlayer() == GameType.CREATIVE) { rads = 0.0; }
			
			// TODO: Localize

			if (rads > 200 && !playerRadiation.getWarn(1)) {
				playerRadiation.setWarn(1, true);
				player.sendSystemMessage(Component.literal("You feel ill..").withStyle(ChatFormatting.RED), true);
			}
			if (rads > 500 && !playerRadiation.getWarn(2)) {
				playerRadiation.setWarn(2, true);
				player.sendSystemMessage(Component.literal("You feel greatly ill..").withStyle(ChatFormatting.RED), true);
			}
			if (rads > 800 && !playerRadiation.getWarn(3)) {
				playerRadiation.setWarn(3, true);
				player.sendSystemMessage(Component.literal("You feel as if you are dying..").withStyle(ChatFormatting.RED), true);
			}
			if (rads > 1200 && !playerRadiation.getWarn(4)) {
				playerRadiation.setWarn(4, true);
				player.sendSystemMessage(Component.literal("You are going to die.").withStyle(ChatFormatting.DARK_RED), true);
			}
			
			// slash kill
			if (rads > 1500) {
				playerRadiation.setWarn(4, true);
				if (player.gameMode.getGameModeForPlayer() != GameType.CREATIVE) { player.kill(); }
			}

			// Apply Radiation effects when not in creative mode
			if (player.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
				
				if (rads > 200) { // effects for 200 rads
					if (currentTick % 360 == 0) { // every 360 ticks (18 seconds), apply weakness and nausea for random seconds with a min of 60 ticks
						player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) Math.max(60, (Math.random() * 200) + 1), 1));
						player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) Math.max(200, (Math.random() * 400) + 1), 1)); // ?? why tf is it called confusion.
	
						if (Math.random() > (3.0/5) ) {
							player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) Math.max(120, (Math.random() * 300) + 1), 1)); // add slowness, but only sokmetimes
						}
						if (Math.random() > (4.0/5) ) {
							player.addEffect(new MobEffectInstance(MobEffects.POISON, (int) Math.max(40, (Math.random() * 100) + 1), 0)); // add poision but also only somethimes
						}
	
	
					}
				}
				if (rads > 500) { // effects for 500 rads
					if (currentTick % 280 == 0) {
						player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) Math.max(140, (Math.random() * 280) + 1), 3));
						player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) Math.max(200, (Math.random() * 400) + 1), 4)); // ?? why tf is it called confusion.
	
						if (Math.random() > (1.0/5) ) {
							player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) Math.max(160, (Math.random() * 300) + 1), 2)); // add slowness, but only sokmetimes
						}
						if (Math.random() > (3.0/5) ) {
							player.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) Math.max(60, (Math.random() * 100) + 1), 2)); // add poision but also only somethimes
							player.addEffect(new MobEffectInstance(MobEffects.POISON, (int) Math.max(40, (Math.random() * 100) + 1), 0)); // add poision but also only somethimes
	
						}
	
	
					}
				}
				if (rads > 800) { // effects for 800 rads
					player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1, 2));
					player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1, 4)); // ?? why tf is it called confusion.
	
					if (currentTick % 100 == 0) {
	
						if (Math.random() > (1.0/5) ) {
							player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) Math.max(40, (Math.random() * 100) + 1), 3)); // add slowness, but only sokmetimes
						}
						if (Math.random() > (1.0/5) ) {
							player.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) Math.max(60, (Math.random() * 100) + 1), 4)); // add poision but also only somethimes
							player.addEffect(new MobEffectInstance(MobEffects.POISON, (int) Math.max(40, (Math.random() * 100) + 1), 2)); // add poision but also only somethimes
	
						}
	
	
					}
				}
				if (rads > 1200) { // yeah you're cooked buddy
	                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 5));
	                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 5)); // ?? why tf is it called confusion.
	                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3));
	                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 5));
	                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 5));
					player.addEffect(new MobEffectInstance(MobEffects.POISON, 50, 10)); // add poision but also only somethimes
	
	
	
				}
			}

			ArrayList<Double> list = playerRadiation.getList();
			list.add(rads);

			if (list.size() > 20) {
				list.remove(0);
			}
			playerRadiation.setList(list);
//			double average = list.stream()
//					.mapToDouble(Double::doubleValue)
//					.average()
//					.orElse(0.0); // Returns 0.0 if list is empty


		});





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
	public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			if (!event.getObject().getCapability(PlayerRadiationProvider.PLAYER_RADIATION).isPresent()) {
				event.addCapability(ResourceLocation.fromNamespaceAndPath(MainRegistry.MOD_ID, "radiation"), new PlayerRadiationProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		// Check if the clone was caused by dying (rather than returning from the End)
		if (!event.isWasDeath()) {
			event.getOriginal().reviveCaps();
			// Retrieve the dead player's data
			event.getOriginal().getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(oldCap -> {

				// Retrieve the new player's capability instance
				event.getEntity().getCapability(PlayerRadiationProvider.PLAYER_RADIATION).ifPresent(newCap -> {

					// Copy the data over
					newCap.setRadiation(oldCap.getRadiation());
					newCap.setWarn(1, oldCap.getWarn(1));
					newCap.setWarn(2, oldCap.getWarn(2));
					newCap.setWarn(3, oldCap.getWarn(3));
					newCap.setWarn(4, oldCap.getWarn(4));
				});
				event.getOriginal().invalidateCaps();
			});
		}
	}


	@SubscribeEvent
	public static void onEntityHit(LivingDamageEvent event){
		DamageSource dmgSrc = event.getSource();
		
		LivingEntity victim = event.getEntity();
		Entity sourceEntity = dmgSrc.getEntity();
		
		if (sourceEntity instanceof LivingEntity attacker){
			ArrayList<ItemStack> stuff=new ArrayList<ItemStack>();
			stuff.add(attacker.getMainHandItem());
			for (ItemStack i : attacker.getArmorSlots()) {
				stuff.add(i);
			}
			//has not been implemented into offhand yet for concerns players will use gems in offhand for benefits; needs to be coded different
			//gems currently do not have valid slots, so i cannot implement this yet.
			
			for (ItemStack item : stuff) {
				if (item.hasTag()){
					CompoundTag tag = item.getTag();
					
					if (tag.contains("GemList")){
						//float damage=event.getAmount();
						event = GemSystem.ApplyGemEffectDamage(item, attacker, victim, event);
					}
				}
			}
		}
		ArrayList<ItemStack> stuff=new ArrayList<ItemStack>();
		stuff.add(victim.getMainHandItem());
		for (ItemStack i : victim.getArmorSlots()) {
			stuff.add(i);
		}
		
		for (ItemStack item : stuff) {
			if (item.hasTag()){
				CompoundTag tag = item.getTag();
				
				if (tag.contains("GemList")){
					//float damage=event.getAmount();
					event = GemSystem.ApplyGemEffectTaken(item, victim, event);
					//damage doesnt always have a source, so it doesnt have an attacker. gems can reach them using
					//the event's parameters
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
