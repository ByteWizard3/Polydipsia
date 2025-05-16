package com.github.bytewizard3.polydipsia.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class BlazingPytheriumBlock extends LiquidBlock {
    public BlazingPytheriumBlock(Supplier<? extends FlowingFluid> fluidSupplier, Properties properties) {
        super(fluidSupplier, properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (!level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) return;

        // Try to set nearby blocks on fire
        for (int i = 0; i < 3; ++i) {
            BlockPos targetPos = pos.offset(
                random.nextInt(3) - 1,
                1,
                random.nextInt(3) - 1
            );

            if (level.isEmptyBlock(targetPos)) {
                for (Direction direction : Direction.values()) {
                    if (direction == Direction.UP) continue; // Only want to check block below
                    BlockState neighbor = level.getBlockState(targetPos.relative(direction));
                    if (neighbor.isFlammable(level, targetPos.relative(direction), direction.getOpposite())) {
                        level.setBlockAndUpdate(targetPos, Blocks.FIRE.defaultBlockState());
                        break;
                    }
                }
            }
        }
    }
}
