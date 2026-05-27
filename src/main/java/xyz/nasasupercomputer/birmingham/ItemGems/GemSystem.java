package xyz.nasasupercomputer.birmingham.ItemGems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class GemSystem {
	
	
	// Returns a list of every Gem that the ItemStack may have.
	public static List<IGemType> GetGemsFromItemStack(ItemStack stack){
		if (stack.isEmpty()) {
			return List.of();
		}
		
		CompoundTag tag = stack.getTag();
		if (tag == null) { return List.of(); }

		ArrayList<IGemType> gems = new ArrayList<IGemType>();
		if (tag.contains("GemList")){
			
			ListTag gem=tag.getList("GemList",Tag.TAG_STRING);
			
			for (int i=0;i<gem.size();i++) {
				gems.add(GemRegistry.gemList.get(gem.getString(i)));
			}
			//for (Tag s: gem){
				//gems.add(GemRegistry.gemList.get(s.toString()));
			//}
		}
		
		return gems;
	}
	
	// Edits the description of an item to add the gem tooltip
	public static void ApplyGemTooltip(ItemStack stack, Player player, List<String> description) {
		if (GetGemsFromItemStack(stack) == null) return;
		
		for (IGemType gems : GetGemsFromItemStack(stack)) {
			gems.AddGemTooltip(player, description, stack);
		}
		

	}
	
	public static LivingDamageEvent ApplyGemEffectDamage(ItemStack stack, LivingEntity attacker, LivingEntity enemy, LivingDamageEvent event) {
		if (stack.isEmpty()) return event;
		
		List<IGemType> itemGems = GetGemsFromItemStack(stack);
		
		for (IGemType gem : itemGems) {
			event=gem.RegisterDamage(attacker, enemy, stack, event);
		}
		
		return event;
	}
	public static LivingDamageEvent ApplyGemEffectTaken(ItemStack stack, LivingEntity enemy, LivingDamageEvent event) {
		if (stack.isEmpty()) return event;
		
		List<IGemType> itemGems = GetGemsFromItemStack(stack);
		
		for (IGemType gem : itemGems) {
			event=gem.RegisterTaken(enemy, stack, event);
		}
		
		return event;
	}
}
