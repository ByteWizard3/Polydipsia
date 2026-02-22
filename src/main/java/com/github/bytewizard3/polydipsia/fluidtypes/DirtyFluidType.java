package com.github.bytewizard3.polydipsia.fluidtypes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

public class DirtyFluidType extends FluidType {
    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public DirtyFluidType() {
        super(getProperties());
    }
    public DirtyFluidType(Properties properties) {
        super(properties);
    }
    private static Properties getProperties(){
        Properties properties= Properties.create()
                .canSwim(false)
                .canDrown(false)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(true)
                .lightLevel(15)
                .density(2000)
                .viscosity(1200)
                .temperature(4000)
                .rarity(Rarity.RARE)
                .pathType(BlockPathTypes.LAVA)
                .adjacentPathType(null)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA);
        return properties;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {

            private static final String ID = "lava_spicy";
            private static final ResourceLocation FLOW = new ResourceLocation(MODID , "block/fluid/" + ID + "_flow");
            private static final ResourceLocation STILL = new ResourceLocation(MODID , "block/fluid/" + ID + "_still");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOW;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return null;
            }
        });
    }

    @Override
    public double motionScale(Entity entity) {
        return entity.level().dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
    }

    @Override
    public void setItemMovement(ItemEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        entity.setDeltaMovement(vec3.x * 0.95F, vec3.y + (vec3.y < 0.06F ? 5.0E-4F : 0.0F), vec3.z * 0.95F);

        // Ignite non-fire-resistant items
        if (!entity.getItem().getItem().isFireResistant()) {
            entity.setSecondsOnFire(5);
        }
    }

    // Custom fluid interaction with entities
    public void onEntityInside(Entity entity) {
        // Burn entities that are not fire-immune
        if (!entity.fireImmune()) {
            entity.setSecondsOnFire(5); // Set the entity on fire for 5 seconds
        }
    }
}
