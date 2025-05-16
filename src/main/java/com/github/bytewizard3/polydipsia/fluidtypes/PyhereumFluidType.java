package com.github.bytewizard3.polydipsia.fluidtypes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

import static com.github.bytewizard3.polydipsia.PolydipsiaMod.MODID;

/**
 * Basic implementation of {@link FluidType} that supports specifying still and flowing textures in the constructor.
 *
 * @author Choonster (<a href="https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.19.x/LICENSE.txt">MIT License</a>)
 * <p>
 * Change by: Kaupenjoe
 * Added overlayTexture and tintColor as well. Also converts tint color into fog color
 */
public class PyhereumFluidType extends BaseFluidType {
    public static final ResourceLocation PYTHERIUM_STILL = new ResourceLocation(MODID, "block/pyrotheum_still");
    public static final ResourceLocation PYTHERIUM_FLOWING = new ResourceLocation(MODID, "block/pyrotheum_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation(PolydipsiaMod.MOD_ID, "misc/in_soap_water");

    public PyhereumFluidType() {
        super(PYTHERIUM_STILL, PYTHERIUM_FLOWING, WATER_OVERLAY_RL, 0xA1E03800, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f)
                , Properties.create().lightLevel(10).density(3000).viscosity(6000).temperature(3000).sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK));

    }

    public PyhereumFluidType(Properties properties) {
        super(PYTHERIUM_STILL, PYTHERIUM_FLOWING, WATER_OVERLAY_RL, 0xA1E03800, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties);
    }

    public PyhereumFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, Properties properties) {
        super(stillTexture, flowingTexture, overlayTexture, tintColor, fogColor, properties);
    }
}
