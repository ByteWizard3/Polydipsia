package com.github.bytewizard3.polydipsia.Thirst;

import com.github.bytewizard3.polydipsia.ExampleMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.text.DecimalFormat;

public class ThirstOverlay implements IGuiOverlay {
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(ExampleMod.MODID,
            "textures/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(ExampleMod.MODID,
            "textures/thirst/empty_thirst.png");
    private static final ResourceLocation THIRST_BAR_EMPTY = new ResourceLocation(ExampleMod.MODID,
            "textures/thirst/thirst_bar_empty.png");
    private static final ResourceLocation THIRST_BAR_FULL = new ResourceLocation(ExampleMod.MODID,
            "textures/thirst/thirst_bar_full.png");
    private static final ResourceLocation WATER_DROP = new ResourceLocation(ExampleMod.MODID,
            "textures/thirst/drop.png");
    int COLOR_WHITE = 16777215;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int ylevelOfThirstBar=y-15;
        int xLevelOfThirstBar=3;
        int thirstBarHeight=7;
        int thirstBarWidth=60;
        int waterDropx=xLevelOfThirstBar+thirstBarWidth+1;
        int waterdropSize=8;
        int textX=waterDropx+waterdropSize+1;
        guiGraphics.blit(THIRST_BAR_EMPTY,xLevelOfThirstBar,ylevelOfThirstBar,0,0,thirstBarWidth,thirstBarHeight,thirstBarWidth,thirstBarHeight);
        guiGraphics.blit(WATER_DROP,waterDropx,ylevelOfThirstBar,0,0,waterdropSize,waterdropSize,waterdropSize,waterdropSize);
        DecimalFormat df = new DecimalFormat("00.00");
        double d=ClientThirstData.getPlayerThirst();
        guiGraphics.drawString(gui.getFont(),df.format(d)+"%",textX,ylevelOfThirstBar, COLOR_WHITE);
        int percentage= (int) (thirstBarWidth*(d/100));
        guiGraphics.blit(THIRST_BAR_FULL,xLevelOfThirstBar,ylevelOfThirstBar,0,0,percentage,thirstBarHeight,thirstBarWidth,thirstBarHeight);

        //        guiGraphics.blit(THIRST_BAR_FULL,50,50,0,0,20,15,274,15);

    }
}
