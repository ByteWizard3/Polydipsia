package com.github.bytewizard3.polydipsia.heat;

public class PlayerHeat {
    private int heat = 0;

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public void addHeat(int amount) {
        this.heat += amount;
    }

    public void copyFrom(PlayerHeat source) {
        this.heat = source.heat;
    }
}
