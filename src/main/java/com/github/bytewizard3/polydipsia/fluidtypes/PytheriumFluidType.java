package com.github.bytewizard3.polydipsia.fluidtypes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class PytheriumFluidType extends FluidType
{
    // Resource locations for textures
    private static final ResourceLocation STILL_TEXTURE = new ResourceLocation(MODID, "block/pyrotheum_still");
    private static final ResourceLocation FLOWING_TEXTURE = new ResourceLocation(MODID, "block/pyrotheum_flow");
    private static final ResourceLocation OVERLAY_TEXTURE = null;

    public static ResourceLocation getFlowingTexture() {
        return FLOWING_TEXTURE;
    }
    public static ResourceLocation getStillTexture() {
        return STILL_TEXTURE;
    }
    public PytheriumFluidType(Properties properties)
    {
        super(properties);
    }

    @Override
    public int getTemperature()
    {
        return 1500;
    }

    @Override
    public int getViscosity()
    {
        return 2000;
    }

    @Override
    public int getLightLevel()
    {
        return 15;
    }

    @Override
    public boolean canExtinguish(FluidState state, BlockGetter getter, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean canPushEntity(Entity entity)
    {
        return true;
    }

    @Override
    public boolean canSwim(Entity entity)
    {
        return false;
    }

    @Override
    public boolean canDrownIn(LivingEntity entity)
    {
        return false;
    }

    @Override
    public boolean canHydrate(Entity entity)
    {
        return false;
    }

    @Override
    public double motionScale(Entity entity)
    {
        return 0.8;
    }

    @Override
    public void setItemMovement(net.minecraft.world.entity.item.ItemEntity entity)
    {
        var vec3 = entity.getDeltaMovement();
        entity.setDeltaMovement(vec3.x * 0.8, vec3.y + (vec3.y < 0.06 ? 0.001 : 0), vec3.z * 0.8);
    }

    @Nullable
    @Override
    public SoundEvent getSound(SoundAction action)
    {
        if (action.equals(SoundActions.BUCKET_FILL)) {
            return SoundEvents.BUCKET_FILL_LAVA;
        } else if (action.equals(SoundActions.BUCKET_EMPTY)) {
            return SoundEvents.BUCKET_EMPTY_LAVA;
        }
        return null;
    }

    @Override
    public BlockPathTypes getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable net.minecraft.world.entity.Mob mob, boolean canFluidLog)
    {
        return BlockPathTypes.LAVA;
    }

    public static PytheriumFluidType create() {
        return new PytheriumFluidType(FluidType.Properties.create()
                .density(1500)
                .viscosity(2000)
                .temperature(1500)
                .lightLevel(15)
        );
    }
    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                // Return overlay texture or null/transparent if none
//                return new ResourceLocation("modid", "block/your_fluid_overlay");
                return null;
            }

            // You can override more methods here for color, tint, etc.
        });
    }

}
