package xyz.nasasupercomputer.birmingham.radiation;

// i essnetially just went off the code from googles ai overvierw thing because there is ZERO chance i ujnddeerstand anyh of tihs

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerRadiationProvider implements ICapabilitySerializable<CompoundTag> { // wtf is ICapabilitySerializable<CompoundTag>
    public static Capability<PlayerRadiation> PLAYER_RADIATION = CapabilityManager.get(new CapabilityToken<>(){}); // capabilities sutff , ZERO clue on any of this

    private PlayerRadiation radiation = null;

    private final LazyOptional<PlayerRadiation> optional = LazyOptional.of(this::createPlayerRadiation); // the FUCK does this mean. or do. or anything.

    private PlayerRadiation createPlayerRadiation() { // think i understand this. if from the start the radiation hasnt been initialzied yetr (still null) we just set it to type of playerradiation., or smth. im not good at java
        if(this.radiation == null) {
            this.radiation = new PlayerRadiation();
        }
        return this.radiation;
    }



    @Override // more stolen code i don't understand. awesome.
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == PLAYER_RADIATION) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() { // kiknd of ujnderstand but not really
        CompoundTag nbt = new CompoundTag();
        createPlayerRadiation().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) { // same here
        createPlayerRadiation().loadNBTData(nbt);
    }
}
