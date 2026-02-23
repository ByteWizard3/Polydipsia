package com.github.bytewizard3.polydipsia.tab;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.core.registries.Registries;

public class ModCreativeTabs {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, PolydipsiaMod.MODID);

        public static final RegistryObject<CreativeModeTab> POLYDIPSIA_TAB = CREATIVE_MODE_TABS.register(
                        "polydipsia_tab",
                        () -> CreativeModeTab.builder()
                                        .title(Component.translatable("itemGroup.polydipsia.polydipsia_tab"))
                                        .icon(() -> new ItemStack(ModItems.DIRTY_WATER_BOTTLE.get()))
                                        .displayItems((parameters, output) -> {
                                                output.accept(ModItems.DIRTY_WATER_BOTTLE.get());
                                                output.accept(ModItems.WATER_PURIFIER.get());
                                                output.accept(ModItems.SALTY_WATER_BOTTLE.get());
                                                output.accept(ModItems.PURIFIED_WATER_BOTTLE.get());
                                                output.accept(ModItems.MUDDY_WATER_BOTTLE.get());
                                                output.accept(ModItems.TOXIC_WATER_BOTTLE.get());
                                                output.accept(ModItems.COLD_WATER_BOTTLE.get());
                                                output.accept(ModItems.DIRTY_WATER_BUCKET.get());
                                                output.accept(ModItems.CAMELPACK_ITEM.get());
                                                output.accept(ModItems.WATER_ANALYZER.get());
                                                output.accept(ModItems.SALT.get());
                                                output.accept(ModItems.MUD_BALL.get());
                                        })
                                        .build());
}
