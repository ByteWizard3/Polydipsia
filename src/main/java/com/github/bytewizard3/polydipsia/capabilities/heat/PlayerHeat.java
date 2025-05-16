package com.github.bytewizard3.polydipsia.capabilities.heat;

public class PlayerHeat {
    private float heat = 0;

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }

    public void addHeat(float amount) {
        this.heat += amount;
    }

    public void copyFrom(PlayerHeat source) {
        this.heat = source.heat;
    }

    public void setInitialHeat() {
        heat=37.5f;
    }
}
