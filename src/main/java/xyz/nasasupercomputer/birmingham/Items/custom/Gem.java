package xyz.nasasupercomputer.birmingham.Items.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class Gem extends Item {

	public String gemTag;
	String gemTitle;
	String gemDescription;
	
	public Gem(Properties pProperties, String gemTag, String translatableGemTitle, String translatableShiftGemDescription) {
		super(pProperties);
		
		this.gemTag = gemTag;
		this.gemTitle = translatableGemTitle;
		this.gemDescription = translatableShiftGemDescription;
	}
	
	public static String getTag(Item item) {
		
		if (item instanceof Gem) {
			return ((Gem) item).gemTag;
		}
		return "null";
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(Component.translatable(gemTitle).withStyle(ChatFormatting.YELLOW));
			pTooltipComponents.add(Component.translatable(gemDescription).withStyle(ChatFormatting.GRAY));
		}
		else {
			pTooltipComponents.add(Component.translatable(gemTitle).withStyle(ChatFormatting.YELLOW));
			pTooltipComponents.add(Component.translatable("gems.birmingham.shift_to_view").withStyle(ChatFormatting.DARK_GRAY));
		}
	}

}
