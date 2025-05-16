package com.github.bytewizard3.polydipsia.capabilities.heat;

import com.github.bytewizard3.polydipsia.capabilities.thirst.PlayerThirst;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerHeatProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<PlayerHeat> PLAYER_HEAT = CapabilityManager.get(new CapabilityToken<>() {});
    private PlayerHeat heat = new PlayerHeat();
    private final LazyOptional<PlayerHeat> optional = LazyOptional.of(this::createPlayerHeat);

    private @NotNull PlayerHeat createPlayerHeat() {
        if(this.heat == null) {
            this.heat = new PlayerHeat();
        }

        return this.heat;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_HEAT ? LazyOptional.of(() -> heat).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("Heat", heat.getHeat());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        heat.setHeat(nbt.getInt("Heat"));
    }
}
