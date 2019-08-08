package com.m31.minecraft.forge.levelsandskill.gui;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.proxy.ClientProxy;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import com.m31.minecraft.forge.levelsandskill.utils.TranslationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
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
    static final String LEVELUP="user_level_up";
    static final String ATTR_STRENGTH="user_attr_strength";



    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_skill.png";
    static final ResourceLocation texture=new ResourceLocation(path);
    private GuiScreen parent;
    private EntityPlayerMP entityPlayerMp;

    private int strengthTemp=0;
    private int currentModifyAttrPoint=0;

//    private GuiButtonImage strengthUpBtn=getAttrModifyBtn()


    private GuiButton btnClose;
    private GuiButton p_actionPerformed_1_;

    public LevelsGui(GuiScreen parent, EntityPlayerMP entityPlayer){
        this.parent=parent;
        this.entityPlayerMp=entityPlayer;
    }
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(getAttrModifyBtn(0,1,true,false));
        this.buttonList.add(getAttrModifyBtn(1,1,false,false));
        //name label
    }
    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        //更新
        currentModifyAttrPoint=DataAccesser.getPlayerLevelPoint(this.entityPlayerMp.getEntityData())
                -strengthTemp;



        drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        GlStateManager.color(1f,1f,1f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect((width / 2)-128,5,0,0,256,256);
        //SKILLUP
        getLevelUpLable().drawLabel(this.mc,0,0);
        getLevelUpNumLable().drawLabel(this.mc,0,0);

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
//        getAttrModifyBtn(1,1,true).drawButton(this.mc,getGuiButtonLeftSide(true),getGuiButtonTopSide(1),0);//++
        //存在升级、则展示
        if(DataAccesser.getPlayerLevelPoint(this.entityPlayerMp.getEntityData())>0){
            if(0==strengthTemp){
                this.buttonList.get(1).drawButton(this.mc,-1,-1,0);//--
            }else{
                this.buttonList.get(1).drawButton(this.mc,p_drawScreen_1_,p_drawScreen_2_,0);//--
                this.buttonList.get(0).drawButton(this.mc,p_drawScreen_1_,p_drawScreen_2_,0);//++
            }
            this.buttonList.get(0).drawButton(this.mc,p_drawScreen_1_,p_drawScreen_2_,0);//++
        }


        //button
//        new GuiButton().drawButton();

    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        //按钮监听
        if(currentModifyAttrPoint!=0){
            if(0==p_actionPerformed_1_.id){
                strengthTemp++;
            }else if(1==p_actionPerformed_1_.id){
                if(strengthTemp!=0){
                    strengthTemp--;
                }
            }else{}
        }
//        if(p_actionPerformed_1_==btnClose){
//            mc.displayGuiScreen(parent);
//        }
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
    //===================================升级要素
    private GuiLabel getLevelUpLable(){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*23-4,getVeryStartY()+((getPicHeight()/32)*1),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(LEVELUP)+":");
        return target;
    }
    private GuiLabel getLevelUpNumLable(){
        int lp=currentModifyAttrPoint;
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*28-4,getVeryStartY()+((getPicHeight()/32)*1),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        if(0!=lp){
            target.addLine("§a§l"+lp+"§r ");
        }else{
            target.addLine("§l"+lp+"§r ");
        }
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
        float strengh=DataAccesser.getPlayerStength(this.entityPlayerMp.getEntityData())+strengthTemp;
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*25-2,getVeryStartY()+((getPicHeight()/32)*4-4),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        if(strengthTemp!=0){
            target.addLine("§a§l"+strengh);
        }else{
            target.addLine("§l"+strengh);
        }

        return target;
    }

    /** 绘制属性添加按钮，行从1开始
     * isleft 表示 加、反则表示减
     * @param id
     * @param lineNumber
     * @param isLeft
     * @return
     */
    private GuiButtonImage getAttrModifyBtn(int id,int lineNumber,boolean isLeft,boolean isActive){
        isActive=false;
        lineNumber--;
        //x
        int x=isLeft?
                (getVeryStartX()+(getPicWidth()/32)*28-2):(getVeryStartX()+(getPicWidth()/32)*29);
        //y
        int y=getVeryStartY()+((getPicHeight()/32)*(4+lineNumber)-5);
        //pix add
        int pix_x=isLeft?95:103;
        //pix sub
        int pix_y=isActive?25:16;
        //width
        int width=9;
        //height
        int height=9;
        //diff
        int diff=isActive?0:9;
       return new GuiButtonImage(id,x,y,width,height,pix_x,pix_y,diff,ClientProxy.gui_texture);
    }

    /**获取按钮左边界
     *
     * @param isLeft
     * @return
     */
    private int getGuiButtonLeftSide(boolean isLeft){
        //x
        int x=isLeft?
                (getVeryStartX()+(getPicWidth()/32)*27-2):(getVeryStartX()+(getPicWidth()/32)*28);
        return x;
    }
    /**获取按钮上边界
     *
     * @param lineNumber
     * @return
     */
    private int getGuiButtonTopSide(int lineNumber){
        //y
        int y=getVeryStartY()+((getPicHeight()/32)*(4+lineNumber)-4);
        return y;
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
