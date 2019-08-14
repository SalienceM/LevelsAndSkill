package com.m31.minecraft.forge.levelsandskill.utils;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.events.PlayerEventBus;
import com.m31.minecraft.forge.levelsandskill.items.Body;
import com.m31.minecraft.forge.levelsandskill.items.heart.Heart;
import com.m31.minecraft.forge.levelsandskill.proxy.ClientProxy;
import com.m31.minecraft.forge.levelsandskill.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

import java.math.BigDecimal;
import java.util.Optional;

public class DataAccesser {
    public static final String PLAYER_EXTENTION="LevelsAndSkill";
    public static final String PLAYER_EXTENTION_HEART="Heart";
    public static final String PLAYER_EXTENTION_ATTRIBUTE="Attribute";
    public static final String PLAYER_EXTENTION_LEVEL="Level";
    public static final String PLAYER_EXTENTION_HEART_STATIC_ASFLOAT ="heart_static";
    public static final String PLAYER_EXTENTION_HEART_TEMP_ASFLOAT ="heart_temp";
    public static final String PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT ="heart_revert_speed";
    public static final String PLAYER_EXTENTION_ATTR_STRENGTH_ASFLOAT ="attr_strength";
    public static final String PLAYER_EXTENTION_LEVEL_POINT_ASINT="level_point";
    public static final String PLAYER_EXTENTION_LEVEL_CAP_ASINT="level_capcity";
    public static final String PLAYER_EXTENTION_LEVEL_EXP_CURRENT_ASINT ="level_current_exp";

    public static final String PLAYER_EXTENTION_LEVEL_ASINT="levels";
    public static final String PLAYER_EXTENTION_LEVEL_MAX_ASINT="levels_max";

    public static final String TRAITS="Traits";

    /**从playerRoot标签中获取Heart 节点
     *
     * @param root
     * @return
     */
    private static NBTTagCompound getRootHeart(NBTTagCompound root){
       return root.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(PLAYER_EXTENTION).getCompoundTag(PLAYER_EXTENTION_HEART);
    }

    /**从playerRoot标签中获取Attibute 节点
     *
     * @param root
     * @return
     */
    private static NBTTagCompound getRootAttribute(NBTTagCompound root){
        return root.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(PLAYER_EXTENTION).getCompoundTag(PLAYER_EXTENTION_ATTRIBUTE);
    }

    /**从playerRoot标签中获取Level 节点
     *
     * @param root
     * @return
     */
    private static NBTTagCompound getRootLevel(NBTTagCompound root){
        return root.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(PLAYER_EXTENTION).getCompoundTag(PLAYER_EXTENTION_LEVEL);
    }



    /**是否存在指定特性
     *
     * @param root
     * @param traitName
     * @return
     */
    public static boolean hasTargetTrait(NBTTagCompound root,String traitName){
        return root.getCompoundTag(TRAITS).hasKey(traitName);
    }










    /**获取当前最大血量有效值
     * =base+static+temp
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static float getPlayerMaxHealth(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootHeart(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Heart.BaseHealth;
        //calculate
        float base=Heart.BaseHealth;
        float staticHealth=nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_STATIC_ASFLOAT);
        float tempHealth=nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT);
        float full=base+staticHealth+tempHealth;
        if(full<=Heart.BaseMinHealth)full=Heart.BaseMinHealth;
        return full;
    }


    /**获取当前临时血量有效值
     * =temp
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static float getPlayerTempHealth(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootHeart(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Heart.BaseTempHealth;
        //calculate
        float tempHealth=nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT);
        return tempHealth;
    }

    /**获取当前临时血量有效值
     * =heart revert speed
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static float getPlayerHealthRevertSpeed(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootHeart(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Heart.BaseHealthRevertSpeed;
        //calculate
        float revertHealthSpeed=nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT);
        return revertHealthSpeed;
    }


    public static float getPlayerMaxHealthRespawn(NBTTagCompound playerNbtTagCompound) {
        clearTempHealth(playerNbtTagCompound);
        return getPlayerMaxHealth(playerNbtTagCompound);
    }

    /**清除临时效果
     *
     * @param playerNbtTagCompound
     */
    private static void clearTempHealth(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootHeart(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return ;
        nbtTagCompoundPlayerHeart.setFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,0f);
    }


