package com.github.bytewizard3.polydipsia.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import com.github.bytewizard3.polydipsia.fluidtypes.ModFluidTypes;
public class ModFluidInteractionRegistrar {

    public static void register() {
        // Assuming ModFluids.BLAZING_PYTHERIUM_TYPE is your FluidType instance
        FluidType blazingPytheriumType = ModFluidTypes.PYTHEREUM_WATER_FLUID_TYPE.get();

        FluidInteractionRegistry.addInteraction(blazingPytheriumType, new FluidInteractionRegistry.InteractionInformation(
                // Predicate: check if neighbor fluid is water
                (level, currentPos, relativePos, neighborFluidState) -> neighborFluidState.is(net.minecraft.tags.FluidTags.WATER),
                // Interaction: replace the current block with obsidian if source level = 0 else stone
                (level, currentPos, relativePos, currentState) -> {
                    BlockState newBlock = currentState.getValue(BlockStateProperties.LEVEL) == 0
                            ? Blocks.OBSIDIAN.defaultBlockState()
                            : Blocks.STONE.defaultBlockState();
                    level.setBlockAndUpdate(currentPos, newBlock);
                    level.levelEvent(1501, currentPos, 0); // fizz effect
                }
        ));
    }
}
