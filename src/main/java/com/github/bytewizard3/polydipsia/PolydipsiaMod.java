package com.github.bytewizard3.polydipsia;

import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import com.github.bytewizard3.polydipsia.thirst.PlayerThirst;
import com.github.bytewizard3.polydipsia.thirst.PlayerThirstProvider;
import com.github.bytewizard3.polydipsia.overlay.ThirstOverlay;
import com.github.bytewizard3.polydipsia.damage.ModDamageSources;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PolydipsiaMod.MODID)
public class PolydipsiaMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "polydipsia";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));


    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public PolydipsiaMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModItems.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
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

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModItems.CAMELPACK_ITEM);
        }
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    @SubscribeEvent
    public void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                event.addCapability(new ResourceLocation(MODID, "properties"), new PlayerThirstProvider());
            }
        }
    }


    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerThirst.class);

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            ClientThirstData.tickCount++;
            @NotNull LazyOptional<PlayerThirst> playerThirst=event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST);
            Player player=event.player;
            playerThirst.ifPresent(thirst -> {
                if (thirst.getThirst() > 0 && ClientThirstData.tickCount / 100 > 1) {
                    ClientThirstData.tickCount = 0;
                    thirst.subThirst(1);
                    ClientThirstData.set(thirst.getThirst());
                }
            });

//            event.player.getArmorSlots().forEach(slot->{
//                LOGGER.info("SOLTS rrr:{} {} ",slot.getDescriptionId());
//                LOGGER.info("SOLTS index:{} ");
//            });

            // Armor slots are: 0 -> Helmet, 1 -> Chestplate, 2 -> Leggings, 3 -> Boots
            ItemStack chestplayeItem = event.player.getInventory().getArmor(2);
            if (chestplayeItem.is(ModItems.CAMELPACK_ITEM.get())) {
                if (ClientThirstData.tickCount % 80 == 0) {
                    playerThirst.ifPresent(thirst -> {
                        thirst.addThirst(1);
                        chestplayeItem.hurtAndBreak(1, event.player, player2 -> player2.broadcastBreakEvent(EquipmentSlot.CHEST));
                        ClientThirstData.set(thirst.getThirst());
                    });

                }
            }
            if(ClientThirstData.getPlayerThirst()<10){
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,10));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,10,2));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,10,2));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,10,2));
            }

            Level level=event.player.level();
            DamageSource damageSource = new ModDamageSources(
                    level.registryAccess())
                    .dehydration(player,null);


            if(ClientThirstData.getPlayerThirst()<1){
                player.hurt(damageSource,10);

            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Sheep) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (player.getMainHandItem().getItem() == Items.BEEF) {
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " hurt a Sheep with BEEF! But why?"));
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        player.sendSystemMessage(Component.literal("currentPlayerThirst is " + thirst.getThirst()));
                    });
                } else {
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        thirst.addThirst(10);
                        ClientThirstData.set(thirst.getThirst());
                    });
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " hurt a Sheep!"));
                }
            }
        }
    }
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){
        if(event.getEntity() instanceof Player){
            Player player= (Player) event.getEntity();
            @NotNull LazyOptional<PlayerThirst> playerThirst=player.getCapability(PlayerThirstProvider.PLAYER_THIRST);
            playerThirst.ifPresent(thirst -> {
                thirst.addThirst(100);
                ClientThirstData.set(thirst.getThirst());
            });
        }
    }
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            thirst.setInitialThirst(); // full thirst
            ClientThirstData.set(thirst.getThirst());
        });
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        }


        @SubscribeEvent
        public static void registerHuds(RegisterGuiOverlaysEvent event) {
            LOGGER.info("Registering Thirst Overlay");
            ThirstOverlay guiOverlay = new ThirstOverlay();
            event.registerAboveAll("thirst", guiOverlay);
        }
    }
}
