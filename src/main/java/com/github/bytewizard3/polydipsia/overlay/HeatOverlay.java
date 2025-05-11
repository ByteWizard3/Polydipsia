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

    int COLOR_WHITE = 0xFFFFFF;

    // Heat configuration in Celsius
    float minHeat = 30f;
    float maxHeat = 45f;
    float idealMin = 36.5f;
    float idealMax = 37.5f;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        int xStart = 3;
        int yStart = screenHeight - 30;

        int barWidth = 60;
        int barHeight = 7;
        int markerHeight = 4;
        int dotRadius = 3;

        float currentHeat = 32f;//ClientHeatData.getPlayerHeat();
        float minHeat = 30f;
        float maxHeat = 45f;
        float idealMin = 36.5f;
        float idealMax = 37.5f;

        // Draw the gradient background
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(HEAT_GRADIENT, xStart, yStart, 0, 0, barWidth, barHeight, barWidth, barHeight);

        // Draw ideal temperature marker lines (top + bottom)
        int idealMinX = xStart + (int)(((idealMin - minHeat) / (maxHeat - minHeat)) * barWidth);
        int idealMaxX = xStart + (int)(((idealMax - minHeat) / (maxHeat - minHeat)) * barWidth);

        int markerColor = 0xFFFFFFFF;

        // Ideal min marker
        guiGraphics.fill(idealMinX, yStart - markerHeight, idealMinX + 1, yStart, markerColor);
        guiGraphics.fill(idealMinX, yStart + barHeight, idealMinX + 1, yStart + barHeight + markerHeight, markerColor);

        // Ideal max marker
        guiGraphics.fill(idealMaxX, yStart - markerHeight, idealMaxX + 1, yStart, markerColor);
        guiGraphics.fill(idealMaxX, yStart + barHeight, idealMaxX + 1, yStart + barHeight + markerHeight, markerColor);

        // Draw white circle representing current heat
        int currentX = xStart + (int)(((currentHeat - minHeat) / (maxHeat - minHeat)) * barWidth);
        int centerY = yStart + barHeight / 2;

        // Draw the white circle manually as a filled square with a circle look (simplified)
        guiGraphics.fill(currentX - dotRadius, centerY - dotRadius, currentX + dotRadius, centerY + dotRadius, 0xFFFFFFFF);

        // Draw the temperature text
        DecimalFormat df = new DecimalFormat("00.0");
        guiGraphics.drawString(gui.getFont(), df.format(currentHeat) + "°C", xStart + barWidth + 10, yStart, 0xFFFFFFFF);
    }

}
