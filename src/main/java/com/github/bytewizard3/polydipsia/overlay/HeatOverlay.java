package com.github.bytewizard3.polydipsia.overlay;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.text.DecimalFormat;

public class HeatOverlay implements IGuiOverlay {
    private static final ResourceLocation HEAT_GRADIENT = new ResourceLocation(PolydipsiaMod.MODID,
            "textures/thirst/heat_gradient.png");
    private static final ResourceLocation CIRCLE = new ResourceLocation(PolydipsiaMod.MODID,
            "textures/thirst/circle-64.png");
    private static final ResourceLocation LINE = new ResourceLocation(PolydipsiaMod.MODID,
            "textures/thirst/line.png");
    int COLOR_WHITE = 0xFFFFFF;

    // Heat configuration in Celsius
    float minHeat = 30f;
    float maxHeat = 45f;
    float idealTemp = 37.5f;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        int xStart = 3;
        int yStart = screenHeight - 30;

        int barWidth = 60;
        int barHeight = 7;
        int markerHeight = 2;

        float currentHeat = 32f; // Replace with actual dynamic value if needed

        // Draw the background gradient
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(HEAT_GRADIENT, xStart, yStart, 0, 0, barWidth, barHeight, barWidth, barHeight);

        int markerX = xStart + (barWidth / 2);
        int markerWidth=5;

        // Draw line at the top of the bar
        guiGraphics.blit(LINE, markerX , yStart +5, 0, 0, markerWidth, 2, markerWidth, 2);
        guiGraphics.blit(LINE, markerX , yStart, 0, 0, markerWidth, 2, markerWidth, 2);


        // Calculate circle position (we assume center is ideal)
        int currentX = 50;
        int centerY = yStart + barHeight / 2;
        int circleSize = 2;

        guiGraphics.blit(CIRCLE, currentX, centerY-circleSize/2, 0, 0, circleSize, circleSize, circleSize, circleSize);

        // Draw current temperature label
        DecimalFormat df = new DecimalFormat("00.0");
        guiGraphics.drawString(gui.getFont(), df.format(currentHeat) + "°C", xStart + barWidth + 10, yStart, 0xFFFFFFFF);
    }

}
