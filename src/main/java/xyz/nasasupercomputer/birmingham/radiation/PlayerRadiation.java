package xyz.nasasupercomputer.birmingham.radiation;

import net.minecraft.nbt.CompoundTag;

import java.math.BigDecimal;
import java.math.RoundingMode;

// this is probably the wrong place to puyt this file but i do not care

public class PlayerRadiation  {

    private double radiation;


    public double getRadiation() {
        double rounded = new BigDecimal(Double.toString(this.radiation)).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return rounded;
    }
    public void setRadiation(double amount) {
        this.radiation = amount;
    }
    public void addRadiation(double amount) {
        this.radiation = this.radiation + amount;
    }



    //copied from google ai ovberview
    // Save data to NBT
    public void saveNBTData(CompoundTag nbt) {
        nbt.putDouble("radiation", this.radiation);
    }

    // Load data from NBT
    public void loadNBTData(CompoundTag nbt) {
        this.radiation = nbt.getInt("radiation");
    }

}
