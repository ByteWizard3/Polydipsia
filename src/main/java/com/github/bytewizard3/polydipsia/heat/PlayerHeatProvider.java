package com.github.bytewizard3.polydipsia.heat;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerHeatProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<PlayerHeat> PLAYER_HEAT = CapabilityManager.get(new CapabilityToken<>() {});
    private final PlayerHeat instance = new PlayerHeat();

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_HEAT ? LazyOptional.of(() -> instance).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Heat", instance.getHeat());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.setHeat(nbt.getInt("Heat"));
    }
}
