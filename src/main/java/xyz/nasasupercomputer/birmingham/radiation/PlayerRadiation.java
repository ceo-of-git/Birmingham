package xyz.nasasupercomputer.birmingham.radiation;

import net.minecraft.nbt.CompoundTag;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

// this is probably the wrong place to puyt this file but i do not care

public class PlayerRadiation  {

    private double radiation;
    private boolean hasSeenFirstWarn; // radiation warnings
    private boolean hasSeenSecondWarn; // radiation warnings
    private boolean hasSeenThirdWarn; // radiation warnings
    private boolean hasSeenLastWarn; // radiation warnings

    public ArrayList<Double> last20Radiation = new ArrayList<>();


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
    
    public boolean getWarn(int warn) {
        switch (warn) {
            case 1:
                return this.hasSeenFirstWarn;
            case 2:
                return this.hasSeenSecondWarn;
            case 3:
                return this.hasSeenThirdWarn;
            case 4:
                return this.hasSeenLastWarn;
            default:
                return false;
        }

        }
    public ArrayList<Double> getList(){
        return this.last20Radiation;
    }

    public void setList(ArrayList<Double> listToSet) {
        this.last20Radiation = listToSet;
    }

    public void setWarn(int warnNumber, boolean setTo) {
        switch (warnNumber) {
            case 1:
                this.hasSeenFirstWarn = setTo;
                break;
            case 2:
                this.hasSeenSecondWarn = setTo;
                break;
            case 3:
                this.hasSeenThirdWarn = setTo;
                break;
            case 4:
                this.hasSeenLastWarn = setTo;
                break;
            default:
                break;


        }
    }



    //copied from google ai ovberview
    // Save data to NBT
    public void saveNBTData(CompoundTag nbt) {
        nbt.putDouble("radiation", this.radiation);
        nbt.putBoolean("hasSeenFirstWarn", this.hasSeenFirstWarn);
        nbt.putBoolean("hasSeenSecondWarn", this.hasSeenSecondWarn);
        nbt.putBoolean("hasSeenThirdWarn", this.hasSeenThirdWarn);
        nbt.putBoolean("hasSeenLastWarn", this.hasSeenLastWarn);
    }

    // Load data from NBT
    public void loadNBTData(CompoundTag nbt) {
        this.radiation = nbt.getDouble("radiation");
        this.hasSeenFirstWarn = nbt.getBoolean("hasSeenFirstWarn");
        this.hasSeenSecondWarn = nbt.getBoolean("hasSeenSecondWarn");
        this.hasSeenThirdWarn = nbt.getBoolean("hasSeenThirdWarn");
        this.hasSeenLastWarn = nbt.getBoolean("hasSeenLastWarn");
    }

}
