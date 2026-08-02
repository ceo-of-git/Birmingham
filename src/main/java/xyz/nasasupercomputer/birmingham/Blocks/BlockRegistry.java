package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.PurificationChamber.PurificationChamberBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.PurificationChamber.PurificationChamberBlockEntity;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace.AlloyBlastFurnaceBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace.AlloyBlastFurnaceBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.DesktopBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.TerminalBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.TerminalBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.FuelGenerator.FuelGeneratorBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.FuelGenerator.FuelGeneratorBlockEntity;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;
import xyz.nasasupercomputer.birmingham.Items.custom.DescriptiveBlockItem;

public class BlockRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainRegistry.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MainRegistry.MOD_ID);
	
	// =========================
	// MACHINES
	public static final RegistryObject<Block> BIGBLOCK_PART = BLOCKS.register("bigblock_part", () -> new BigBlockPart(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().destroyTime(2.5f)));
	public static final RegistryObject<Block> COKING_OVEN = BLOCKS.register("coking_oven", () -> new CokingOvenBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).requiresCorrectToolForDrops().strength(3.0F, 12.0F).noOcclusion().destroyTime(2.5f)));
	public static final RegistryObject<BlockEntityType<CokingOvenBlockEntity>> COKING_OVEN_ENTITY = BLOCK_ENTITIES.register("coking_oven", () -> BlockEntityType.Builder.of(CokingOvenBlockEntity::new, COKING_OVEN.get()).build(null));
	public static final RegistryObject<Block> ALLOY_BLAST_FURNACE = BLOCKS.register("alloy_blast_furnace", () -> new AlloyBlastFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).requiresCorrectToolForDrops().strength(3.0F, 14.0F).noOcclusion().destroyTime(2.5f)));
	public static final RegistryObject<BlockEntityType<AlloyBlastFurnaceBlockEntity>> ALLOY_BLAST_FURNACE_ENTITY = BLOCK_ENTITIES.register("alloy_blast_furnace", () -> BlockEntityType.Builder.of(AlloyBlastFurnaceBlockEntity::new, ALLOY_BLAST_FURNACE.get()).build(null));
	public static final RegistryObject<Block> PURIFICATION_CHAMBER = BLOCKS.register("purification_chamber", () -> new PurificationChamberBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).requiresCorrectToolForDrops().strength(3.0F, 12.0F).noOcclusion()));
	public static final RegistryObject<BlockEntityType<PurificationChamberBlockEntity>> PURIFICATION_CHAMBER_ENTITY = BLOCK_ENTITIES.register("purification_chamber", () -> BlockEntityType.Builder.of(PurificationChamberBlockEntity::new, PURIFICATION_CHAMBER.get()).build(null));
	public static final RegistryObject<Block> FUEL_GENERATOR = BLOCKS.register("fuel_generator", () -> new FuelGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).requiresCorrectToolForDrops().strength(3.0F, 12.0F).noOcclusion().destroyTime(2.5f)));
	public static final RegistryObject<BlockEntityType<FuelGeneratorBlockEntity>> FUEL_GENERATOR_ENTITY = BLOCK_ENTITIES.register("fuel_generator", () -> BlockEntityType.Builder.of(FuelGeneratorBlockEntity::new, FUEL_GENERATOR.get()).build(null));

	// =========================
	// DESKTOP COMPONENTS
	public static final RegistryObject<Block> DESKTOP = BLOCKS.register("desktop", () -> new DesktopBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(3.0F, 12.0F).noOcclusion().destroyTime(2.5f)));
	public static final RegistryObject<BlockEntityType<DesktopBlockEntity>> DESKTOP_ENTITY = BLOCK_ENTITIES.register("desktop", () -> BlockEntityType.Builder.of(DesktopBlockEntity::new, DESKTOP.get()).build(null));
	public static final RegistryObject<Block> TERMINAL = BLOCKS.register("terminal", () -> new TerminalBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(3.0F, 12.0F).noOcclusion().destroyTime(2.5f)));
	public static final RegistryObject<BlockEntityType<TerminalBlockEntity>> TERMINAL_ENTITY = BLOCK_ENTITIES.register("terminal", () -> BlockEntityType.Builder.of(TerminalBlockEntity::new, TERMINAL.get()).build(null));
			
	
	// =========================
	// ORIGINAL BLOCKS
	public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block> ADAMANTITE = BLOCKS.register("adamantite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DEEPSLATE_ADAMANTITE_ORE = BLOCKS.register("deepslate_adamantite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> ADAMANTITE_ORE = BLOCKS.register("adamantite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(6f).requiresCorrectToolForDrops().destroyTime(2.0f)));
	public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops().destroyTime(1.5f)));
	public static final RegistryObject<Block> RAW_TIN_BLOCK = BLOCKS.register("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops().destroyTime(2.0f)));
	public static final RegistryObject<Block> TABLE = BLOCKS.register("table", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1f)));

	
	// =========================
	// BLOCK ITEMS
	public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ItemRegistry.ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> ADAMANTITE_ITEM = ItemRegistry.ITEMS.register("adamantite", () -> new BlockItem(ADAMANTITE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_ADAMANTITE_ITEM = ItemRegistry.ITEMS.register("deepslate_adamantite_ore", () -> new BlockItem(DEEPSLATE_ADAMANTITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> ORE_ADAMANTITE_ITEM = ItemRegistry.ITEMS.register("adamantite_ore", () -> new BlockItem(ADAMANTITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> COKING_OVEN_ITEM = ItemRegistry.ITEMS.register("coking_oven", () -> new BlockItem(COKING_OVEN.get(), new Item.Properties()));
	public static final RegistryObject<Item> ALLOY_BLAST_FURNACE_ITEM = ItemRegistry.ITEMS.register("alloy_blast_furnace", () -> new BlockItem(ALLOY_BLAST_FURNACE.get(), new Item.Properties()));
	public static final RegistryObject<Item> TIN_ORE_ITEM = ItemRegistry.ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_TIN_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_tin_ore", () -> new BlockItem(DEEPSLATE_TIN_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> RAW_TIN_BLOCK_ITEM = ItemRegistry.ITEMS.register("raw_tin_block", () -> new BlockItem(RAW_TIN_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> PURIFICATION_CHAMBER_ITEM = ItemRegistry.ITEMS.register("purification_chamber", () -> new BlockItem(PURIFICATION_CHAMBER.get(), new Item.Properties()));
	public static final RegistryObject<Item> FUEL_GENERATOR_ITEM = ItemRegistry.ITEMS.register("fuel_generator", () -> new BlockItem(FUEL_GENERATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> DESKTOP_ITEM = ItemRegistry.ITEMS.register("desktop", () -> new BlockItem(DESKTOP.get(), new Item.Properties()));
	public static final RegistryObject<Item> TERMINAL_ITEM = ItemRegistry.ITEMS.register("terminal", () -> new DescriptiveBlockItem(TERMINAL.get(), new Item.Properties(), "tooltip.birmingham.terminal", true, ChatFormatting.DARK_GRAY));
	public static final RegistryObject<Item> TABLE_ITEM = ItemRegistry.ITEMS.register("table", () -> new DescriptiveBlockItem(TABLE.get(), new Item.Properties(), "tooltip.birmingham.table", true, ChatFormatting.DARK_GRAY));

	
	// Helper Methods
    // Rotates a VoxelShape (code found online somewhere) (probably barely works) (not sure) (slightly modified)
	// This is used for blocks that have custom bounds and can also rotate, see the Desktop/Terminal blocks for an example
    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] shapeBuffer = new VoxelShape[] { shape, Shapes.empty() };
        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        for (int i = 0; i < times; i++) {
        	shapeBuffer[1] = Shapes.empty();

        	shapeBuffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> { shapeBuffer[1] = Shapes.or(shapeBuffer[1],
                Block.box(minZ * 16, minY * 16, (1 - maxX) * 16, maxZ * 16, maxY * 16, (1 - minX) * 16));
            });

        	shapeBuffer[0] = shapeBuffer[1];
        }
        
        // bug I found where east/west was flipped
        // so I flip it now
        if (to == Direction.EAST || to == Direction.WEST) {
        	shapeBuffer[1] = Shapes.empty();
        	
            shapeBuffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                shapeBuffer[1] = Shapes.or(shapeBuffer[1], Block.box( (1 - maxX) * 16, minY * 16, minZ * 16, (1 - minX) * 16, maxY * 16, maxZ * 16));
            });

            shapeBuffer[0] = shapeBuffer[1];
        }

        return shapeBuffer[0];
    }

}