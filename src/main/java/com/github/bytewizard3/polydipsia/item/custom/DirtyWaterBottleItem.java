package com.github.bytewizard3.polydipsia.item.custom;

import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DirtyWaterBottleItem extends BottleItem {
    public DirtyWaterBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        System.out.println("Using DirtyWaterBottleItem");

        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hit.getType() != HitResult.Type.BLOCK) {
            System.out.println("Hit type is not a block: " + hit.getType());
            return InteractionResultHolder.pass(stack);
        }

        BlockPos pos = hit.getBlockPos();
        System.out.println("Hit block position: " + pos);

        if (!level.mayInteract(player, pos)) {
            System.out.println("Player cannot interact with block at position: " + pos);
            return InteractionResultHolder.pass(stack);
        }

        var fluid = level.getFluidState(pos).getType();
        System.out.println("Fluid at position: " + fluid);

        if (fluid == ModFluids.SOURCE_DIRTY_WATER.get()) {
            System.out.println("Matched SOURCE_DIRTY_WATER");
        } else if (fluid == ModFluids.FLOWING_DIRTY_WATER.get()) {
            System.out.println("Matched FLOWING_DIRTY_WATER");
        } else {
            System.out.println("Fluid is not dirty water");
        }

        if (fluid == ModFluids.SOURCE_DIRTY_WATER.get() || fluid == ModFluids.FLOWING_DIRTY_WATER.get()) {
            System.out.println("Filling Dirty Water Bottle");

            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

            ItemStack result = new ItemStack(ModItems.DIRTY_WATER_BOTTLE.get());

            return InteractionResultHolder.sidedSuccess(
                    ItemUtils.createFilledResult(stack, player, result),
                    level.isClientSide()
            );
        }

        return InteractionResultHolder.pass(stack);
    }

}
