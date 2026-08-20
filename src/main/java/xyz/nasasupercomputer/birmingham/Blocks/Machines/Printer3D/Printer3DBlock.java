package xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.CokingOven.CokingOvenBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.Desktops.Office.DesktopBlockEntity;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.TerminalBlockEntity;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenMenu;
import xyz.nasasupercomputer.birmingham.Inventories.CokingOvenScreen;
import xyz.nasasupercomputer.birmingham.Inventories.Computers.TerminalScreen;
import xyz.nasasupercomputer.birmingham.Inventories.Printer3DMenu;
import xyz.nasasupercomputer.birmingham.Inventories.Printer3DScreen;

public class Printer3DBlock extends BaseEntityBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public Printer3DBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ACTIVE, false));
    }

    // Right-Click Use
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockState(pos).getValue(ACTIVE)) {
            BlockEntity blockBelow = level.getBlockEntity(pos.below());
            // Open GUI since Active
            // add power check soon? not sure

            if (blockBelow instanceof DesktopBlockEntity desktopBE) {
                // TODO: Add power check for Desktop

                if (!level.isClientSide()) {
                    if (player instanceof ServerPlayer playuh) {
                        NetworkHooks.openScreen(playuh, Printer3DBlock.createMenuProvider(pos), pos);
                    }
                }
            }
        }


        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        // Drop GUI Items when broken
        if (pLevel.getBlockEntity(pPos) instanceof Printer3DBlockEntity printerBE) {
            Containers.dropContents(pLevel, pPos, printerBE);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    // Stuff for opening the menu
    public static MenuProvider createMenuProvider(BlockPos pos) {
        return new SimpleMenuProvider((id, inv, player) -> new Printer3DMenu(id, inv, pos), Component.empty());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, BlockRegistry.PRINTER_3D_ENTITY.get(), Printer3DBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new Printer3DBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state){
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    // Create the block state
    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> builder) {
        builder.add(FACING);
        builder.add(ACTIVE);
    }

    // Check which state (Direction) to face the block when placed
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = defaultBlockState().setValue(FACING, facing).setValue(ACTIVE, false);

        return state;
    }
}