    /**提升血量
     *
     * @param playerNbtTagCompound
     * @param amout
     * @param useInTempHeart
     */
    public static void incrPlayerMaxHealth(NBTTagCompound playerNbtTagCompound,float amout,boolean useInTempHeart) {
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootHeart(playerNbtTagCompound);
        if(nbtTagCompoundPlayerHeart.isEmpty())return;
        if(useInTempHeart){
            nbtTagCompoundPlayerHeart.setFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT)+amout);
        }else{
            nbtTagCompoundPlayerHeart.setFloat(PLAYER_EXTENTION_HEART_STATIC_ASFLOAT,nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_STATIC_ASFLOAT)+amout);
        }
    }

    /**减少血量
     *
     * @param playerNbtTagCompound
     * @param amout
     * @param useInTempHeart
     */
    public static void subPlayerMaxHealth(NBTTagCompound playerNbtTagCompound,float amout,boolean useInTempHeart){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootHeart(playerNbtTagCompound);
        if(nbtTagCompoundPlayerHeart.isEmpty())return;
        if(useInTempHeart){
            nbtTagCompoundPlayerHeart.setFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT)-amout);
        }else{
            nbtTagCompoundPlayerHeart.setFloat(PLAYER_EXTENTION_HEART_STATIC_ASFLOAT,nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_HEART_STATIC_ASFLOAT)-amout);
        }
    }

    /**构建特性NBT
     *
     * @param titleTrait
     * @param subTrait
     * @return
     */
    public static NBTTagCompound buildTargetTraitNBTTagCompound(String titleTrait,String[] subTrait){
        NBTTagCompound nbtTagCompound=new NBTTagCompound();
        NBTTagCompound subnbtTagCompound=new NBTTagCompound();
        for(int i=0;i<subTrait.length;i++){
            subnbtTagCompound.setString(i+"",subTrait[i]);
        }
        nbtTagCompound.setTag(titleTrait,subnbtTagCompound);
        return nbtTagCompound;
    }


    /**判断是否存在子特性
     *
     * @param targetNBTTagCompound
     * @param titleTrait
     * @param inner
     * @return
     */
    public static boolean ifHasSubTargetTraitNBTTagCompound(NBTTagCompound targetNBTTagCompound, String titleTrait,String inner){

        if(null==targetNBTTagCompound)return false;
        NBTTagCompound targetSub=targetNBTTagCompound.getCompoundTag(titleTrait);
        if(targetSub.isEmpty())return false;
        for(int i=0;i<targetSub.getSize();i++){
            String target=targetSub.getString(i+"");
            if(target.equals(inner))return true;
        }
        return false;
    }





    /**设定最大血量
     *
     *
     * @param entityPlayerMP
     * @param maxHealth
     */
    public static void setMaxHealth(EntityPlayerMP entityPlayerMP, float maxHealth){
//        if(!world.isRemote){
            entityPlayerMP.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
//        }
    }

    /**获取服务器玩家信息
     *
     * @param entityPlayer
     * @return
     */
    public static Optional<EntityPlayerMP> getPlayerMpFromEntityPlayer(EntityPlayer entityPlayer,boolean onlyNeedFromServer){
        if(entityPlayer instanceof EntityPlayerMP){
            return Optional.of((EntityPlayerMP) entityPlayer);
        }else if(entityPlayer instanceof EntityPlayerSP){
            if(onlyNeedFromServer)return Optional.empty();
            Minecraft mc=Minecraft.getMinecraft();
            if(mc.world.isRemote){
                LevelsAndSkill.proxy.registerFakePlayer();
                MinecraftServer minecraftServer = ClientProxy.fakePlayer.server;
                EntityPlayerMP entityPlayerMP=minecraftServer.getPlayerList().getPlayerByUsername(mc.player.connection.getGameProfile().getName());
                if(null==entityPlayerMP)return Optional.empty();
                return Optional.of(entityPlayerMP);
            }
        }else{}
        return Optional.empty();
    }
    //======================================================

    public static float getPlayerStength(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootAttribute(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Body.ATTR_STRENGTH_DEFAULT;
        //calculate
        float strength=nbtTagCompoundPlayerHeart.getFloat(PLAYER_EXTENTION_ATTR_STRENGTH_ASFLOAT);
        return strength;
    }

    /**获取玩家属性值
     *
     * @param playerNbtTagCompound
     * @param attrName
     * @return
     */
    public static int getPlayerAttr(NBTTagCompound playerNbtTagCompound,ATTR_TYPE attrName){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootAttribute(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Body.ATTR_DEFAULT;
        //calculate
        int target=nbtTagCompoundPlayerHeart.getInteger(attrName.untranslateName);
        return target;
    }

    /**设置玩家属性值
     *
     * @param playerNbtTagCompound
     * @param attrName
     * @return
     */
    public static void setPlayerAttr(NBTTagCompound playerNbtTagCompound,ATTR_TYPE attrName,float attrValue){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootAttribute(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return;
        //calculate
        float target=nbtTagCompoundPlayerHeart.getFloat(attrName.untranslateName);
        nbtTagCompoundPlayerHeart.setFloat(attrName.untranslateName,attrValue);
    }





    /**获取用户升级属性点
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static int getPlayerLevelPoint(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Body.LEVELPOINT_DEFAULT;
        //calculate
        int levelPoint=nbtTagCompoundPlayerHeart.getInteger(PLAYER_EXTENTION_LEVEL_POINT_ASINT);
        return levelPoint;
    }

    /**获取用户升级属性点
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static int getPlayerLevelExp(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Body.LEVELEXP_CURRENT_DEFAULT;
        //calculate
        int levelPoint=nbtTagCompoundPlayerHeart.getInteger(PLAYER_EXTENTION_LEVEL_EXP_CURRENT_ASINT);
        return levelPoint;
    }

    /**获取用户升级属性点
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static void setPlayerLevelExp(NBTTagCompound playerNbtTagCompound,int newexp){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return;
        //calculate
        nbtTagCompoundPlayerHeart.setInteger(PLAYER_EXTENTION_LEVEL_EXP_CURRENT_ASINT,newexp);
    }


    /**获取用户当前等级
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static int getPlayerLevels(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Body.LEVELS_DEFAULT;
        //calculate
        int level=nbtTagCompoundPlayerHeart.getInteger(PLAYER_EXTENTION_LEVEL_ASINT);
        return level;
    }

    /**设置用户当前等级
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static void setPlayerLevels(NBTTagCompound playerNbtTagCompound,int level){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return;
        //calculate
        nbtTagCompoundPlayerHeart.setInteger(PLAYER_EXTENTION_LEVEL_ASINT,level);
    }

    /**获取用户当前等级
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static int getPlayerMaxLevels(NBTTagCompound playerNbtTagCompound){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return Body.LEVELS_DEFAULT;
        //calculate
        int level=nbtTagCompoundPlayerHeart.getInteger(PLAYER_EXTENTION_LEVEL_MAX_ASINT);
        return level;
    }

    /**设置用户当前等级
     *
     * @param playerNbtTagCompound
     * @return
     */
    public static void setPlayerMaxLevels(NBTTagCompound playerNbtTagCompound,int level){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return;
        //calculate
        nbtTagCompoundPlayerHeart.setInteger(PLAYER_EXTENTION_LEVEL_MAX_ASINT,level);
    }








    /**获取用户当前等级经验槽
     *
     * @return
     */
    public static int getPlayerLevelsCap(int level){
        return getPlayerLevelCapcity(level,Body.LEVELS_BASECAP, Body.LEVELS_CAP_GROWTH);
    }









    /**设置用户当前经验槽
     *
     * @param playerNbtTagCompound
     * @param cap
     */
    public static void setPlayerLevelsCap(NBTTagCompound playerNbtTagCompound,int cap){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return ;
        //calculate
        nbtTagCompoundPlayerHeart.setInteger(PLAYER_EXTENTION_LEVEL_CAP_ASINT,cap);
    }




    /**设置用户升级属性点
     *
     * @param playerNbtTagCompound
     * @param playerLevelPoint
     */
    public static void setPlayerLevelPoint(NBTTagCompound playerNbtTagCompound,int playerLevelPoint){
        NBTTagCompound nbtTagCompoundPlayerHeart=getRootLevel(playerNbtTagCompound);
        if (nbtTagCompoundPlayerHeart.isEmpty())return ;
        //calculate
        nbtTagCompoundPlayerHeart.setInteger(PLAYER_EXTENTION_LEVEL_POINT_ASINT,playerLevelPoint);
    }


    /**属性类型枚举
     *
     */
    public static enum ATTR_TYPE{
        STRENGTH("attr_strength")   //力量
        ,AGILIE("attr_agilie")   //灵巧
        ,KNOWLEDGE("attr_knowledge") //知识
        ,TOUGHNESS("attr_toughness") //韧性
        ,VIGOUR("attr_vigour")//活力
        ;





        private String untranslateName="default";

        public String getUntranslateName() {
            return untranslateName;
        }

        public void setUntranslateName(String untranslateName) {
            this.untranslateName = untranslateName;
        }

        private ATTR_TYPE(String untranslateName){
            this.untranslateName=untranslateName;
        }
    }

    /**获取当前等级Cap
     *
     * @param currentLevel
     * @param baseLevelCapcity
     * @param growth
     * @return
     */
    public static int getPlayerLevelCapcity(int currentLevel,int baseLevelCapcity,float growth){
        if(0==currentLevel)return baseLevelCapcity;
        int total=0;
        for( int i=0;i<currentLevel;i++){
            if(0==total){
                total=baseLevelCapcity;
            }else{
                total=new BigDecimal(total*(1+growth)).setScale(0,BigDecimal.ROUND_FLOOR).intValue();
            }
        }
        return total;
    }

    /**是否为远程对象
     *
     * @return
     */
    public static boolean isRemote(){
        return Minecraft.getMinecraft().world.isRemote;
    }






}
