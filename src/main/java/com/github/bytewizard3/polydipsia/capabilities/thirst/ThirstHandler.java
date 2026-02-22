package com.github.bytewizard3.polydipsia.capabilities.thirst;

import com.github.bytewizard3.polydipsia.block.ModBlocks;
import com.github.bytewizard3.polydipsia.data.ClientThirstData;
import com.github.bytewizard3.polydipsia.item.ModItems;
import com.github.bytewizard3.polydipsia.damage.ModDamageSources;
import com.momosoftworks.coldsweat.api.registry.BlockTempRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class ThirstHandler {
    public static void onDeath(Player player){
        @NotNull LazyOptional<PlayerThirst> playerThirst = player.getCapability(PlayerThirstProvider.PLAYER_THIRST);
        playerThirst.ifPresent(thirst -> {
            thirst.addThirst(100);
            ClientThirstData.set(thirst.getThirst());
        });
    }
    public static void sheepHurt(Entity source,Entity target){
        if (target instanceof Sheep) {
            if (source instanceof Player player) {
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
    public static void tick(Player player) {
        Level level = player.level();
        if (level.isClientSide) return;
        ClientThirstData.tickCount++;
        @NotNull LazyOptional<PlayerThirst> playerThirst = player.getCapability(PlayerThirstProvider.PLAYER_THIRST);

        playerThirst.ifPresent(thirst -> {
            if (thirst.getThirst() > 0 && ClientThirstData.tickCount / 100 > 1) {
                ClientThirstData.tickCount = 0;
                thirst.subThirst(1);
                ClientThirstData.set(thirst.getThirst());
            }
        });

        ItemStack chestItem = player.getInventory().getArmor(2);
        if (chestItem.is(ModItems.CAMELPACK_ITEM.get()) && ClientThirstData.tickCount % 80 == 0) {
            playerThirst.ifPresent(thirst -> {
                thirst.addThirst(1);
                chestItem.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(EquipmentSlot.CHEST));
                ClientThirstData.set(thirst.getThirst());
            });
        }

        if (ClientThirstData.getPlayerThirst() < 10) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 10, 2));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 2));
        }

        if (ClientThirstData.getPlayerThirst() < 1) {
            DamageSource source = new ModDamageSources(level.registryAccess()).dehydration(player, null);
            player.hurt(source, 10);
        }
    }

    public static void attachCapability(AttachCapabilitiesEvent<Entity> event,Player player) {
        if (!player.getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
            event.addCapability(new ResourceLocation(MODID, "properties"), new PlayerThirstProvider());
        }
    }
    public static void onPlayerClone(Player original, Player player) {
        original.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
    }

    public static void onPlayerJoin(Player entity) {
        entity.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
            thirst.setInitialThirst();
            ClientThirstData.set(thirst.getThirst());
        });
    }

    public static void dirtyWaterRightClick(PlayerInteractEvent.RightClickBlock event) {
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
        }
    }
    private static void consumeBottleAndGive(Player player, ItemStack held, ItemStack newBottle) {
        held.shrink(1);
        if (!player.addItem(newBottle)) {
            player.drop(newBottle, false);
        }
    }
}
