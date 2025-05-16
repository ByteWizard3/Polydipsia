package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirst;
import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirstProvider;
import com.github.bytewizard3.polydipsia.capabilities.thirst.ThirstHandler;
import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class DamageAndDeathEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        ThirstHandler.sheepHurt(event.getSource().getEntity(),event.getEntity());
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ThirstHandler.onDeath(player);
        }
    }
}
