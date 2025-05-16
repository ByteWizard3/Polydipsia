package com.github.bytewizard3.polydipsia.capabilities.heat;

import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirstProvider;
import com.github.bytewizard3.polydipsia.data.ClientHeatData;
import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class HeatHandler {
    private static final Logger log = LogManager.getLogger();
    private static final Map<UUID, Integer> tickCounters = new HashMap<>();
    public static void tick(ServerLevel level, Player player) {
        Biome biome = level.getBiome(player.blockPosition()).value();
        float temp = biome.getBaseTemperature();
        boolean outside = isOutside(level, player);
        // Increment and check tick count
        UUID playerId = player.getUUID();
        int count = tickCounters.getOrDefault(playerId, 0) + 1;
        tickCounters.put(playerId, count);

        player.getCapability(PlayerHeatProvider.PLAYER_HEAT).ifPresent(heat -> {
            int change = 0;
            if (outside) {
                if (temp > 1.5f) change++;
                else if (temp < 0.15f) change--;
            }

            if (count % 40 == 0) {
                log.info("Tick #{} for [{}] in biome [{}] (temp: {}). Outside: {}. Heat change: {}. Current heat: {}",
                        count,
                        player.getName().getString(),
                        biome.toString(),
                        temp,
                        outside,
                        change,
                        heat.getHeat());
            }
            heat.addHeat(change);
            ClientHeatData.set(heat.getHeat());
        });
    }

    private static boolean isOutside(ServerLevel level, Player player) {
        BlockPos pos = player.blockPosition().above();
        return level.canSeeSky(pos) && !level.isRainingAt(pos);
    }


    public static void attachCapability(AttachCapabilitiesEvent<Entity> event, Player player) {
        if (!event.getObject().getCapability(PlayerHeatProvider.PLAYER_HEAT).isPresent()) {
            event.addCapability(new ResourceLocation(MODID, "player_heat"), new PlayerHeatProvider());
        }
    }

    public static void onPlayerClone(Player original, Player player) {
        original.getCapability(PlayerHeatProvider.PLAYER_HEAT).ifPresent(oldStore -> {
            player.getCapability(PlayerHeatProvider.PLAYER_HEAT).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
    }
    public static void onPlayerJoin(Player entity) {
        entity.getCapability(PlayerHeatProvider.PLAYER_HEAT).ifPresent(heat -> {
            heat.setInitialHeat();
            ClientHeatData.set(heat.getHeat());
        });
    }
}
