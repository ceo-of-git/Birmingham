package xyz.nasasupercomputer.birmingham.Items.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class DescriptiveBlockItem extends BlockItem {

	private final String description;
	private final Boolean isLocalized;
	private final ChatFormatting style;
	
	public DescriptiveBlockItem(Block block, Properties pProperties, String description, boolean isLocalized, ChatFormatting style) {
		super(block, pProperties);
		
		this.description = description;
		this.isLocalized = isLocalized;
		
		if (style == null) {
			this.style = ChatFormatting.WHITE;
		} else { this.style = style; }
		
	}

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		if (isLocalized) {
			pTooltipComponents.add(Component.translatable(description).withStyle(style));
		} else {
			pTooltipComponents.add(Component.literal(description).withStyle(style));
		}
    }
}
