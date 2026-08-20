package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;

public class DesktopChair extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty DYE_COLOR = IntegerProperty.create("dye_color", 1, 3); // 1 - red, 2 - green, 3 - blu
    public static final BooleanProperty RENDER_ACTIVE = BooleanProperty.create("render_active");

    private double powerBoostPercent;
    private double speedBoostPercent;
    private double efficiencyBoostPercent;

    public DesktopChair(Properties properties, double powerBoostPercent, double speedBoostPercent, double efficiencyBoostPercent) {
        super(properties);

        this.powerBoostPercent = powerBoostPercent;
        this.speedBoostPercent = speedBoostPercent;
        this.efficiencyBoostPercent = efficiencyBoostPercent;

        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    // Right-Click Use
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        // Dye Chair
        if (player.getItemInHand(hand).is(Tags.Items.DYES_RED)) {
            level.setBlock(pos, state.setValue(DYE_COLOR, 1), Block.UPDATE_ALL);
            level.playSound(player, pos, SoundEvents.GRASS_STEP, SoundSource.BLOCKS, 1.0f, 2.0f);
        }
        else if (player.getItemInHand(hand).is(Tags.Items.DYES_GREEN)) {
            level.setBlock(pos, state.setValue(DYE_COLOR, 2), Block.UPDATE_ALL);
            level.playSound(player, pos, SoundEvents.GRASS_STEP, SoundSource.BLOCKS, 1.0f, 2.0f);
        }
        else if (player.getItemInHand(hand).is(Tags.Items.DYES_BLUE)) {
            level.setBlock(pos, state.setValue(DYE_COLOR, 3), Block.UPDATE_ALL);
            level.playSound(player, pos, SoundEvents.GRASS_STEP, SoundSource.BLOCKS, 1.0f, 2.0f);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FACING, DYE_COLOR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = defaultBlockState().setValue(FACING, facing).setValue(DYE_COLOR, 1);

        return state;
    }

    public double getPowerBoostPercent() { return Math.round(this.powerBoostPercent * 100.0) / 100.0; }
    public double getSpeedBoostPercent() { return Math.round(this.speedBoostPercent * 100.0) / 100.0; }
    public double getEfficiencyBoostPercent() { return Math.round(this.efficiencyBoostPercent * 100.0) / 100.0; }
}
