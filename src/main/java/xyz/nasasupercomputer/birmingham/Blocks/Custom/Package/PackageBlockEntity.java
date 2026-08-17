package xyz.nasasupercomputer.birmingham.Blocks.Custom.Package;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nasasupercomputer.birmingham.Blocks.BlockRegistry;

import java.util.ArrayList;
import java.util.List;

public class PackageBlockEntity extends BlockEntity {

    private final List<ItemStack> packageItems = new ArrayList<>();

    public PackageBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockRegistry.PACKAGE_ENTITY.get(), pPos, pBlockState);
    }

    public void setPackageItems(List<ItemStack> items) {
        this.packageItems.clear();
        this.packageItems.addAll(items);
        setChanged();
    }

    public List<ItemStack> getPackageItems(){
        return packageItems;
    }
}
