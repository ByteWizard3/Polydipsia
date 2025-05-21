package com.github.bytewizard3.polydipsia.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.Random;
import java.util.function.Supplier;

public class SpicyLavaBlock extends LiquidBlock {
    public SpicyLavaBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties); // Enable random ticks
    }
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true; // just to be explicit
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        System.out.println("RandomTick happened and spreading fire");
        for (int i = 0; i < 3; ++i) {
            BlockPos firePos = pos.offset(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
            BlockState below = level.getBlockState(firePos.below());

            if (below.isSolid() && level.getBlockState(firePos).isAir()) {
                if (level.getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_DOFIRETICK)) {
                    level.setBlock(firePos, Blocks.FIRE.defaultBlockState(), 11);
                }
            }
        }
    }
    @Override
    public void entityInside(BlockState state, net.minecraft.world.level.Level level, BlockPos pos, net.minecraft.world.entity.Entity entity) {
        if (!entity.fireImmune()) {
            entity.setSecondsOnFire(8);

            if (entity.hurt(level.damageSources().lava(), 4.0F)) {
                // Optionally do extra stuff
            }
        }

        super.entityInside(state, level, pos, entity);
    }
}
