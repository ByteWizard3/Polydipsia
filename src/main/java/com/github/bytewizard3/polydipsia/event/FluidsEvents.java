package com.github.bytewizard3.polydipsia.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;
import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.fluid.ModFluidInteractionRegistrar;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.fluidtypes.ModFluidTypes;
import com.github.bytewizard3.polydipsia.recipes.ModRecipeSerializers;
import com.github.bytewizard3.polydipsia.tab.ModCreativeTabs;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Random;
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
