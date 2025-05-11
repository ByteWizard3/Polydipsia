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
            "textures/thirst/heat_gradient.png"); // horizontal visible light gradient
    private static final ResourceLocation TEMP_SYMBOL = new ResourceLocation(PolydipsiaMod.MODID,
            "textures/thirst/heat_thermometer_empty.png");   // optional °C symbol texture

    int COLOR_WHITE = 16777215;

    // Configurable values
    float minHeat = 0f;
    float maxHeat = 100f;
    float idealMin = 36.5f;
    float idealMax = 37.5f;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        int xStart = 3;
        int yStart = screenHeight - 30;

        int barWidth = 60;
        int barHeight = 7;
        int dotSize = 2;

        float currentHeat = 27f;//ClientHeatData.getPlayerHeat(); // You need this capability

        // Background gradient bar
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(HEAT_GRADIENT, xStart, yStart, 0, 0, barWidth, barHeight, barWidth, barHeight);

        // Ideal range lines
        int idealMinX = xStart + (int)(((idealMin - minHeat) / (maxHeat - minHeat)) * barWidth);
        int idealMaxX = xStart + (int)(((idealMax - minHeat) / (maxHeat - minHeat)) * barWidth);
        guiGraphics.fill(idealMinX, yStart - 1, idealMinX + 1, yStart + barHeight + 1, COLOR_WHITE);
        guiGraphics.fill(idealMaxX, yStart - 1, idealMaxX + 1, yStart + barHeight + 1, COLOR_WHITE);

        // Current heat indicator (dot)
        int currentX = xStart + (int)(((currentHeat - minHeat) / (maxHeat - minHeat)) * barWidth);
        guiGraphics.fill(currentX - dotSize / 2, yStart - 2, currentX + dotSize / 2 + 1, yStart + barHeight + 2, COLOR_WHITE);

        // Temperature label (text + symbol)
        DecimalFormat df = new DecimalFormat("00.0");
        int textX = xStart + barWidth + 10;
        guiGraphics.drawString(gui.getFont(), df.format(currentHeat) + "°C", textX, yStart, COLOR_WHITE);

        // Optional texture symbol
        // guiGraphics.blit(TEMP_SYMBOL, textX + 30, yStart - 1, 0, 0, 8, 8, 8, 8);
    }
}
