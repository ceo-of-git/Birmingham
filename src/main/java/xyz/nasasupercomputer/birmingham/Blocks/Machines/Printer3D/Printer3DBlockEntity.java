package xyz.nasasupercomputer.birmingham.Blocks.Machines.Printer3D;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.IDesktopType;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.TerminalBlock;
import xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers.TerminalBlockEntity;

public class Printer3DBlockEntity extends BlockEntity implements GeoBlockEntity, Container {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final SimpleContainer items = new SimpleContainer(5);
    IDesktopType supportingDesktop;
    private static int updateCheckTimer;
    public int progress = 0;
    public int maxProgress = 6000;

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", items.createTag());
        tag.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.fromTag(tag.getList("inventory", 10));
        progress = tag.getInt("progress");
    }

    public Printer3DBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockRegistry.PRINTER_3D_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    // Runs every tick, handles recipe progression and stuff.
    public static void tick(Level level, BlockPos pos, BlockState state, Printer3DBlockEntity blockEntity) {
        updateCheckTimer++;

        if (updateCheckTimer >= 20) {
            BlockEntity blockBelow = level.getBlockEntity(pos.below());

            if (blockBelow instanceof IDesktopType desktopBE) {
                // TODO: Add power check for Desktop

                if (!blockEntity.getBlockState().getValue(Printer3DBlock.ACTIVE)) {
                    level.setBlock(pos, state.setValue(Printer3DBlock.ACTIVE, true), Block.UPDATE_ALL);
                    blockEntity.supportingDesktop = desktopBE;
                }

            }
            else {
                level.setBlock(pos, state.setValue(Printer3DBlock.ACTIVE, false), Block.UPDATE_ALL);
                blockEntity.supportingDesktop = null;
            }


            updateCheckTimer = 0;
        }
        blockEntity.setChanged();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch(index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.HOLD_ON_LAST_FRAME));
        return PlayState.CONTINUE;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
    public ContainerData getData() {
        return data;
    }

    @Override public int getContainerSize() { return items.getContainerSize(); }
    @Override public boolean isEmpty() { return items.isEmpty(); }
    @Override public ItemStack getItem(int slot) { return items.getItem(slot); }
    @Override public ItemStack removeItem(int slot, int amount) { return items.removeItem(slot, amount); }
    @Override public ItemStack removeItemNoUpdate(int slot) { return items.removeItemNoUpdate(slot); }
    @Override public void setItem(int slot, ItemStack stack) { items.setItem(slot, stack); }
    @Override public boolean stillValid(Player player) { return true; }
    @Override public void clearContent() { items.clearContent(); }
}
