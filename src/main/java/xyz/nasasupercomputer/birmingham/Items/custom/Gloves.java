package xyz.nasasupercomputer.birmingham.Items.custom;


import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;
import xyz.nasasupercomputer.birmingham.Sound.SoundRegistry;

public class Gloves extends Item implements ICurioItem {

	public double protectionValue;
	public List<IHazardType> protectionBlacklist;
	// The CurioAPI works through tags, to see how Gloves fit in the "Hands" slot, look at.
	// /Birmingham/src/main/resources/data/curios/tags/items/hands.json
	
	// The protectionValue stat determines every single hazard that the gloves protect from depending on their intensity.
	// The protectionBlacklist list determines which hazards this glove does NOT protect from, despite its level of intensity.
	public Gloves(Properties properties, double protectionValue, List<IHazardType> protectionBlacklist) {
		super(properties);
		
		this.protectionBlacklist = protectionBlacklist;
		this.protectionValue = protectionValue;
	}
	
	// Appends a custom description to the item.
    @Override
    public void appendHoverText( ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag ) {
    	tooltip.add(Component.translatable("tooltip.birmingham.gloves"));
    	tooltip.add(Component.translatable("tooltip.birmingham.protects_from"));
    	
    	int hazardsProtectedFrom = 0;
    	
		for (IHazardType hazard : HazardRegistry.HazardVariantsList) {
			if (hazard.GetIntensity() <= this.protectionValue) {
				if (protectionBlacklist != null) {
					if (protectionBlacklist.contains(hazard)) {
						// Protection Blacklist isn't null, and contains this type of hazard.
						// Thus, skip this & don't protect a damn thing!
						continue;
					}
					else {
						// Protection blacklist isn't null, and doesn't contain.
						// Continue (A.K.A don't protect from this hazard type)
					}
				}

				// This glove must protect from that effect.
				hazardsProtectedFrom++;
				tooltip.add(Component.literal("§e- ").append(Component.translatable(hazard.GetTranslatableTitle()).withStyle(ChatFormatting.YELLOW)));
			}
		}
		
		if (hazardsProtectedFrom == 0) {
			tooltip.add(Component.literal("§c- ").append(Component.translatable("tooltip.birmingham.protects_nothing")).withStyle(ChatFormatting.RED));
		}
        super.appendHoverText(stack, level, tooltip, flag);
    }
  
    // When "Used" on something, (right-clicked)
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
    	
    	BlockPos clickedBlockPosition = pContext.getClickedPos();
    	
    	pContext.getLevel().playSeededSound(null, clickedBlockPosition.getX(), clickedBlockPosition.getY(), clickedBlockPosition.getZ(), SoundRegistry.HORSING_AROUND_TEST.get(), SoundSource.PLAYERS, 1f, 1f, 0);
    	// .playSeededSound() args
    	// 1 - Ignored Players (pass null if you want EVERYONE in a radius to hear.)
    	// 2 - X
    	// 3 - Y
    	// 4 - Z
    	// 5 - The SOUND itself from Sound Registry. (make sure it a part in sounds.json)
    	// 6 - Which "Category" of sound it is.
    	// 7 - Volume,
    	// 8 - Pitch,
    	// 9 - Seed (You can probably ignore this)
    	
        return InteractionResult.SUCCESS;
     }
	
	
	// ================
	// CURIOS COMPATABILITY
	

//	 // Called every tick this glove is in the Curio Slot
//	@Override
//	public void curioTick(SlotContext slotContext, ItemStack stack) {
//		
//	}
	
//	// Called when this Curio is Equipped
//	@Override
//	public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
//
//	}
	
//	// Called when this Curio is Unequipped
//	@Override
//	default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
//
//	}

	// Determines whether or not you can just right-click to equip this.
	// Default for this mod should be True because I like it
	@Override
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
		return false; // temp for testing
	}
}
