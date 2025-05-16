package com.github.bytewizard3.polydipsia.compatability;

import com.github.bytewizard3.polydipsia.block.BlazingPytheriumBlock;
import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.momosoftworks.coldsweat.api.temperature.block_temp.BlockTemp;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class PytheriumBlockTemp extends BlockTemp
{
    public PytheriumBlockTemp()
    {
        super(0, 0.88, Double.NEGATIVE_INFINITY, 50.6, 7, true,
              ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block instanceof BlazingPytheriumBlock).toArray(Block[]::new));
    }

    @Override
    public double getTemperature(Level level, LivingEntity entity, BlockState state, BlockPos pos, double distance)
    {
        return 0.33;

    }

    @Override
    public boolean hasBlock(Block block)
    {
        return block instanceof BlazingPytheriumBlock;
    }
}
