package com.github.bytewizard3.polydipsia.recipes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PolydipsiaMod.MODID);

    public static final RegistryObject<RecipeSerializer<DamagedItemRecipe>> DAMAGED_ITEM =
            SERIALIZERS.register("damaged_item", DamagedItemRecipeSerializer::new);
}
