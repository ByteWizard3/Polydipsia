package com.github.bytewizard3.polydipsia.item.custom;

import com.github.bytewizard3.polydipsia.util.WaterPropertyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WaterAnalyzerItem extends Item {
        public WaterAnalyzerItem(Properties pProperties) {
                super(pProperties);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
                net.minecraft.world.phys.BlockHitResult hitResult = getPlayerPOVHitResult(level, player,
                                ClipContext.Fluid.SOURCE_ONLY);
                if (hitResult.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
                        return InteractionResultHolder.pass(player.getItemInHand(hand));
                }

                if (hitResult.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
                        BlockPos pos = hitResult.getBlockPos();
                        BlockState state = level.getBlockState(pos);

                        if (!level.isClientSide()) {
                                if (state.is(Blocks.WATER)) {
                                        WaterPropertyUtil.WaterProperties props = WaterPropertyUtil.getProperties(level,
                                                        pos);
                                        player.sendSystemMessage(Component.literal("--- Water Analysis ---")
                                                        .withStyle(ChatFormatting.AQUA));
                                        player.sendSystemMessage(
                                                        Component.literal("Saltiness: " + props.saltiness + "%")
                                                                        .withStyle(ChatFormatting.WHITE));
                                        player.sendSystemMessage(
                                                        Component.literal("Muddiness: " + props.muddiness + "%")
                                                                        .withStyle(ChatFormatting.DARK_RED));
                                        player.sendSystemMessage(
                                                        Component.literal("Pollution: " + props.pollution + "%")
                                                                        .withStyle(ChatFormatting.DARK_GREEN));

                                        // Damage the tool
                                        player.getItemInHand(hand).hurtAndBreak(1, player,
                                                        p -> p.broadcastBreakEvent(hand));
                                } else {
                                        player.sendSystemMessage(Component.literal("Target is not Water!")
                                                        .withStyle(ChatFormatting.RED));
                                }
                        }
                        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
                }

                return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                        TooltipFlag pIsAdvanced) {
                pTooltipComponents
                                .add(Component.translatable("tooltip.polydipsia.water_analyzer")
                                                .withStyle(ChatFormatting.GRAY));
                super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
}
