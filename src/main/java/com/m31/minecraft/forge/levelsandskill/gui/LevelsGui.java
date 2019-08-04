package com.m31.minecraft.forge.levelsandskill.gui;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class LevelsGui extends GuiScreen {
    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_skill.png";
    static final ResourceLocation texture=new ResourceLocation(path);
    private GuiScreen parent;
    private EntityPlayerMP entityPlayerMp;

    private GuiButton btnClose;

    public LevelsGui(GuiScreen parent, EntityPlayerMP entityPlayer){
        this.parent=parent;
        this.entityPlayerMp=entityPlayer;
    }
    @Override
    public void initGui() {
        super.initGui();
//        buttonList.add(btnClose=new GuiButton(0,(int)(width*0.75), (int) (height*0.85),80,20,"Back"));
    }
    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        GlStateManager.color(1f,1f,1f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect((width / 2)-128,5,0,0,256,256);
        String maxHealth =DataAccesser.getPlayerMaxHealth(entityPlayerMp.getEntityData())+"";
        this.drawCenteredString(this.mc.fontRenderer,"max health:"+maxHealth,width / 2,height/10,0XFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        if(p_actionPerformed_1_==btnClose){
            mc.displayGuiScreen(parent);
        }
    }


}
