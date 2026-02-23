package com.github.bytewizard3.polydipsia.screen;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
            PolydipsiaMod.MODID);

    public static final RegistryObject<MenuType<WaterPurifierMenu>> WATER_PURIFIER_MENU = MENUS
            .register("water_purifier_menu", () -> IForgeMenuType.create(WaterPurifierMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
