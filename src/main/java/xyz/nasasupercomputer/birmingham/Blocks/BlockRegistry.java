package xyz.nasasupercomputer.birmingham.Blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace.AlloyBlastFurnaceBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.AlloyBlastFurnace.AlloyBlastFurnaceBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

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
		
	// =========================
	// ORIGINAL BLOCKS
	public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block> ADAMANTITE = BLOCKS.register("adamantite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DEEPSLATE_ADAMANTITE_ORE = BLOCKS.register("deepslate_adamantite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> ADAMANTITE_ORE = BLOCKS.register("adamantite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = BLOCKS.register("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).strength(6f).requiresCorrectToolForDrops().destroyTime(2.0f)));
	public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops().destroyTime(1.5f)));
	public static final RegistryObject<Block> RAW_TIN_BLOCK = BLOCKS.register("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(6f).requiresCorrectToolForDrops().destroyTime(2.0f)));
	
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

}