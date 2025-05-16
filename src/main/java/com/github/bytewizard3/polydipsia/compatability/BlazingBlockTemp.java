package com.github.bytewizard3.polydipsia.compatability;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.momosoftworks.coldsweat.api.temperature.block_temp.BlockTemp;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlazingBlockTemp extends BlockTemp
{
    public BlazingBlockTemp()
    {
        // minEffect, maxEffect, minTemp, maxTemp, range, fade, logarithmic, affected blocks
        super(0.0, 20.0, -50.0, 100.0, 8.0, true, ModBlocks.BLAZING_PYTHERIUM_BLOCK.get());
    }

    @Override
    public double getTemperature(Level level, LivingEntity entity, BlockState state, BlockPos pos, double distance)
    {
        System.out.println("Cold Sweat queried temperature for Blazing Block at: " + pos);
        // You can optionally make it fade with distance, but this returns constant +10 temperature.
        return 10.0;
    }
}
