package xyz.nasasupercomputer.birmingham.Items.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.logging.LogUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.slf4j.Logger;
import org.w3c.dom.Attr;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

// okl im just straight up not doing it this way, im sure 1 for every item will be fiiiiiiiiiiine

/* 


* - Literally everything
* - kill themaster
* - world domination
*
* */




public class energyDrink extends Item implements ICurioItem {
    public static final Logger LOGGER = LogUtils.getLogger();

    float level;

    public energyDrink(Properties pProperties, float level) {
        super(pProperties);

        this.level = level;
    }

    // Appends a custom description to the item. than ks coe fore code
//    @Override
//    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag ) {
//        tooltip.add(Component.translatable("tooltip.birmingham.gloves"));
//        tooltip.add(Component.translatable("tooltip.birmingham.protects_from"));
//
//        super.appendHoverText(stack, level, tooltip, flag);
//    }
    // cant be bothered to do that rn, but ill do it later (n o i wont)



    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // ticking logic here
//        LivingEntity player = slotContext.entity();
//        AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
//        AttributeModifier modifier = new AttributeModifier( // ignore my ass formatting
//            modifierUUID, // still dont know why this is needed
//          "speed", // internal name (?)
//        0.5 * level, //amount
//        AttributeModifier.Operation.MULTIPLY_TOTAL // self-explanatory
//        );
//        if (attribute != null && !attribute.hasModifier(modifier)) {
//            attribute.addPermanentModifier(modifier);
//        }

        }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create(); // code ENTIRELY stolen, i do not understnad a bit of it
        map.put(
                Attributes.MOVEMENT_SPEED,
                new AttributeModifier(uuid, "speed", 0.5 * level, AttributeModifier.Operation.MULTIPLY_TOTAL)
        );
        return map;

    }

    // has to be false since its eatable as well
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }


    // thanks google overview
    // Changes the holding animation to the drinking animation (like a potion)
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    // Overrides the default eating sound to the generic drinking sound
    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    // Overrides the sound that plays when the drinking finishes
    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }


}
