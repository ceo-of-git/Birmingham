package xyz.nasasupercomputer.birmingham.ItemGems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.CompoundTag;

public class GemSystem {
	
	// Returns a list of every Gem that the ItemStack may have.
	public static List<IGemType> GetGemsFromItemStack(ItemStack stack){
		if (stack.isEmpty()) {
			return List.of(); // Empty List
		}
		CompoundTag tag=item.getTag();
		
		List<IGemType> gems = new List<IGemType>();
		if (tag.contains("GemList")){
			ListTag gem=tag.getList("GemList",Tag.TAG_STRING);
			for (String s: gem){
				gems.add(s);
			}
		}
		
		return gems;
	}
	
	// Edits the description of an item to add hazards :)
	public static void ApplyGemTooltip(ItemStack stack, Player player, List<String> description) {
		if (GetGemsFromItemStack(stack) == null) return;
		
		for (IGemType hazards : GetHazardsFromItemStack(stack)) {
			hazards.AddGemTooltip(player, description, stack);
		}
	}
	
	// Gets all the hazards on an item, and does the "perTickUpdate" method on each of em.
	public static void ApplyGem(ItemStack stack, var attacker, var enemy, float damage) {
		if (stack.isEmpty()) return;
		
		List<IGemType> itemHazards = GetGemsFromItemStack(stack);
		for (IGemType hazard : itemHazards) {
			damage=hazard.RegisterDamage(attacker, enemy, stack, damage);
		}
	}
}
