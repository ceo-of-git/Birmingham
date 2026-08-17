package xyz.nasasupercomputer.birmingham.Items.custom;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Datagen.SavedData.PillData;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

// Random Generation
//int texture = random.nextInt(14);
//
//int effect = pillData.data.get("pill_" + texture);
//
//tag.putInt("pill_texture", texture);
//tag.putInt("pill_effect", effect);


public class PillItem extends Item {

	public static final String PILL_TYPE_KEY = "pill_data";
	
	public PillItem(Properties pProperties) {
		super(pProperties.stacksTo(1).food(new FoodProperties.Builder().nutrition(3).saturationMod(1).fast().alwaysEat().build()));
		Random rng = new Random();
	}
	
//	public static CompoundTag getData(ItemStack itemStack) {
//	    return itemStack.getOrCreateTagElement(PILL_TYPE_KEY);
//	}
	
	public static int getPillType(ItemStack stack) {
	    return stack.getOrCreateTag().getInt(PILL_TYPE_KEY);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
		
		// Just incase
		// TODO: Pill Effects
		if (pStack.getItem() instanceof PillItem pill && pLivingEntity instanceof ServerPlayer player) {
			
			switch(getPillType(pStack)) {
				case 0: // One makes you float (levitation + blindness)
					player.sendSystemMessage(Component.translatable("pill.birmingham.0.chat").withStyle(ChatFormatting.ITALIC));
					player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 5));
					player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
					break;
					
				case 1:
					player.sendSystemMessage(Component.translatable("pill.birmingham.1.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 2: // FULL HEAL
					player.sendSystemMessage(Component.translatable("pill.birmingham.2.chat").withStyle(ChatFormatting.ITALIC));
					player.setHealth(player.getMaxHealth());
					break;
					
				case 3: // FULL KILL
					player.sendSystemMessage(Component.translatable("pill.birmingham.3.chat").withStyle(ChatFormatting.ITALIC));
					player.kill();
					break;
					
				case 4: // One makes you RAGE
					player.sendSystemMessage(Component.translatable("pill.birmingham.4.chat").withStyle(ChatFormatting.ITALIC));
					player.setSecondsOnFire(30);
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2));
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
					player.setHealth(player.getMaxHealth() / 3);
					break;
					
				case 5: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.5.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 6: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.6.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 7: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.7.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 8: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.8.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 9: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.9.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 10: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.10.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 11: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.11.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 12: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.12.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				case 13: // 
					player.sendSystemMessage(Component.translatable("pill.birmingham.13.chat").withStyle(ChatFormatting.ITALIC));
					break;
					
				default:
					// One makes you error
					MainRegistry.LOGGER.error("Illegal Pilltype Consumed!? (hacker?)");
					player.sendSystemMessage(Component.literal(String.valueOf(getPillType(pStack))).withStyle(ChatFormatting.RED));
					break;
			}
		}
		
		return this.isEdible() ? pLivingEntity.eat(pLevel, pStack) : pStack;
	}
	
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    	pTooltipComponents.add(Component.translatable("tooltip.birmingham.mysterious_pill").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
	
    // Spawn in the pill item (for chests or whatever idk.)
    public static ItemStack createPill(PillData pillData) {

        ItemStack pill = new ItemStack(ItemRegistry.MYSTERIOUS_PILL.get());
        CompoundTag tag = pill.getOrCreateTag();

        Random random = new Random();
        int textureID = random.nextInt(PillData.EXISTING_PILL_EFFECTS);

        int effectID = pillData.data.get("pill_" + textureID);

        tag.putInt("pill_texture", textureID);
        tag.putInt("pill_effect", effectID);

        return pill;
    }

    
}
