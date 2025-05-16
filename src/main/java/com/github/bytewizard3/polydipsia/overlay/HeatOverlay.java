package com.github.bytewizard3.polydipsia.overlay;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.github.bytewizard3.polydipsia.data.ClientHeatData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.text.DecimalFormat;

public class HeatOverlay implements IGuiOverlay {
    private static final ResourceLocation HEAT_GRADIENT = new ResourceLocation(PolydipsiaMod.MODID, "textures/thirst/heat_gradient.png");
    private static final ResourceLocation CIRCLE = new ResourceLocation(PolydipsiaMod.MODID, "textures/thirst/circle-64.png");
    private static final ResourceLocation LINE = new ResourceLocation(PolydipsiaMod.MODID, "textures/thirst/line.png");
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

        float minHeat = 30f;
        float maxHeat = 45f;
        float idealTemp = 37.5f;
        float currentHeat = ClientHeatData.getPlayerHeat(); // Replace with actual dynamic value if needed

// Draw the background gradient
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(HEAT_GRADIENT, xStart, yStart, 0, 0, barWidth, barHeight, barWidth, barHeight);

// Calculate normalized positions
        float heatRange = maxHeat - minHeat;
        float normalizedCurrent = (currentHeat - minHeat) / heatRange;
        float normalizedIdeal = (idealTemp - minHeat) / heatRange;

        int currentX = xStart + Math.round(normalizedCurrent * barWidth);
        int markerX = xStart + Math.round(normalizedIdeal * barWidth);

// Draw marker line for ideal temperature
        int markerWidth = 5;
        guiGraphics.blit(LINE, markerX, yStart + 5, 0, 0, markerWidth, 2, markerWidth, 2);
        guiGraphics.blit(LINE, markerX, yStart, 0, 0, markerWidth, 2, markerWidth, 2);

// Draw current temperature circle
        int circleSize = 2;
        int centerY = yStart + barHeight / 2;
        guiGraphics.blit(CIRCLE, currentX, centerY - circleSize / 2 , 0, 0, circleSize, circleSize, circleSize, circleSize);

// Draw current temperature label
        DecimalFormat df = new DecimalFormat("00.0");
        guiGraphics.drawString(gui.getFont(), df.format(currentHeat) + "°C", xStart + barWidth + 10, yStart, 0xFFFFFFFF);
    }
}
