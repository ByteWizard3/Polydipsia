package com.github.bytewizard3.polydipsia.event;


import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.damage.ModDamageSources;
import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import com.github.bytewizard3.polydipsia.fluid.ModFluids;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.github.bytewizard3.polydipsia.thirst.PlayerThirst;
import com.github.bytewizard3.polydipsia.thirst.PlayerThirstProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack held = event.getItemStack();

        if (!level.isClientSide && held.getItem() == Items.GLASS_BOTTLE) {
            BlockState clickedBlock = level.getBlockState(pos);

            if (clickedBlock.getBlock() == ModBlocks.DIRTY_WATER_BLOCK.get()) {
                consumeBottleAndGive(player, held, new ItemStack(ModItems.DIRTY_WATER_BOTTLE.get()));
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }

            // Add more water types here if needed:
            // if (clickedBlock.getBlock() == ModFluids.MUDDY_WATER_BLOCK.get()) { ... }
        }
    }
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().getItem() == ModItems.CAMELPACK_ITEM.get()) {
            event.getCrafting().setDamageValue(event.getCrafting().getMaxDamage());
        }
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





    private static void consumeBottleAndGive(Player player, ItemStack held, ItemStack newBottle) {
        held.shrink(1);
        if (!player.addItem(newBottle)) {
            player.drop(newBottle, false);
        }
    }
}