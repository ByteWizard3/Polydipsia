package com.example.examplemod.Thirst;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ColorResolverManager;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;
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
        RenderSystem.setShaderTexture(0, EMPTY_THIRST);
        for(int i = 0; i < 10; i++) {
            guiGraphics.blit(EMPTY_THIRST,x - 94 + (i * 9), y - 54,0,0,12,12,
                    12,12);
        }
        RenderSystem.setShaderTexture(0, FILLED_THIRST);
        guiGraphics.blit(THIRST_BAR_EMPTY,50,50,0,0,274,15,274,15);
        guiGraphics.blit(THIRST_BAR_EMPTY,10,100,0,0,50,15,50,15);
        guiGraphics.blit(THIRST_BAR_EMPTY,20,150,0,0,274,5,274,5);
        guiGraphics.blit(THIRST_BAR_EMPTY,20,y-20,0,0,120,10,120,10);
        guiGraphics.drawString(gui.getFont(),"5%",75,75, COLOR_WHITE);
        guiGraphics.blit(WATER_DROP,130,y-20,0,0,12,12,12,12);

        //        guiGraphics.blit(THIRST_BAR_FULL,50,50,0,0,20,15,274,15);

    }
}
