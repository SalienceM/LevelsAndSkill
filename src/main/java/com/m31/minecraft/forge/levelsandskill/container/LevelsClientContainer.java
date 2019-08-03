package com.m31.minecraft.forge.levelsandskill.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LevelsClientContainer extends GuiContainer {


    public LevelsClientContainer(Container p_i1072_1_) {
        super(p_i1072_1_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {

    }
    protected void drawGuiContainerForegroundLayer(int p_drawGuiContainerForegroundLayer_1_, int p_drawGuiContainerForegroundLayer_2_) {
    }
}
