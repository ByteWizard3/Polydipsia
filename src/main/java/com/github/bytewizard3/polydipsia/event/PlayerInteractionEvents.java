package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.fluidtypes.ModFluidTypes;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class PlayerInteractionEvents {
    private static final Logger log = LogUtils.getLogger();

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide())
            return;
        if (event.getHand() != InteractionHand.MAIN_HAND)
            return;
        Level level = event.getLevel();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        ItemStack heldItem = event.getEntity().getMainHandItem();
        String itemName = heldItem.isEmpty() ? "empty"
                : heldItem.getItem().builtInRegistryHolder().key().location().toString();

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = event.getLevel().getBlockState(pos);
            Block block = state.getBlock();
            String blockName = block.builtInRegistryHolder().key().location().toString();
            Fluid fluid = level.getFluidState(pos).getType();
            log.info("Right click hit: Block={} Item={}", blockName, itemName);

            if (state.getFluidState().isSource()) {
                log.info("Fluid Clicked");
            }

            // Example: if it's water
            if (fluid == ModFluids.SOURCE_DIRTY_WATER.get() || fluid == ModFluids.FLOWING_DIRTY_WATER.get()) {
                log.info("Player right-clicked a water source block");
                level.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                ItemStack result = new ItemStack(ModItems.DIRTY_WATER_BOTTLE.get());
                ItemStack filled = ItemUtils.createFilledResult(heldItem, player, result);

                player.setItemInHand(hand, filled); // Set the new item

                event.setCanceled(true); // Cancel the rest of the interaction
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));

                ThirstHandler.dirtyWaterRightClick(event); // still call your handler
            } else if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
                if (itemName.equals("minecraft:glass_bottle")) {
                    boolean isOcean = level.getBiome(pos).is(BiomeTags.IS_OCEAN);
                    if (isOcean) {
                        log.info("Player right-clicked ocean water");
                        level.playSound(player, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                        ItemStack result = new ItemStack(ModItems.SALTY_WATER_BOTTLE.get());
                        ItemStack filled = ItemUtils.createFilledResult(heldItem, player, result);

                        player.setItemInHand(hand, filled);
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().getItem() == ModItems.CAMELPACK_ITEM.get()) {
            event.getCrafting().setDamageValue(event.getCrafting().getMaxDamage());
        }
    }

    protected static BlockHitResult getPlayerPOVHitResult(Level pLevel, Player pPlayer, ClipContext.Fluid pFluidMode) {
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = pPlayer.getBlockReach();
        Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
    }

}
