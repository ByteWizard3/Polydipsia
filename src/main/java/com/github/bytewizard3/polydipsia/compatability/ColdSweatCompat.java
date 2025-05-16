package com.github.bytewizard3.polydipsia.compatability;

import com.momosoftworks.coldsweat.api.event.core.registry.BlockTempRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "polydypsia", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ColdSweatCompat
{
    @SubscribeEvent
    public static void onBlockTempRegister(BlockTempRegisterEvent event)
    {
        event.register(new BlazingBlockTemp());
        System.out.println("[Polydypsia] Registered PytheriumBlockTemp via BlockTempRegisterEvent");
    }
}
