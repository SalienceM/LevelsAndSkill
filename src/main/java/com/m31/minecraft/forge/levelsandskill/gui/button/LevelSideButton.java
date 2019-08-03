package com.m31.minecraft.forge.levelsandskill.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class LevelSideButton extends GuiButton {
    private final Predicate<GuiScreen> predicate;

    public LevelSideButton(int id, int x, int y,Predicate<GuiScreen> selectPred) {
        super(id, x, y, 32,28,"");
        this.predicate=selectPred;
    }


    @Override
    public void drawButton(@Nonnull Minecraft minecraft, int mouseX, int mouseY, float f) {
        enabled=!minecraft.player.getRecipeBook().isGuiOpen();
        GuiScreen curr=minecraft.currentScreen;
//        if(curr instanceof GuiContainerCreative&& )
        if(enabled){
            GlStateManager.color(1f,1f,1f);
//            minecraft.renderEngine.bindTexture();



        }


//        super.drawButton(p_drawButton_1_, p_drawButton_2_, p_drawButton_3_, p_drawButton_4_);
    }
}
