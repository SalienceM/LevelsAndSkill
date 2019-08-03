package com.m31.minecraft.forge.levelsandskill.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class SimpleGui extends GuiScreen {
    private GuiScreen parent;
    private GuiButton btnClose;
    public SimpleGui(GuiScreen parent){
        this.parent=parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(btnClose=new GuiButton(0,(int)(width*0.75), (int) (height*0.85),80,20,"Back"));
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        //modify
        drawCenteredString(fontRenderer,
                "your §f§nPROUD§r first screen",width/2,
                (int) (height*0.2),0xFFF00);
            drawString(fontRenderer,String.format("You are pointing to (§o%d§r,§o%d§r)",p_drawScreen_1_,p_drawScreen_2_),
                    (int)(width*0.05),(int)(height*0.9),0XFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        if(p_actionPerformed_1_==btnClose){
            mc.displayGuiScreen(parent);
        }
    }
}
