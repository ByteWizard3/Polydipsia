package com.github.bytewizard3.polydipsia.event;


import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack held = event.getItemStack();

        if (!level.isClientSide && held.getItem() == Items.GLASS_BOTTLE) {
            BlockState clickedBlock = level.getBlockState(pos);

            if (clickedBlock.getBlock() == ModFluids.DIRTY_WATER_BLOCK.get()) {
                consumeBottleAndGive(player, held, new ItemStack(ModItems.DIRTY_WATER_BOTTLE.get()));
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }

            // Add more water types here if needed:
            // if (clickedBlock.getBlock() == ModFluids.MUDDY_WATER_BLOCK.get()) { ... }
        }
    }

    private static void consumeBottleAndGive(Player player, ItemStack held, ItemStack newBottle) {
        held.shrink(1);
        if (!player.addItem(newBottle)) {
            player.drop(newBottle, false);
        }
    }
}