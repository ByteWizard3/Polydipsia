package com.github.bytewizard3.polydipsia.events;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.github.bytewizard3.polydipsia.util.WaterPropertyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PolydipsiaMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandlers {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack itemStack = event.getItemStack();

        // Ensure we are using a glass bottle
        if (itemStack.is(Items.GLASS_BOTTLE)) {
            // Perform a raytrace to see what we are looking at
            HitResult hitResult = player.pick(5.0D, 0.0F, true);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos pos = blockHitResult.getBlockPos();
                BlockState state = level.getBlockState(pos);

                if (state.is(Blocks.WATER)) {
                    event.setCanceled(true); // Cancel normal vanilla behavior

                    if (!level.isClientSide) {
                        // Calculate procedural properties
                        WaterPropertyUtil.WaterProperties props = WaterPropertyUtil.getProperties(level, pos);

                        // Give output item
                        Item outputBottle = props.isCold ? ModItems.COLD_WATER_BOTTLE.get()
                                : ModItems.DIRTY_WATER_BOTTLE.get();
                        ItemStack resultItem = new ItemStack(outputBottle);

                        // Set NBT data
                        CompoundTag tag = resultItem.getOrCreateTag();
                        tag.putInt("Saltiness", props.saltiness);
                        tag.putInt("Muddiness", props.muddiness);
                        tag.putInt("Pollution", props.pollution);

                        // Deduct glass bottle
                        if (!player.isCreative()) {
                            itemStack.shrink(1);
                        }

                        if (itemStack.isEmpty()) {
                            player.setItemInHand(event.getHand(), resultItem);
                        } else if (!player.getInventory().add(resultItem)) {
                            player.drop(resultItem, false);
                        }

                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL,
                                SoundSource.NEUTRAL, 1.0F, 1.0F);
                    }
                }
            }
        }
    }
}
