package com.github.bytewizard3.polydipsia;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.block.entity.ModBlockEntities;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.fluidtypes.ModFluidTypes;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.github.bytewizard3.polydipsia.recipes.ModRecipeSerializers;
import com.github.bytewizard3.polydipsia.recipes.ModRecipes;
import com.github.bytewizard3.polydipsia.screen.ModMenuTypes;
import com.github.bytewizard3.polydipsia.tab.ModCreativeTabs;
import com.mojang.logging.LogUtils;
import com.momosoftworks.coldsweat.api.registry.BlockTempRegistry;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PolydipsiaMod.MODID)
public class PolydipsiaMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "polydipsia";
    public static final String MOD_ID = "polydipsia";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Creates a creative tab with the id "examplemod:example_tab" for the example
    // item, that is placed after the combat tab

    public PolydipsiaMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModFluidTypes.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModRecipeSerializers.SERIALIZERS.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // MinecraftForge.EVENT_BUS.register(new ModEvents());

        // Register our mod's ForgeConfigSpec so that Forge can create and load the
        // config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // @SubscribeEvent
    // public void onLevelTick(TickEvent.LevelTickEvent event) {
    // if (event.phase == TickEvent.Phase.END && !event.level.isClientSide &&
    // event.level instanceof ServerLevel level) {
    // spreadFireAroundSpicyLava(level);
    // }
    // }
    //
    // private void spreadFireAroundSpicyLava(ServerLevel level) {
    // Random random = new Random();
    //
    // // Iterate through all positions within a range of spicy lava
    // for (ServerPlayer player : level.getPlayers(player -> true)) {
    // BlockPos playerPos = player.blockPosition();
    // BlockPos.betweenClosedStream(playerPos.offset(-32, -16, -32),
    // playerPos.offset(32, 16, 32))
    // .filter(pos -> level.getBlockState(pos).getBlock() ==
    // ModBlocks.LAVA_SPICY_BLOCK.get())
    // .forEach(pos -> {
    // for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1),
    // pos.offset(1, 1, 1))) {
    // BlockState nearby = level.getBlockState(nearbyPos);
    // if (nearby.isAir() || nearby.getBlock() == Blocks.FIRE) {
    // if (level.random.nextFloat() < 0.3f) {
    // level.setBlock(nearbyPos, Blocks.FIRE.defaultBlockState(), 11);
    // }
    // }
    // }
    // });
    // }
    //
    // }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

}
