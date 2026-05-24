package xyz.nasasupercomputer.birmingham.Items.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.IHazardType;

import java.util.List;


/* TODO :
* - Literally everything
* - kill themaster
* - world domination
*
* */




public class attributeCurios extends Item implements ICurioItem {

    Attribute givenAttribute;
    float modifier; // probably multiplicative

    public attributeCurios(Properties pProperties, Attribute givenAttribute, float modifier) {
        super(pProperties);

        this.givenAttribute = givenAttribute;
        this.modifier = modifier;
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
    }


    // has to be false since its eatable as well
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }

}
