package xyz.nasasupercomputer.birmingham.Materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public record MaterialSetRecord(
		String name,
		RegistryObject<Item> ingot,
		RegistryObject<Item> nugget,
		RegistryObject<Item> dust,
		RegistryObject<Item> plate,
		RegistryObject<Item> slag,
		RegistryObject<Item> blockItem,
		RegistryObject<Block> block
) {}