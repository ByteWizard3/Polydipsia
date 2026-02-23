package com.github.bytewizard3.polydipsia.water;

public record WaterProperties(int hydrationLevel, float pollution, float salinity) {
    public static final WaterProperties DIRTY = new WaterProperties(1, 0.8f, 0.0f);
    public static final WaterProperties SALTY = new WaterProperties(-2, 0.0f, 1.0f);
    public static final WaterProperties PURIFIED = new WaterProperties(6, 0.0f, 0.0f);
    public static final WaterProperties NORMAL = new WaterProperties(3, 0.1f, 0.0f);
}
