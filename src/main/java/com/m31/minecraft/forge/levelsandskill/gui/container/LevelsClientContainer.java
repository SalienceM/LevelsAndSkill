package com.m31.minecraft.forge.levelsandskill.gui.container;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LevelsClientContainer extends GuiContainer {
    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_skill.png";
    static final ResourceLocation texture=new ResourceLocation(path);


    public LevelsClientContainer(Container p_i1072_1_) {
        super(p_i1072_1_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1f,1f,1f);
        this.mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX,offsetY,0,0,this.xSize,this.ySize);
    }
    protected void drawGuiContainerForegroundLayer(int p_drawGuiContainerForegroundLayer_1_, int p_drawGuiContainerForegroundLayer_2_) {
    }
}
