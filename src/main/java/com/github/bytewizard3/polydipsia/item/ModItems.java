package com.github.bytewizard3.polydipsia.item;

import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.item.custom.CamelpackItem;
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

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));
    public static final RegistryObject<Item> CAMELPACK_ITEM = ITEMS.register("camelpack", CamelpackItem::getInstance);
    public static final RegistryObject<Item> DIRTY_WATER_BOTTLE =
            ITEMS.register("dirty_water_bottle", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MUDDY_WATER_BOTTLE =
            ITEMS.register("muddy_water_bottle", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TOXIC_WATER_BOTTLE =
            ITEMS.register("toxic_water_bottle", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIRTY_WATER_BUCKET =
            ModItems.ITEMS.register("dirty_water_bucket", () ->
                    new BucketItem(ModFluids.SOURCE_DIRTY_WATER, new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));
    public static final RegistryObject<Item> SOAP_WATER_BUCKET = ITEMS.register("soap_water_bucket",
            () -> new BucketItem(ModFluids.SOURCE_SOAP_WATER,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> BLAZING_PYTHERIUM_BUCKET = ITEMS.register("pytherium_bucket",
            () -> new BucketItem(ModFluids.BLAZING_PYTHERIUM_SOURCE,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> LAVA_SPICY_BUCKET = ITEMS.register(
            "lava_spicy_bucket",
            () -> new BucketItem(
                    // Ensure proper supplier reference to LAVA_SPICY_SOURCE
                    () -> ModFluids.LAVA_SPICY_SOURCE.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET) // Returns an empty bucket after use
                            .stacksTo(1) // Maximum stack size of 1
            )
    );

}
