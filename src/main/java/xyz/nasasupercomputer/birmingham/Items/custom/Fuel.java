package xyz.nasasupercomputer.birmingham.Items.custom;

import javax.annotation.Nullable;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class Fuel extends Item {

	private int burnTime;
	
	public Fuel(Properties pProperties, int burnTime) {
		super(pProperties);
		setBurnTme(burnTime);
	}

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }
    
    private void setBurnTme(int newBurnTime) {
    	this.burnTime = newBurnTime;
    }
}
