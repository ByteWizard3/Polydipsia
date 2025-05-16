package com.github.bytewizard3.polydipsia.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class BlazingPytheriumBlock extends LiquidBlock {
    private static final Logger log = LogManager.getLogger();
    public BlazingPytheriumBlock(Supplier<? extends FlowingFluid> fluidSupplier, Properties properties) {
        super(fluidSupplier, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity living) {
            living.hurt(level.damageSources().lava(), 4.0F);
            living.setSecondsOnFire(15);
        }
    }


    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (!level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) return;

        trySetFire(level, pos, random);
    }
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (!level.isClientSide) {
            tryTransformWithWater((ServerLevel) level, state, pos);
        }
    }

    private void trySetFire(ServerLevel level, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 3; ++i) {
            BlockPos targetPos = pos.offset(
                    random.nextInt(3) - 1,
                    1,
                    random.nextInt(3) - 1
            );

            if (level.isEmptyBlock(targetPos)) {
                if (canSetFireNormally(level, targetPos)) {
                    level.setBlockAndUpdate(targetPos, Blocks.FIRE.defaultBlockState());
                    return; // Fire placed, stop early
                } else if (canForceSetFire(level, targetPos)) {
                    level.setBlockAndUpdate(targetPos, Blocks.FIRE.defaultBlockState());
                    return;
                }
            }
        }
    }

    private boolean canSetFireNormally(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP) continue;
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighbor = level.getBlockState(neighborPos);
            if (neighbor.isFlammable(level, neighborPos, direction.getOpposite())) {
                return true;
            }
        }
        return false;
    }

    private boolean canForceSetFire(ServerLevel level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState blockBelow = level.getBlockState(below);

        // Adjust this list of blocks as needed
        return blockBelow.is(Blocks.STONE) ||
                blockBelow.is(Blocks.COBBLESTONE) ||
                blockBelow.is(Blocks.NETHERRACK);
    }
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        tryTransformWithWater(level, state, pos);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            serverLevel.scheduleTick(pos, this, 1);
        }
    }

    private void tryTransformWithWater(ServerLevel level, BlockState state, BlockPos pos) {
        log.info("[BlazingPytherium] tryTransformWithWater called at {}", pos);

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            FluidState neighborFluid = level.getFluidState(neighborPos);

            log.info("[BlazingPytherium] Checking neighbor at {}: Fluid type = {}", neighborPos, neighborFluid.getType());

            if (neighborFluid.is(FluidTags.WATER)) {
                log.info("[BlazingPytherium] Water found at {}, current fluid level = {}", neighborPos, state.getValue(BlockStateProperties.LEVEL));

                if (state.getValue(BlockStateProperties.LEVEL) == 0) {
                    log.info("[BlazingPytherium] Transforming to OBSIDIAN at {}", pos);
                    level.setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
                } else {
                    log.info("[BlazingPytherium] Transforming to STONE at {}", pos);
                    level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
                }

                level.levelEvent(1501, pos, 0);
                return; // Exit after transformation
            }
        }

        log.info("[BlazingPytherium] No water found nearby to transform.");
    }

}
