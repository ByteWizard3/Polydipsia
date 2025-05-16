package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.capabilities.heat.HeatHandler;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class PlayerInteractionEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ThirstHandler.dirtyWaterRightClick(event);
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().getItem() == ModItems.CAMELPACK_ITEM.get()) {
            event.getCrafting().setDamageValue(event.getCrafting().getMaxDamage());
        }
    }


}
