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
import java.math.BigDecimal;

public class LevelsGui extends GuiScreen {

//    int pic_width=256;
//    int pic_height=256;
//    int very_start_x=(width/2)-128;
//    int very_start_y=5;


    static final String USERNAME="user_name";
    static final String MAXHEALTH="user_max_health";
    static final String CURRENTHEALTH="user_curr_health";
    static final String TEMPHEALTH="user_temp_health";
    static final String RVTHEALTHSPEED="user_health_revert_speed";
    static final String ATTR_STRENGTH="user_attr_strength";



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
        getTempHealthLable().drawLabel(this.mc,0,0);
        getTempHealthNumLable().drawLabel(this.mc,0,0);
        getRevertHealthLable().drawLabel(this.mc,0,0);
        getRevertHealthNumLable().drawLabel(this.mc,0,0);
        //21\5
        getAttrStrengthLable().drawLabel(this.mc,0,0);
        getAttrStrengthNumLable().drawLabel(this.mc,0,0);

        //button
//        new GuiButton().drawButton();

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
        float maxhp=new BigDecimal( DataAccesser.getPlayerMaxHealth(entityPlayerMp.getEntityData())).setScale(1,BigDecimal.ROUND_FLOOR).floatValue();
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
        float currenthp=new BigDecimal( entityPlayerMp.getHealth()).setScale(1,BigDecimal.ROUND_FLOOR).floatValue();
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*7,getVeryStartY()+((getPicHeight()/32)*11)-1,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine("§l"+currenthp);
        return target;
    }
    //=====================================临时血量
    private GuiLabel getTempHealthLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+getPicWidth()/32,getVeryStartY()+((getPicHeight()/32)*12)+1,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(TEMPHEALTH)+":");
        return target;
    }
    private GuiLabel getTempHealthNumLable(){
        float tempHealth= new BigDecimal(DataAccesser.getPlayerTempHealth(this.entityPlayerMp.getEntityData())).setScale(1,BigDecimal.ROUND_FLOOR).floatValue();
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*7,getVeryStartY()+((getPicHeight()/32)*12)+1,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine("§l"+tempHealth);
        return target;
    }
    //=====================================秒回
    private GuiLabel getRevertHealthLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+getPicWidth()/32,getVeryStartY()+((getPicHeight()/32)*13)+3,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(RVTHEALTHSPEED)+":");
        return target;
    }
    private GuiLabel getRevertHealthNumLable(){
        float revertSpeed= DataAccesser.getPlayerHealthRevertSpeed(this.entityPlayerMp.getEntityData());
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*7,getVeryStartY()+((getPicHeight()/32)*13)+3,
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine("§l"+revertSpeed);
        return target;
    }
    //strength=================================================
    private GuiLabel getAttrStrengthLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*22-2,getVeryStartY()+((getPicHeight()/32)*4-4),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(ATTR_STRENGTH)+":");
        return target;
    }
    private GuiLabel getAttrStrengthNumLable(){
        float strengh=DataAccesser.getPlayerStength(this.entityPlayerMp.getEntityData());
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*25-2,getVeryStartY()+((getPicHeight()/32)*4-4),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine("§l"+strengh);
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
