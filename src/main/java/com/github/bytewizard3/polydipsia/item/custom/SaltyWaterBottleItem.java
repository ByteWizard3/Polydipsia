package com.github.bytewizard3.polydipsia.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.water.WaterProperties;

public class SaltyWaterBottleItem extends BottleItem {
    public SaltyWaterBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player) pEntityLiving : null;
        if (player instanceof Player && !pLevel.isClientSide()) {
            ThirstHandler.handleDrink(player, WaterProperties.SALTY);
        }

        if (player != null) {
            player.awardStat(net.minecraft.stats.Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
        }

        if (pStack.isEmpty()) {
            return new ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE);
        } else {
            if (player != null && !player.getAbilities().instabuild) {
                ItemStack glassBottle = new ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE);
                if (!player.getInventory().add(glassBottle)) {
                    player.drop(glassBottle, false);
                }
            }
            return pStack;
        }
    }
}
