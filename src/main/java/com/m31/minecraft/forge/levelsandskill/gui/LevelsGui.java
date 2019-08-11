package com.m31.minecraft.forge.levelsandskill.gui;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.proxy.ClientProxy;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import com.m31.minecraft.forge.levelsandskill.utils.TranslationUtil;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static com.m31.minecraft.forge.levelsandskill.utils.DataAccesser.ATTR_TYPE;

public class LevelsGui extends GuiScreen {
    static Logger logger= LogManager.getLogger(LevelsGui.class);

    static int BTN_SEQ_ATTR=50;     //50以内的btn序列全为属性操作按钮序列

    static final String ATTR_MODIFY_PRE="ATTRM_";
    static final String ATTR_MODIFY_AFT_PLUS="_PLUS";
    static final String ATTR_MODIFY_AFT_SUB="_SUB";

    static final String CONFIRM_BTN="lvlchange_confirm";
    static final String USERNAME="user_name";
    static final String MAXHEALTH="user_max_health";
    static final String CURRENTHEALTH="user_curr_health";
    static final String TEMPHEALTH="user_temp_health";
    static final String RVTHEALTHSPEED="user_health_revert_speed";
    static final String LEVELUP="user_level_up";

    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_skill.png";
    static final ResourceLocation texture=new ResourceLocation(path);
    private GuiScreen parent;
    private EntityPlayerMP entityPlayerMp;
    private ConcurrentHashMap<Integer,GuiButton> currentButtonMapCached=new ConcurrentHashMap<>();

    private ConcurrentHashMap<ATTR_TYPE,Integer> currentAttrMapCached=new ConcurrentHashMap<>();

    private ConcurrentHashMap<String,BTN_TYPE> bntIdTypeCached=new ConcurrentHashMap<>();

    private int currentModifyAttrPoint=0;


