package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.capabilities.heat.PlayerHeat;
import com.github.bytewizard3.polydipsia.capabilities.heat.PlayerHeatProvider;
import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirst;
import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirstProvider;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.capabilities.heat.HeatHandler;
import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class PlayerCapabilityEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            ThirstHandler.attachCapability(event,player);
            HeatHandler.attachCapability(event,player);
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);
        event.register(PlayerHeat.class);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        HeatHandler.onPlayerClone(event.getOriginal(),event.getEntity());
        ThirstHandler.onPlayerClone(event.getOriginal(),event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ThirstHandler.onPlayerJoin(event.getEntity());
        HeatHandler.onPlayerJoin(event.getEntity());

    }
}
