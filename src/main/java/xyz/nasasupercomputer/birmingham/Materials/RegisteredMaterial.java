package xyz.nasasupercomputer.birmingham.Materials;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public record RegisteredMaterial(
        MaterialProperties props,
        RegistryObject<Item> ingot,
        RegistryObject<Item> nugget,        // null if props.hasNugget() == false
        RegistryObject<Block> block,       // null if props.hasBlock()  == false
        RegistryObject<BlockItem> blockItem // null if no block
) {}