    public LevelsGui(GuiScreen parent, EntityPlayerMP entityPlayer){
        this.parent=parent;
        this.entityPlayerMp=entityPlayer;
    }
    @Override
    public void initGui() {
        try{
        super.initGui();
        for(BTN_TYPE btn_type:BTN_TYPE.values()){
            bntIdTypeCached.put(btn_type.getId()+"",btn_type);
        }
//        初始化临时属性节点
        for (ATTR_TYPE attr_type:ATTR_TYPE.values()){
            currentAttrMapCached.put(attr_type,0);
        }

        //若不存在升级要素，将不进行绘制相关按钮
        if(DataAccesser.getPlayerLevelPoint(this.entityPlayerMp.getEntityData())>0){
            registerButton(BTN_TYPE.ATTRM_STRENGTH_PLUS,getAttrModifyBtn(0,1,true,false));
            registerButton(BTN_TYPE.ATTRM_STRENGTH_SUB,getAttrModifyBtn(0,1,false,false));
            registerButton(BTN_TYPE.ATTRM_AGILIE_PLUS,getAttrModifyBtn(0,2,true,false));
            registerButton(BTN_TYPE.ATTRM_AGILIE_SUB,getAttrModifyBtn(0,2,false,false));
            registerButton(BTN_TYPE.ATTRM_KNOWLEDGE_PLUS,getAttrModifyBtn(0,3,true,false));
            registerButton(BTN_TYPE.ATTRM_KNOWLEDGE_SUB,getAttrModifyBtn(0,3,false,false));
            registerButton(BTN_TYPE.ATTRM_TOUGHNESS_PLUS,getAttrModifyBtn(0,4,true,false));
            registerButton(BTN_TYPE.ATTRM_TOUGHNESS_SUB,getAttrModifyBtn(0,4,false,false));
            registerButton(BTN_TYPE.ATTRM_VIGOUR_PLUS,getAttrModifyBtn(0,5,true,false));
            registerButton(BTN_TYPE.ATTRM_VIGOUR_SUB,getAttrModifyBtn(0,5,false,false));

            registerButton(BTN_TYPE.CONFIRM_CHANGE,
                    new GuiButton(51,getVeryStartX()+(getPicWidth()/32)*28,getVeryStartY()+(getPicHeight()/32)*15-4,(getPicWidth()/32)*3,(getPicHeight()/32)*2,TranslationUtil.gettFullModTranslateBtnString(CONFIRM_BTN)));
        }
        }catch (Exception e){
            logger.error(e);
        }
    }
    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        //更新
        currentModifyAttrPoint=DataAccesser.getPlayerLevelPoint(this.entityPlayerMp.getEntityData());
        for(Map.Entry<ATTR_TYPE,Integer> entry:currentAttrMapCached.entrySet()){
            currentModifyAttrPoint-=entry.getValue();
        }
        //=========================默认==================
        drawDefaultBackground();
        //=========================绘制GUI背景==================
        GlStateManager.color(1f,1f,1f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect((width / 2)-128,5,0,0,256,256);
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);

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
        for(int i=0;i<ATTR_TYPE.values().length;i++) {
            getAttrLable(ATTR_TYPE.values()[i],i+1).drawLabel(this.mc,0,0);
            getAttrNumLable(ATTR_TYPE.values()[i],i+1).drawLabel(this.mc,0,0);
        }
//        getAttrStrengthLable().drawLabel(this.mc,0,0);
//        getAttrStrengthNumLable().drawLabel(this.mc,0,0);
        // 按钮组渲染
//        attrChangeConfirmButtonDraw(p_drawScreen_1_,p_drawScreen_2_);
//        attrButtonDraw(ATTR_TYPE.STRENGTH,p_drawScreen_1_,p_drawScreen_2_);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        //按钮监听
        //1、属性变更类按钮
        if(isAttrChangeBtn(button)){
            int changeMode=getAttrModifyAvalable(currentAttrMapCached.get(getAttrTypeFromAttrBtnId(button.id)),currentModifyAttrPoint,button.id);
            switch (changeMode){
                case 0:
                    currentAttrMapCached.put(getAttrTypeFromAttrBtnId(button.id),currentAttrMapCached.get(getAttrTypeFromAttrBtnId(button.id))+1) ;
                    break;
                case 1:
                    currentAttrMapCached.put(getAttrTypeFromAttrBtnId(button.id),currentAttrMapCached.get(getAttrTypeFromAttrBtnId(button.id))-1) ;
                    break;
                    default:break;
            }
        }
        //2、confirm按钮
        if(BTN_TYPE.CONFIRM_CHANGE.id==button.id){
            if(currentModifyAttrPoint!=DataAccesser.getPlayerLevelPoint(this.entityPlayerMp.getEntityData())
                    &&DataAccesser.getPlayerLevelPoint(this.entityPlayerMp.getEntityData())!=0){
                //更新细节属性
                for(Map.Entry<ATTR_TYPE,Integer> entry:this.currentAttrMapCached.entrySet()){
                    int currentStatic=DataAccesser.getPlayerAttr(this.entityPlayerMp.getEntityData(),entry.getKey());
                    DataAccesser.setPlayerAttr(this.entityPlayerMp.getEntityData(),entry.getKey(),currentStatic+entry.getValue());
                    this.currentAttrMapCached.put(entry.getKey(),0);
                }
                DataAccesser.setPlayerLevelPoint(this.entityPlayerMp.getEntityData(),currentModifyAttrPoint);
            }
        }

//        if(p_actionPerformed_1_==btnClose){
//            mc.displayGuiScreen(parent);
//        }
    }

    /**判断当前按钮操作是否可执行
     *
     * @param currentAttrTypeTemp
     * @param totalAttrTemp
     * @param btnId
     * @return
     *
     * 0 为可加、1为可减
     *
     *
     *
     */
    private int getAttrModifyAvalable(int currentAttrTypeTemp,int totalAttrTemp,int btnId){
        //对2取模为0的为+
        if(btnId%2==0){
            if(totalAttrTemp!=0)return 0;
        }else if(btnId%2!=0){
            if(currentAttrTypeTemp>0)return 1;
        }else{}
        return -1;
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

    private GuiLabel getAttrLable(ATTR_TYPE attr_type,int line){
        GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*22-2,getVeryStartY()+new BigDecimal((getPicHeight()/32)*(3+line*1.5)-8).setScale(1,BigDecimal.ROUND_FLOOR).intValue(),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        target.addLine(TranslationUtil.getFullModTranslateAttrName(attr_type.getUntranslateName())+":");
        return target;
    }
    private GuiLabel getAttrNumLable(ATTR_TYPE attr_type,int line){
        int targetAttr=DataAccesser.getPlayerAttr(this.entityPlayerMp.getEntityData(),attr_type)+currentAttrMapCached.get(attr_type);
                GuiLabel target=new GuiLabel(fontRenderer,1,getVeryStartX()+(getPicWidth()/32)*25-2,getVeryStartY()+new BigDecimal((getPicHeight()/32)*(3+line*1.5)-8).setScale(1,BigDecimal.ROUND_FLOOR).intValue(),
                getPicWidth()/16,getPicHeight()/32,0XFFFFFF);
        if(currentAttrMapCached.get(attr_type)!=0){
            target.addLine("§a§l"+targetAttr);
        }else{
            target.addLine("§l"+targetAttr);
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
        int y=getVeryStartY()+new BigDecimal((getPicHeight()/32)*(4+lineNumber*1.5)-5).setScale(1,BigDecimal.ROUND_FLOOR).intValue();
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

//    /**获取按钮左边界
//     *
//     * @param isLeft
//     * @return
//     */
//    private int getGuiButtonLeftSide(boolean isLeft){
//        //x
//        int x=isLeft?
//                (getVeryStartX()+(getPicWidth()/32)*27-2):(getVeryStartX()+(getPicWidth()/32)*28);
//        return x;
//    }
//    /**获取按钮上边界
//     *
//     * @param lineNumber
//     * @return
//     */
//    private int getGuiButtonTopSide(int lineNumber){
//        //y
//        int y=getVeryStartY()+((getPicHeight()/32)*(4+lineNumber)-4);
//        return y;
//    }








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

    /**按钮类型枚举
     *
     */
    enum BTN_TYPE{
        ATTRM_STRENGTH_PLUS(0)
        ,ATTRM_STRENGTH_SUB(1)
        ,ATTRM_AGILIE_PLUS(2)
        ,ATTRM_AGILIE_SUB(3)
        ,ATTRM_KNOWLEDGE_PLUS(4)
        ,ATTRM_KNOWLEDGE_SUB(5)
        ,ATTRM_TOUGHNESS_PLUS(6)
        ,ATTRM_TOUGHNESS_SUB(7)
        ,ATTRM_VIGOUR_PLUS(8)
        ,ATTRM_VIGOUR_SUB(9)
        ,CONFIRM_CHANGE(51)
        ;

        private int id=0;
        private BTN_TYPE(int id) {
            this.id=id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

    /**注册按钮
     *
     * @param buttonTypeName
     * @param button
     */
    private void registerButton(BTN_TYPE buttonTypeName,GuiButton button){
        currentButtonMapCached.put(buttonTypeName.getId(),button);
        button.id=buttonTypeName.getId();
        this.buttonList.add(button);
    }

    /**获取按钮
     *
     * @param buttonTypeName
     * @return
     */
    private GuiButton getCacheButton(String buttonTypeName){
        BTN_TYPE btn_type=Enum.valueOf(BTN_TYPE.class,buttonTypeName);
        return currentButtonMapCached.get(btn_type.getId());
    }


    /**绘制属性按钮
     * 可以不进行回执卡
     * @param type
     * @param mousex
     * @param mousey
     */
    private void attrButtonDraw(ATTR_TYPE type, int mousex, int mousey){
        String cachedKeyPlus=ATTR_MODIFY_PRE+type+ATTR_MODIFY_AFT_PLUS;
        String cachedKeySub=ATTR_MODIFY_PRE+type+ATTR_MODIFY_AFT_SUB;

        if(0==this.currentAttrMapCached.get(type)){
            getCacheButton(cachedKeySub).drawButton(this.mc,-1,-1,0);//--
        }else{
            getCacheButton(cachedKeySub).drawButton(this.mc,mousex,mousey,0);//--
            getCacheButton(cachedKeyPlus).drawButton(this.mc,mousex,mousey,0);//++
        }
        if(0==currentModifyAttrPoint){
            getCacheButton(cachedKeyPlus).drawButton(this.mc,-1,-1,0);//++
        }else{
            getCacheButton(cachedKeyPlus).drawButton(this.mc,mousex,mousey,0);//++
        }
    }

    /**绘制属性确认按钮
     *
     * @param mousex
     * @param mousey
     */
    private void attrChangeConfirmButtonDraw(int mousex,int mousey){
        if(DataAccesser.getPlayerLevelPoint(entityPlayerMp.getEntityData())!=0){
            this.currentButtonMapCached.get(BTN_TYPE.CONFIRM_CHANGE.id).enabled=true;
            this.currentButtonMapCached.get(BTN_TYPE.CONFIRM_CHANGE.id)
                    .drawButton(this.mc,mousex,mousey,0);
        }else{
            this.currentButtonMapCached.get(BTN_TYPE.CONFIRM_CHANGE.id).enabled=false;
        }
    }



    /**判断是否为属性操作按钮
     *
     * @param button
     * @return
     */
    private boolean isAttrChangeBtn(GuiButton button){
        if(button.id<=BTN_SEQ_ATTR)return true;
        return false;
    }

    /**根据按钮id获取对应属性内容
     *
     * @param btnId
     * @return
     */
    private ATTR_TYPE getAttrTypeFromAttrBtnId(int btnId){
        String btnName =bntIdTypeCached.get(btnId+"").name();
        String attrName=btnName.replace(ATTR_MODIFY_PRE,"").replace(ATTR_MODIFY_AFT_SUB,"").replace(ATTR_MODIFY_AFT_PLUS,"");
        ATTR_TYPE type=Enum.valueOf(ATTR_TYPE.class,attrName);
        return type;
    }
}
