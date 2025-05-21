package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.fluidtypes.ModFluidTypes;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
        if (event.getLevel().isClientSide()) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;


        BlockHitResult hitResult = getPlayerPOVHitResult(event.getLevel(), event.getEntity(), ClipContext.Fluid.SOURCE_ONLY);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = event.getLevel().getBlockState(pos);
            Block block = state.getBlock();
            ItemStack heldItem = event.getEntity().getMainHandItem();

            String blockName = block.builtInRegistryHolder().key().location().toString();
            String itemName = heldItem.isEmpty() ? "empty" : heldItem.getItem().builtInRegistryHolder().key().location().toString();

            log.info("Right click hit: Block={} Item={}", blockName, itemName);

            // Example: if it's water
            if (state.getFluidState().isSource() && state.getFluidState().getType() == ModFluids.SOURCE_DIRTY_WATER.get()) {
                log.info("Player right-clicked a water source block");
                ThirstHandler.dirtyWaterRightClick(event); // still call your handler
            }
            if (state.getFluidState().isSource()) {
                log.info("Fluid Clicked");
            }
        }
    }


    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().getItem() == ModItems.CAMELPACK_ITEM.get()) {
            event.getCrafting().setDamageValue(event.getCrafting().getMaxDamage());
        }
    }
    public static BlockHitResult getPlayerPOVHitResult(Level level, Player player, ClipContext.Fluid fluidMode) {
        double reach = player.getAttribute(ForgeMod.BLOCK_REACH.get()).getValue();
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = eye.add(look.scale(reach));

        return level.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, fluidMode, player));
    }


}
