package com.m31.minecraft.forge.levelsandskill.utils;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.events.PlayerEventBus;
import com.m31.minecraft.forge.levelsandskill.items.heart.Heart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;

public class DataAccesser {
    public static final String PLAYER_EXTENTION="LevelsAndSkill";
    public static final String PLAYER_EXTENTION_HEART="Heart";
    public static final String PLAYER_EXTENTION_HEART_STATIC_ASFLOAT ="heart_static";
    public static final String PLAYER_EXTENTION_HEART_TEMP_ASFLOAT ="heart_temp";
    public static final String TRAITS="Traits";

    /**从playerRoot标签中获取Heart 节点
     *
     * @param root
     * @return
     */
    private static NBTTagCompound getRootHeart(NBTTagCompound root){
       return root.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(PLAYER_EXTENTION).getCompoundTag(PLAYER_EXTENTION_HEART);
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

}
