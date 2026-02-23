package com.github.bytewizard3.polydipsia.fluid;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.fluidtypes.ModFluidTypes;
import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class ModFluids {

        // Register
        public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

        // WaterSources and Flowing
        public static final RegistryObject<FlowingFluid> SOURCE_DIRTY_WATER = FLUIDS.register("dirty_water",
                        () -> new ForgeFlowingFluid.Source(ModFluids.DIRTY_WATER_FLUID_PROPERTIES));
        public static final RegistryObject<FlowingFluid> FLOWING_DIRTY_WATER = FLUIDS.register("flowing_dirty_water",
                        () -> new ForgeFlowingFluid.Flowing(ModFluids.DIRTY_WATER_FLUID_PROPERTIES));

        // Properties
        public static final ForgeFlowingFluid.Properties DIRTY_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
                        ModFluidTypes.DIRTY_WATER_TYPE, // FluidType
                        SOURCE_DIRTY_WATER,
                        FLOWING_DIRTY_WATER)
                        .block(ModBlocks.DIRTY_WATER_BLOCK)
                        .bucket(ModItems.DIRTY_WATER_BUCKET);

}
