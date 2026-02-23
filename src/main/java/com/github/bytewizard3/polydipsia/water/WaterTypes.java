package com.github.bytewizard3.polydipsia.water;

import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.Map;

public class WaterTypes {
    private static final Map<Item, WaterProperties> ITEM_PROPERTIES = new HashMap<>();

    public static void register(Item item, WaterProperties properties) {
        ITEM_PROPERTIES.put(item, properties);
    }

    public static WaterProperties getProperties(Item item) {
        return ITEM_PROPERTIES.get(item);
    }

    public static boolean isWaterItem(Item item) {
        return ITEM_PROPERTIES.containsKey(item);
    }
}
