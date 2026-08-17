package xyz.nasasupercomputer.birmingham.Capabilities;

// Aug 17 cleaned up vinxinty slop code

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class PlayerRadiationProvider implements ICapabilitySerializable<CompoundTag> {
    public static Capability<PlayerRadiation> PLAYER_RADIATION = CapabilityManager.get(new CapabilityToken<>(){});

    private PlayerRadiation radiation = null;
    private final LazyOptional<PlayerRadiation> optional = LazyOptional.of(this::createPlayerRadiation);

    private PlayerRadiation createPlayerRadiation() {
        if (this.radiation == null) {
            this.radiation = new PlayerRadiation();
            MainRegistry.LOGGER.info("Player radiation created");
        }
        return this.radiation;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == PLAYER_RADIATION) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerRadiation().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) { // same here
        createPlayerRadiation().loadNBTData(nbt);
    }
}
