package com.github.bytewizard3.polydipsia.block.entity;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, PolydipsiaMod.MODID);

    public static final RegistryObject<BlockEntityType<WaterPurifierBlockEntity>> WATER_PURIFIER_BE = BLOCK_ENTITIES
            .register("water_purifier", () -> BlockEntityType.Builder.of(WaterPurifierBlockEntity::new,
                    ModBlocks.WATER_PURIFIER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
