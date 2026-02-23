package com.github.bytewizard3.polydipsia.item.custom;

import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.water.WaterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
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

        // If they are sneaking or something else that bypasses filling?
        // We actually want them to drink it, so we override finishUsingItem.
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32; // Standard drinking time
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        // Drink the item
        Player player = pEntityLiving instanceof Player ? (Player) pEntityLiving : null;
        if (player instanceof Player && !pLevel.isClientSide()) {
            ThirstHandler.handleDrink(player, WaterProperties.DIRTY);
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

    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel,
            java.util.List<net.minecraft.network.chat.Component> pTooltipComponents,
            net.minecraft.world.item.TooltipFlag pIsAdvanced) {
        if (pStack.hasTag()) {
            net.minecraft.nbt.CompoundTag tag = pStack.getTag();
            if (tag.contains("Saltiness")) {
                pTooltipComponents
                        .add(net.minecraft.network.chat.Component.literal("Saltiness: " + tag.getInt("Saltiness") + "%")
                                .withStyle(net.minecraft.ChatFormatting.WHITE));
            }
            if (tag.contains("Muddiness")) {
                pTooltipComponents
                        .add(net.minecraft.network.chat.Component.literal("Muddiness: " + tag.getInt("Muddiness") + "%")
                                .withStyle(net.minecraft.ChatFormatting.DARK_RED));
            }
            if (tag.contains("Pollution")) {
                pTooltipComponents
                        .add(net.minecraft.network.chat.Component.literal("Pollution: " + tag.getInt("Pollution") + "%")
                                .withStyle(net.minecraft.ChatFormatting.DARK_GREEN));
            }
        } else {
            pTooltipComponents.add(net.minecraft.network.chat.Component.literal("Unknown Properties")
                    .withStyle(net.minecraft.ChatFormatting.GRAY));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
