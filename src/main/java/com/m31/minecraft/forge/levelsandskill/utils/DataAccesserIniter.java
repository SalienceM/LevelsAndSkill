package com.m31.minecraft.forge.levelsandskill.utils;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.events.PlayerEventBus;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;

public class DataAccesserIniter {
    public static final String PLAYER_EXTENTION="LevelsAndSkill";
    public static final String PLAYER_EXTENTION_HEART="Heart";
    public static final String PLAYER_EXTENTION_HEART_STATIC_ASFLOAT ="heart_static";
    public static final String PLAYER_EXTENTION_HEART_TEMP_ASFLOAT ="heart_temp";
    public static void initRoot(NBTTagCompound nbtTagCompound, EntityPlayerMP player){
        LogManager.getLogger(PlayerEventBus.class).info(new Gson().toJson(nbtTagCompound));
        NBTTagCompound persistent=nbtTagCompound.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        if(persistent.isEmpty()){
            nbtTagCompound.setTag(EntityPlayerMP.PERSISTED_NBT_TAG,persistent);
        }
        NBTTagCompound levelsandskill=persistent.getCompoundTag(PLAYER_EXTENTION);
        if(levelsandskill.isEmpty()){
            persistent.setTag(PLAYER_EXTENTION,intitLevelsAndSkillsPlayerExtentions(levelsandskill));
        }
    }
    /**根属性
     *
     * @param playerNbtTagCompound
     */
    private static void initLevelsAndSkillsPlayerExtentionsRootHeart(NBTTagCompound playerNbtTagCompound){
        if(playerNbtTagCompound.getCompoundTag(PLAYER_EXTENTION_HEART).isEmpty()){
            NBTTagCompound nbtTagCompoundPlayerHeartBuild=new NBTTagCompound();
            //maxHeart
            nbtTagCompoundPlayerHeartBuild.setFloat(PLAYER_EXTENTION_HEART_STATIC_ASFLOAT,0f);
            //maxTemp
            nbtTagCompoundPlayerHeartBuild.setFloat(PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,0f);
            playerNbtTagCompound.setTag(PLAYER_EXTENTION_HEART,nbtTagCompoundPlayerHeartBuild);
        }
    }

    /**初始化玩家属性标签
     * @param playerNbtTagCompound
     */
    private static NBTTagCompound intitLevelsAndSkillsPlayerExtentions(NBTTagCompound playerNbtTagCompound){
        //生命属性
        initLevelsAndSkillsPlayerExtentionsRootHeart(playerNbtTagCompound);
//        LogManager.getLogger().info("初始化完毕=>"+new Gson().toJson(playerNbtTagCompound));
        return playerNbtTagCompound;
    }







}
