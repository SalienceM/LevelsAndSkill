package com.m31.minecraft.forge.levelsandskill.gui;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import com.m31.minecraft.forge.levelsandskill.utils.TranslationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;

public class LevelsGui extends GuiScreen {

//    int pic_width=256;
//    int pic_height=256;
//    int very_start_x=(width/2)-128;
//    int very_start_y=5;


    static final String USERNAME="user_name";
    static final String MAXHEALTH="user_max_health";
    static final String CURRENTHEALTH="user_curr_health";


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
        //name label
    }
    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {

        drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        GlStateManager.color(1f,1f,1f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect((width / 2)-128,5,0,0,256,256);
        getNameLable().drawLabel(this.mc,0,0);
        getMaxHealthLable().drawLabel(this.mc,0,0);
        getMaxHealthNumLable().drawLabel(this.mc,0,0);
        getCurrHealthLable().drawLabel(this.mc,0,0);
        getCurrHealthNumLable().drawLabel(this.mc,0,0);

    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        if(p_actionPerformed_1_==btnClose){
            mc.displayGuiScreen(parent);
        }
    }


    /**获取姓名标签
     *
     * @return
     */
    private GuiLabel getNameLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+getPicWidth()/32,getVeryStartY()+getPicHeight()/32,
                getPicWidth()/8,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(USERNAME)+":"+entityPlayerMp.getDisplayNameString());
        return target;
    }
    /**获取姓名标签
     *
     * @return
     */
    private GuiLabel getMaxHealthLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+getPicWidth()/32,getVeryStartY()+((getPicHeight()/32)*10)-3,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(MAXHEALTH)+":");
        return target;
    }
    private GuiLabel getMaxHealthNumLable(){
        int maxhp=MathHelper.ceil(Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*7,getVeryStartY()+((getPicHeight()/32)*10)-3,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine("§l"+maxhp);
        return target;
    }
    private GuiLabel getCurrHealthLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+getPicWidth()/32,getVeryStartY()+((getPicHeight()/32)*11)-1,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(CURRENTHEALTH)+":");
        return target;
    }
    private GuiLabel getCurrHealthNumLable(){
        int currenthp= MathHelper.ceil(Minecraft.getMinecraft().player.getHealth());
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*7,getVeryStartY()+((getPicHeight()/32)*11)-1,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine("§l"+currenthp);
        return target;
    }



    private int getVeryStartX(){
        return (width/2)-128;
    }
    private int getVeryStartY(){
        return 8;
    }
    private int getPicWidth(){
        return 256;
    }
    private int getPicHeight(){
        return 256;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
