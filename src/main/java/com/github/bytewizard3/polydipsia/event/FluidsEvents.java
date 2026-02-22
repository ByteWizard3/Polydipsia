package com.github.bytewizard3.polydipsia.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;
import com.github.bytewizard3.polydipsia.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@Mod.EventBusSubscriber(modid = MODID)
public class FluidsEvents {
    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        if (event.getState().getBlock() == ModBlocks.LAVA_SPICY_BLOCK.get()) {
            Level level = (Level) event.getLevel();
            if (!level.isClientSide) {
                BlockPos pos = event.getPos();
                spreadFireNearby((ServerLevel) level, pos);
            }
        }
    }

    private static void spreadFireNearby(ServerLevel level, BlockPos lavaPos) {
        for (BlockPos offset : BlockPos.betweenClosed(lavaPos.offset(-1, -1, -1), lavaPos.offset(1, 1, 1))) {
            if (offset.equals(lavaPos)) continue;

            BlockState target = level.getBlockState(offset);
            if (target.isAir() || target.getBlock() == Blocks.FIRE) {
                if (level.random.nextFloat() < 0.3f) {
                    level.setBlock(offset, Blocks.FIRE.defaultBlockState(), 11);
                }
            }
        }
    }

}
