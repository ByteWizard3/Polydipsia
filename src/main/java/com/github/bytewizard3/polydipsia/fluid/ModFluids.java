package com.github.bytewizard3.polydipsia.fluid;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.block.DirtyWaterBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PolydipsiaMod.MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, PolydipsiaMod.MODID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PolydipsiaMod.MODID);

    public static final RegistryObject<FluidType> DIRTY_WATER_TYPE =
            FLUID_TYPES.register("dirty_water_type", DirtyWaterFluidType::new);

    public static final RegistryObject<FlowingFluid> DIRTY_WATER =
            FLUIDS.register("dirty_water", () -> new DirtyWaterFluid(DIRTY_WATER_TYPE));

    public static final RegistryObject<FlowingFluid> FLOWING_DIRTY_WATER =
            FLUIDS.register("flowing_dirty_water", () -> new FlowingDirtyWaterFluid(DIRTY_WATER_TYPE));

    public static final RegistryObject<LiquidBlock> DIRTY_WATER_BLOCK =
            BLOCKS.register("dirty_water_block", () ->
                    new DirtyWaterBlock(
                            DIRTY_WATER.get(),
                            BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()
                    ));

}
