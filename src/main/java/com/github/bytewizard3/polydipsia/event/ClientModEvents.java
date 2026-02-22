package com.github.bytewizard3.polydipsia.event;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.overlay.ThirstOverlay;
import com.momosoftworks.coldsweat.api.registry.BlockTempRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.mojang.text2speech.Narrator.LOGGER;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = PolydipsiaMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }


    @SubscribeEvent
    public static void registerHuds(RegisterGuiOverlaysEvent event) {
        LOGGER.info("Registering Thirst Overlay");
        event.registerAboveAll("thirst", new ThirstOverlay());
    }
}