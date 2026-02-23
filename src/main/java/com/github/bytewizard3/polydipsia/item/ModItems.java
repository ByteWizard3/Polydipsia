package com.github.bytewizard3.polydipsia.item;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.item.custom.CamelpackItem;
import com.github.bytewizard3.polydipsia.item.custom.DirtyWaterBottleItem;
import com.github.bytewizard3.polydipsia.item.custom.PurifiedWaterBottleItem;
import com.github.bytewizard3.polydipsia.item.custom.SaltyWaterBottleItem;
import com.github.bytewizard3.polydipsia.tab.ModCreativeTabs;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;
import static com.github.bytewizard3.polydipsia.block.ModBlocks.EXAMPLE_BLOCK;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }

        public static final RegistryObject<Item> WATER_PURIFIER = ITEMS.register("water_purifier",
                        () -> new BlockItem(ModBlocks.WATER_PURIFIER.get(), new Item.Properties()));

        public static final RegistryObject<Item> CAMELPACK_ITEM = ITEMS.register("camelpack",
                        CamelpackItem::getInstance);
        public static final RegistryObject<Item> DIRTY_WATER_BOTTLE = ITEMS.register("dirty_water_bottle",
                        () -> new DirtyWaterBottleItem(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> SALTY_WATER_BOTTLE = ITEMS.register("salty_water_bottle",
                        () -> new SaltyWaterBottleItem(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> PURIFIED_WATER_BOTTLE = ITEMS.register("purified_water_bottle",
                        () -> new PurifiedWaterBottleItem(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> MUDDY_WATER_BOTTLE = ITEMS.register("muddy_water_bottle",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> TOXIC_WATER_BOTTLE = ITEMS.register("toxic_water_bottle",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> COLD_WATER_BOTTLE = ITEMS.register("cold_water_bottle",
                        () -> new Item(new Item.Properties().stacksTo(1)));
        public static final RegistryObject<Item> WATER_ANALYZER = ITEMS.register("water_analyzer",
                        () -> new com.github.bytewizard3.polydipsia.item.custom.WaterAnalyzerItem(
                                        new Item.Properties().stacksTo(1).durability(64)));
        public static final RegistryObject<Item> SALT = ITEMS.register("salt",
                        () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> MUD_BALL = ITEMS.register("mud_ball",
                        () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> DIRTY_WATER_BUCKET = ModItems.ITEMS.register("dirty_water_bucket",
                        () -> new BucketItem(ModFluids.SOURCE_DIRTY_WATER, new Item.Properties()
                                        .craftRemainder(Items.BUCKET)
                                        .stacksTo(1)));

}
