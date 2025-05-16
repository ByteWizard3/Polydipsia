package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.capabilities.heat.HeatHandler;
import com.github.bytewizard3.polydipsia.capabilities.heat.PlayerHeat;
import com.github.bytewizard3.polydipsia.capabilities.heat.PlayerHeatProvider;
import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirst;
import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirstProvider;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.damage.ModDamageSources;
import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class PlayerTickEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        if (event.side == LogicalSide.SERVER) {
            ThirstHandler.tick(event.player);
            HeatHandler.tick((ServerLevel) event.player.level(),event.player);
        }
    }

    private static boolean isOutside(ServerLevel level, Player player) {
        BlockPos pos = player.blockPosition();
        for (int y = pos.getY(); y < level.getMaxBuildHeight(); y++) {
            if (!level.isEmptyBlock(new BlockPos(pos.getX(), y, pos.getZ()))) {
                return false;
            }
        }
        return true;
    }
}
