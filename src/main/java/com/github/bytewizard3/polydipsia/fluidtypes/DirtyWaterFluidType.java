package com.github.bytewizard3.polydipsia.fluidtypes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

public class DirtyWaterFluidType extends BaseFluidType {

    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(PolydipsiaMod.MOD_ID, "misc/in_soap_water");

    public DirtyWaterFluidType() {
        super(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL, 0xA1E03800, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f)
                , Properties.create()
                        .density(1000)
                        .viscosity(1000)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_EMPTY));

    }

    public DirtyWaterFluidType(Properties properties) {
        super(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL, 0xA1E03800, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties);
    }

    public DirtyWaterFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, Properties properties) {
        super(stillTexture, flowingTexture, overlayTexture, tintColor, fogColor, properties);
    }
}
