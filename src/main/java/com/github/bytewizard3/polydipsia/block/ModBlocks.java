package com.github.bytewizard3.polydipsia.block;

import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    public static final RegistryObject<LiquidBlock> DIRTY_WATER_BLOCK =
            BLOCKS.register("dirty_water_block", () ->
                    new DirtyWaterBlock(
                            ModFluids.DIRTY_WATER.get(),
                            BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()
                    ));
    public static final RegistryObject<LiquidBlock> SOAP_WATER_BLOCK = BLOCKS.register("soap_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_SOAP_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));


}
