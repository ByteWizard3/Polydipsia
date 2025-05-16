package com.github.bytewizard3.polydipsia.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class SpicyLavaBlock extends LiquidBlock {
    public SpicyLavaBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 3; ++i) {
            BlockPos firePos = pos.offset(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
            if (level.getBlockState(firePos.below()).isSolid() && level.getBlockState(firePos).isAir()) {
                level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 11);
            }
        }
    }
}
