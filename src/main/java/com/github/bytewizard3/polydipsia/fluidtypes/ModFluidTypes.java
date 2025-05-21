package com.github.bytewizard3.polydipsia.fluidtypes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);
    public static final RegistryObject<FluidType> LAVA_SPICY_TYPE = FLUID_TYPES.register(
            "lava_spicy",
            FireLavaFluidType::new);
    public static final RegistryObject<FluidType> DIRTY_WATER_TYPE = FLUID_TYPES.register(
            "dirty_water",
            DirtyFluidType::new);



}
