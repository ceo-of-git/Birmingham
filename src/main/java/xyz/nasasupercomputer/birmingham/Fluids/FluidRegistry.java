package xyz.nasasupercomputer.birmingham.Fluids;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class FluidRegistry {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MainRegistry.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MainRegistry.MOD_ID);

    public static final FluidRegistryContainer EXAMPLE_FLUID = new FluidRegistryContainer(
            "example_fluid",
            FluidType.Properties.create().viscosity(1),
            () -> FluidRegistryContainer.createExtension(
            new FluidRegistryContainer.ClientExtensions(MainRegistry.MOD_ID, "example_fluid").
            fogColor(0.0f, 1.0f, 0.0f)),
            BlockBehaviour.Properties.copy(Blocks.WATER),
            new Item.Properties().stacksTo(1));
}
