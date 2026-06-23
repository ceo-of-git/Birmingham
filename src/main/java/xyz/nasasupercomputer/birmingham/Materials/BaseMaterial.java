package xyz.nasasupercomputer.birmingham.Materials;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

public class BaseMaterial extends Item {
    public final RegistryObject<Item> ingot;

    public BaseMaterial(Properties pProperties, RegistryObject<Item> ingot) {
        super(pProperties);
        this.ingot = ItemRegistry.ITEMS.register("ingot", () -> new Item(new Item.Properties().stacksTo(64)));
    }

}
