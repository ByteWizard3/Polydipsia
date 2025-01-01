package com.github.bytewizard3.polydipsia.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CamelpackItem extends Item implements Equipable {

    public static CamelpackItem getInstance() {
        CamelpackItem camelpack;
        Item.Properties properties =new Item.Properties();
        properties.defaultDurability(0);
        properties.durability(1000);
        camelpack=new CamelpackItem(properties);
        return camelpack;
    }


    public CamelpackItem(Properties pProperties) {
        super(pProperties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide){
            BlockPos positionClicked=pContext.getClickedPos();
            Player player = pContext.getPlayer();
            BlockState state=pContext.getLevel().getBlockState(positionClicked);
            if(state.is(Blocks.DIAMOND_BLOCK)){
                state.getBlock();
                assert player != null;
                player.sendSystemMessage(Component.literal("Diamond Block found"));
                pContext.getItemInHand().hurtAndBreak(2,player,player2->player2.broadcastBreakEvent(player2.getUsedItemHand()));
            }
        }
        return InteractionResult.SUCCESS;
    }

    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }
}
