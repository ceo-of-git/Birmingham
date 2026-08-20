package xyz.nasasupercomputer.birmingham;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

// Thank you Kaupengoe
public class ModTagRegistry {

    public static class Items {
        public static final TagKey<Item> PLASTIC_FILAMENT = createTag("plastic_filament");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(new ResourceLocation(MainRegistry.MOD_ID, name));
        }
    }

    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(new ResourceLocation(MainRegistry.MOD_ID, name));
        }
    }
}
