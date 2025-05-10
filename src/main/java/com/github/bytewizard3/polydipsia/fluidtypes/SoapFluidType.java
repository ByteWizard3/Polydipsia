package com.github.bytewizard3.polydipsia.fluidtypes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.function.Consumer;

/**
 * Basic implementation of {@link FluidType} that supports specifying still and flowing textures in the constructor.
 *
 * @author Choonster (<a href="https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.19.x/LICENSE.txt">MIT License</a>)
 * <p>
 * Change by: Kaupenjoe
 * Added overlayTexture and tintColor as well. Also converts tint color into fog color
 */
public class SoapFluidType extends BaseFluidType {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(PolydipsiaMod.MOD_ID, "misc/in_soap_water");

    public SoapFluidType() {
        super(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL, 0xA1E03800, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f)
                , FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK));

    }

    public SoapFluidType(Properties properties) {
        super(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL, 0xA1E03800, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties);
    }

    public SoapFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, Properties properties) {
        super(stillTexture, flowingTexture, overlayTexture, tintColor, fogColor, properties);
    }
}
