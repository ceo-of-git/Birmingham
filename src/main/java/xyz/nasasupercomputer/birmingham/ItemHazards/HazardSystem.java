package xyz.nasasupercomputer.birmingham.ItemHazards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardToxic;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.Items.custom.Gloves;

public class HazardSystem {

	public static HashMap<Item, List<IHazardType>> ALL_HAZARDS = new HashMap<>();
	
	// Registers a Hazard that an item may have
	// ex: the test item being toxic to all non-creative mode players.
	public static void RegisterHazard(Item item, IHazardType hazard) {

	    if (!ALL_HAZARDS.containsKey(item)) {
	        ALL_HAZARDS.put(item, new ArrayList<>());
	    }

	    ALL_HAZARDS.get(item).add(hazard);
	}
	
	// Returns a list of every Hazard that the ItemStack may have.
	public static List<IHazardType> GetHazardsFromItemStack(ItemStack stack){
		if (stack.isEmpty()) {
			return List.of(); // Empty List
		}
		
		List<IHazardType> hazard = ALL_HAZARDS.get(stack.getItem());
		
		if (hazard == null) {
			return List.of();
		}
		
		return hazard;
	}
	
	// Edits the description of an item to add hazards :)
	public static void ApplyHazardTooltip(ItemStack stack, Player player, List<String> description) {
		if (GetHazardsFromItemStack(stack) == null) return;
		
		for (IHazardType hazards : GetHazardsFromItemStack(stack)) {
			hazards.AddHazardTooltip(player, description, stack);
		}
	}
	
	// Gets all the hazards on an item, and does the "perTickUpdate" method on each of em.
	public static void ApplyHazard(ItemStack stack, ServerPlayer player) {
		if (stack.isEmpty()) return;
		
		List<IHazardType> itemHazards = GetHazardsFromItemStack(stack);
		for (IHazardType hazard : itemHazards) {
			
			// CuriosApi.getCuriosHelper().findEquippedCurio(ItemRegistry.LEATHER_GLOVES.get(), player)
			// TODO: Add checking for curio worn gloves
			// TODO: Optimize glove checks if possible.
			ItemStack offhandStack = player.getOffhandItem();
			ItemStack mainhandStack = player.getMainHandItem();

			boolean offhandIsProtective = false;
			boolean mainhandIsProtective = false;
			
			// Check if offhand item is an instance of "Gloves"
			// Also check if the Offhand items protection value is good or not.
			if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof Gloves glove) {
			    offhandIsProtective = hazard.GetIntensity() <= glove.protectionValue;
			}
			
			// Check if offhand item is an instance of "Gloves"
			// Also check if the Mainhand items protection value is good or not.
			if (!mainhandStack.isEmpty() && offhandStack.getItem() instanceof Gloves glove) {
			    mainhandIsProtective = hazard.GetIntensity() <= glove.protectionValue;
			}
			
			if (offhandIsProtective || mainhandIsProtective) {
			    // Don't add effect.
			    continue;
			}

			hazard.PerTickUpdate(player, stack);
		}
	}
}
