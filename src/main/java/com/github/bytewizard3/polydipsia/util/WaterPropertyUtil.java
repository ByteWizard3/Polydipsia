package com.github.bytewizard3.polydipsia.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Random;

public class WaterPropertyUtil {

    public static class WaterProperties {
        public final int saltiness;
        public final int muddiness;
        public final int pollution;
        public final boolean isCold;

        public WaterProperties(int saltiness, int muddiness, int pollution, boolean isCold) {
            this.saltiness = Math.max(0, Math.min(100, saltiness));
            this.muddiness = Math.max(0, Math.min(100, muddiness));
            this.pollution = Math.max(0, Math.min(100, pollution));
            this.isCold = isCold;
        }
    }

    /**
     * Procedurally generates water properties for a specific block coordinate.
     */
    public static WaterProperties getProperties(Level level, BlockPos pos) {
        long seed = 0;
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            seed = serverLevel.getSeed();
        }

        // Use a simple hash function on the coordinates and world seed to get stable
        // pseudo-random properties per block
        // Chunk coordinates are important to give general areas a similar feel. We'll
        // use block pos directly for higher variance.

        int saltiness = calculateNoise(seed, pos.getX(), pos.getY(), pos.getZ(), 101); // offset 101
        int muddiness = calculateNoise(seed, pos.getX(), pos.getY(), pos.getZ(), 202); // offset 202
        int pollution = calculateNoise(seed, pos.getX(), pos.getY(), pos.getZ(), 303); // offset 303

        Biome biome = level.getBiome(pos).value();

        // Biome Modifiers
        if (biome.getBaseTemperature() > 0.8f) { // Hotter biomes might evaporate more, leaving higher concentration
            saltiness += 10;
        }

        // Ocean/Beach biomes - High salt
        if (biome.getModifiedClimateSettings().temperatureModifier() == Biome.TemperatureModifier.NONE) {
            // Just a basic check, Forge BiomeTags are safer
        }

        // This relies on basic parameters, ideally in 1.20 you'd check Holder<Biome>
        // against BiomeTags
        // For simplicity we will rely on random noise with a baseline, but you can
        // enhance this.

        boolean isCold = biome.getBaseTemperature() < 0.15f;
        boolean isSwamp = level.getBiome(pos).is(BiomeTags.HAS_SWAMP_HUT);

        int finalSalt = (saltiness % 40) + getBiomeSaltModifier(level, pos);
        int finalMud = (muddiness % 60) + getBiomeMudModifier(level, pos);
        int finalPol = pollution % 30;

        // Swamps are muddy but not very salty
        if (isSwamp) {
            finalSalt = 0;
            finalMud = Math.max(finalMud, 40);
        }

        // Cold water is generally purer
        if (isCold) {
            finalPol /= 3;
            finalMud /= 2;
        }

        return new WaterProperties(finalSalt, finalMud, finalPol, isCold);
    }

    private static int calculateNoise(long seed, int x, int y, int z, int offset) {
        // A simple, fast hashing function just for gameplay variety
        long hash = (seed + offset) ^ (x * 73856093L) ^ (y * 19349663L) ^ (z * 83492791L);
        Random random = new Random(hash);
        return random.nextInt(100);
    }

    private static int getBiomeSaltModifier(Level level, BlockPos pos) {
        var biomeHolder = level.getBiome(pos);
        if (biomeHolder.is(BiomeTags.IS_OCEAN) || biomeHolder.is(BiomeTags.IS_BEACH)) {
            return 60; // Oceans have high baseline salt (60% + up to 40% random)
        }
        return 0; // Fresh water baseline
    }

    private static int getBiomeMudModifier(Level level, BlockPos pos) {
        var biomeHolder = level.getBiome(pos);
        if (biomeHolder.is(BiomeTags.IS_RIVER)) {
            return 20; // Rivers have some mud
        } else if (biomeHolder.is(BiomeTags.HAS_SWAMP_HUT)) { // approximation for swamps
            return 40; // Swamps are muddy
        }
        return 0;
    }
}
