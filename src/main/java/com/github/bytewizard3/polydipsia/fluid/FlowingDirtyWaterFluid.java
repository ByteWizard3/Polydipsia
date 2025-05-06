package com.github.bytewizard3.polydipsia.fluid;


import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Supplier;

public class FlowingDirtyWaterFluid extends BaseDirtyWaterFluid {


    public FlowingDirtyWaterFluid(Supplier<FluidType> fluidType) {
        super(fluidType);
    }

    @Override
    public Item getBucket() {
        return ModItems.DIRTY_WATER_BUCKET.get();
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    public int getAmount(FluidState state) {
        return state.getValue(LEVEL);
    }
}