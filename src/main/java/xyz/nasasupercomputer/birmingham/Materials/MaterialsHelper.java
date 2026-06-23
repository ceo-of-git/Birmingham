package xyz.nasasupercomputer.birmingham.Materials;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.ItemGroups.ItemGroupRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.HazardSystem;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardRadioactive;
import xyz.nasasupercomputer.birmingham.MainRegistry;

import java.util.ArrayList;
import java.util.List;

public class MaterialsHelper {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainRegistry.MOD_ID);
    private static final List<RegisteredMaterial> ALL = new ArrayList<>();

    private static final List<Runnable> PENDING_HAZARDS = new ArrayList<>(); // shit so it doesnt try to get the registryo bject before it actually exist so radiation doesnt crash


    public static RegisteredMaterial register(MaterialProperties prop) { // registers or som shit
        MaterialProperties actualprop;
        if (prop.radioactivity() != 0 && prop.irradiationProperties().shouldIrradiate()) {
            actualprop = prop.changeToRadioactive();
        } else {
            actualprop = prop;
        }

        var ingot = ITEMS.register(actualprop.name() + "_ingot", () -> actualprop.ingotFactory().apply(new Item.Properties()));

        if (actualprop.radioactivity() != 0) {
            PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(ingot.get(), new HazardRadioactive(actualprop.radioactivity())));
        }

        RegistryObject<Item> nugget;
        if (actualprop.hasNugget()) {
            nugget = ITEMS.register(actualprop.name() + "_nugget", () -> actualprop.nuggetFactory().apply(new Item.Properties()));

            if (actualprop.radioactivity() != 0) {
                PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(nugget.get(), new HazardRadioactive(actualprop.radioactivity() / 9)));
            }
        } else {
            nugget = null;
        }
        RegistryObject<Block> block;
        RegistryObject<BlockItem> blockItem;

        if (actualprop.hasBlock()) {
            block = BLOCKS.register(actualprop.name() + "_block", () -> actualprop.blockFactory().apply(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .mapColor(actualprop.blockColor())
                    .destroyTime(actualprop.blockHardness())
                    .explosionResistance(actualprop.blockResistance())
                    .requiresCorrectToolForDrops()
            ));

            blockItem = ITEMS.register(actualprop.name() + "_block", () -> new BlockItem(block.get(), new Item.Properties()));

            if (actualprop.radioactivity() != 0) {
                PENDING_HAZARDS.add(() -> HazardSystem.RegisterHazard(blockItem.get(), new HazardRadioactive(actualprop.radioactivity() * 9)));
            }


        } else {
            blockItem = null; // idk why but intellij was BITCHING about it so i just clicked wahjtever it said to do
            block = null;
        }

        var mat = new RegisteredMaterial(actualprop, ingot, nugget, block, blockItem);
        ALL.add(mat);
        return mat;
    }
    public static List<RegisteredMaterial> all() { return ALL; } // ZERO clue what it does


    public static void bindHazards() { // actually add hjazards to the items (Fuck you forge)
        PENDING_HAZARDS.forEach(Runnable::run);
        PENDING_HAZARDS.clear();
    }

}
