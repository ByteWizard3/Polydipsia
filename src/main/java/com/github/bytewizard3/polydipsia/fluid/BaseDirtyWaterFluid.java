package com.github.bytewizard3.polydipsia.fluid;

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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Supplier;

public abstract class BaseDirtyWaterFluid extends FlowingFluid {
    protected final Supplier<FluidType> fluidType;

    public BaseDirtyWaterFluid(Supplier<FluidType> fluidType) {
        this.fluidType = fluidType;
    }

    @Override
    public FluidType getFluidType() {
        return fluidType.get();
    }

    @Override
    public Fluid getSource() {
        return ModFluids.DIRTY_WATER.get();
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_DIRTY_WATER.get();
    }

    @Override
    public Item getBucket() {
        return null; // or register your custom dirty water bucket if needed
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction dir) {
        return false;
    }

    @Override
    public int getTickDelay(LevelReader world) {
        return 5; // Reasonable delay for fluid tick updates
    }

    @Override
    protected float getExplosionResistance() {
        return 100f;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ModFluids.DIRTY_WATER_BLOCK.get().defaultBlockState().setValue(FlowingFluid.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected boolean canConvertToSource(Level world) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        // No special behavior
    }

    @Override
    protected int getSlopeFindDistance(LevelReader world) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader world) {
        return 1;
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        builder.add(LEVEL); // required for flowing fluid state
    }
}
