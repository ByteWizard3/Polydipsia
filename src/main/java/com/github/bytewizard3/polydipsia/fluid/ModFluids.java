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


    //Register
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    //WaterSources and Flowing
    public static final RegistryObject<FlowingFluid> SOURCE_SOAP_WATER = FLUIDS.register("soap_water_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.SOAP_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SOAP_WATER = FLUIDS.register("flowing_soap_water",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.SOAP_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_DIRTY_WATER =
            FLUIDS.register("dirty_water", () -> new ForgeFlowingFluid.Source(ModFluids.DIRTY_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_DIRTY_WATER =
            FLUIDS.register("flowing_dirty_water", () ->  new ForgeFlowingFluid.Flowing(ModFluids.DIRTY_WATER_FLUID_PROPERTIES));
//    public static final RegistryObject<ForgeFlowingFluid> BLAZING_PYTHERIUM_SOURCE = FLUIDS.register("blazing_pytherium_still",
//            () -> new ForgeFlowingFluid.Source(ModFluids.PYTHERIUM_PROPERTIES));
//    public static final RegistryObject<ForgeFlowingFluid> BLAZING_PYTHERIUM_FLOWING = FLUIDS.register("blazing_pytherium_flowing",
//            () -> new ForgeFlowingFluid.Flowing(ModFluids.PYTHERIUM_PROPERTIES));
public static final RegistryObject<FlowingFluid> BLAZING_PYTHERIUM_SOURCE = FLUIDS.register("blazing_pytherium_still",
        BlazingPytherium.Source::new);
    public static final RegistryObject<FlowingFluid> BLAZING_PYTHERIUM_FLOWING = FLUIDS.register("blazing_pytherium_flowing",
            BlazingPytherium.Flowing::new);

    //Properties
    public static final ForgeFlowingFluid.Properties DIRTY_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.DIRTY_WATER_TYPE,  // FluidType
            SOURCE_DIRTY_WATER,
            FLOWING_DIRTY_WATER)
            .block(ModBlocks.DIRTY_WATER_BLOCK)
            .bucket(ModItems.DIRTY_WATER_BUCKET);
    public static final ForgeFlowingFluid.Properties SOAP_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.SOAP_WATER_FLUID_TYPE, SOURCE_SOAP_WATER, FLOWING_SOAP_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.SOAP_WATER_BLOCK)
            .bucket(ModItems.SOAP_WATER_BUCKET);
//    public static final ForgeFlowingFluid.Properties PYTHERIUM_PROPERTIES = new ForgeFlowingFluid.Properties(
//            ModFluidTypes.PYTHEREUM_WATER_FLUID_TYPE,
//            ModFluids.BLAZING_PYTHERIUM_SOURCE,
//            ModFluids.BLAZING_PYTHERIUM_FLOWING)
//            .block(ModBlocks.BLAZING_PYTHERIUM_BLOCK)
//            .bucket(ModItems.BLAZING_PYTHERIUM_BUCKET)
//            .temperature(1500)
//            .density(3000)
//            .viscosity(6000)
//            .luminosity(10);


}
