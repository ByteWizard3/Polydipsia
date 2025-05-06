package com.github.bytewizard3.polydipsia.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidType.Properties;

public class DirtyWaterFluidType extends FluidType {
    public DirtyWaterFluidType() {
        super(Properties.create()
            .density(1000)
            .viscosity(1000)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
        );
    }
}
