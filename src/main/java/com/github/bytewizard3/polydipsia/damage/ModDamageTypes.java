package com.github.bytewizard3.polydipsia.damage;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {

    ResourceKey<DamageType> DEHYDRATION = register("dehydration");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(PolydipsiaMod.MODID, name));
    }

}
