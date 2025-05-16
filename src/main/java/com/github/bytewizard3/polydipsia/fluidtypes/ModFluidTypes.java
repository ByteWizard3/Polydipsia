package com.github.bytewizard3.polydipsia.fluidtypes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PolydipsiaMod.MODID);
    public static final RegistryObject<FluidType> DIRTY_WATER_TYPE =
            FLUID_TYPES.register("dirty_water_type", DirtyWaterFluidType::new);
    public static final RegistryObject<FluidType> SOAP_WATER_FLUID_TYPE = FLUID_TYPES.register("soap_water_fluid", SoapFluidType::new);
    public static final RegistryObject<FluidType> PYTHEREUM_WATER_FLUID_TYPE = FLUID_TYPES.register("pytherium_fluid_type", PyhereumFluidType::new);

}
