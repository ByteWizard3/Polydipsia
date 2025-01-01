package com.github.bytewizard3.polydipsia.Thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst {
    private double thirst;
    private final double MIN_THIRST = 0;
    private final double MAX_THIRST = 100;

    public double getThirst() {
        return thirst;
    }

    public void addThirst(double add) {
        this.thirst = Math.min(thirst + add, MAX_THIRST);
    }

    public void subThirst(double sub) {
        this.thirst = Math.max(thirst - sub, MIN_THIRST);
    }

    public void copyFrom(PlayerThirst source) {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putDouble("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt) {
        thirst = nbt.getDouble("thirst");
    }
}