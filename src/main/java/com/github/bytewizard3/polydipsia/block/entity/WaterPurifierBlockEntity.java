package com.github.bytewizard3.polydipsia.block.entity;

import com.github.bytewizard3.polydipsia.item.ModItems;
import com.github.bytewizard3.polydipsia.recipes.WaterPurifierRecipe;
import com.github.bytewizard3.polydipsia.screen.WaterPurifierMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WaterPurifierBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public WaterPurifierBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WATER_PURIFIER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> WaterPurifierBlockEntity.this.progress;
                    case 1 -> WaterPurifierBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> WaterPurifierBlockEntity.this.progress = pValue;
                    case 1 -> WaterPurifierBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.polydipsia.water_purifier");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WaterPurifierMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("water_purifier.progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("water_purifier.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, WaterPurifierBlockEntity pBlockEntity) {
        if (hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(WaterPurifierBlockEntity pBlockEntity) {
        SimpleContainer inventory = new SimpleContainer(pBlockEntity.itemHandler.getSlots());
        for (int i = 0; i < pBlockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pBlockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<WaterPurifierRecipe> match = pBlockEntity.level.getRecipeManager()
                .getRecipeFor(WaterPurifierRecipe.Type.INSTANCE, inventory, pBlockEntity.level);

        if (match.isPresent()) {
            ItemStack outputItem = match.get().getResultItem(null);

            ItemStack inputItem = pBlockEntity.itemHandler.getStackInSlot(0);
            int saltiness = 0;
            int muddiness = 0;
            if (inputItem.hasTag()) {
                saltiness = inputItem.getTag().getInt("Saltiness");
                muddiness = inputItem.getTag().getInt("Muddiness");
            }

            pBlockEntity.itemHandler.extractItem(0, 1, false);
            pBlockEntity.itemHandler.setStackInSlot(1, new ItemStack(outputItem.getItem(),
                    pBlockEntity.itemHandler.getStackInSlot(1).getCount() + 1));

            // Byproducts
            if (saltiness > 30) {
                pBlockEntity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.SALT.get(),
                        pBlockEntity.itemHandler.getStackInSlot(2).getCount() + 1));
            }
            if (muddiness > 30) {
                pBlockEntity.itemHandler.setStackInSlot(3, new ItemStack(ModItems.MUD_BALL.get(),
                        pBlockEntity.itemHandler.getStackInSlot(3).getCount() + 1));
            }

            pBlockEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(WaterPurifierBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<WaterPurifierRecipe> match = entity.level.getRecipeManager()
                .getRecipeFor(WaterPurifierRecipe.Type.INSTANCE, inventory, entity.level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem(null)); // Passing null for
                                                                                            // RegistryAccess
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        boolean canInsertMain = inventory.getItem(1).getItem() == output.getItem() || inventory.getItem(1).isEmpty();
        boolean canInsertSalt = inventory.getItem(2).getItem() == ModItems.SALT.get() || inventory.getItem(2).isEmpty();
        boolean canInsertMud = inventory.getItem(3).getItem() == ModItems.MUD_BALL.get()
                || inventory.getItem(3).isEmpty();
        return canInsertMain && canInsertSalt && canInsertMud;
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        boolean mainCount = inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
        boolean saltCount = inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
        boolean mudCount = inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
        return mainCount && saltCount && mudCount;
    }
}
