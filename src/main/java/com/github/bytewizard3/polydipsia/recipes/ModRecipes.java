package com.github.bytewizard3.polydipsia.recipes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister
            .create(ForgeRegistries.RECIPE_SERIALIZERS, PolydipsiaMod.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES,
            PolydipsiaMod.MODID);

    public static final RegistryObject<RecipeSerializer<WaterPurifierRecipe>> WATER_PURIFYING_SERIALIZER = SERIALIZERS
            .register("water_purifying", () -> WaterPurifierRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<WaterPurifierRecipe>> WATER_PURIFYING_TYPE = TYPES
            .register("water_purifying", () -> WaterPurifierRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
