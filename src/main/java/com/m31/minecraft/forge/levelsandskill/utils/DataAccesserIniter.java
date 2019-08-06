package com.m31.minecraft.forge.levelsandskill.utils;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.events.PlayerEventBus;
import com.m31.minecraft.forge.levelsandskill.items.Body;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;


public class DataAccesserIniter {
    public static void initRoot(NBTTagCompound nbtTagCompound, EntityPlayerMP player){
        LogManager.getLogger(PlayerEventBus.class).info(new Gson().toJson(nbtTagCompound));
        NBTTagCompound persistent=nbtTagCompound.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        if(persistent.isEmpty()){
            nbtTagCompound.setTag(EntityPlayerMP.PERSISTED_NBT_TAG,persistent);
        }
        NBTTagCompound levelsandskill=persistent.getCompoundTag(DataAccesser.PLAYER_EXTENTION);
            persistent.setTag(DataAccesser.PLAYER_EXTENTION,intitLevelsAndSkillsPlayerExtentions(levelsandskill));
    }
    /**根属性
     *
     * @param playerNbtTagCompound
     */
    private static void initLevelsAndSkillsPlayerExtentionsRootHeart(NBTTagCompound playerNbtTagCompound){
        if(playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).isEmpty()){
            NBTTagCompound nbtTagCompoundPlayerHeartBuild=new NBTTagCompound();
            //maxHeart
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_HEART_STATIC_ASFLOAT,0f);
            //maxTemp
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,0f);
            //revertHeart
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT,0f);
            playerNbtTagCompound.setTag(DataAccesser.PLAYER_EXTENTION_HEART,nbtTagCompoundPlayerHeartBuild);
        }else{
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).hasKey(DataAccesser.PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).setFloat(DataAccesser.PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT,0f);
            }
        }
    }

    /**根属性
     *
     * @param playerNbtTagCompound
     */
    private static void initLevelsAndSkillsPlayerExtentionsRootAttr(NBTTagCompound playerNbtTagCompound){
        if(playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE).isEmpty()){
            NBTTagCompound nbtTagCompoundPlayerHeartBuild=new NBTTagCompound();
            //strength
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_ATTR_STRENGTH_ASFLOAT, Body.ATTR_STRENGTH_DEFAULT);
            playerNbtTagCompound.setTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE,nbtTagCompoundPlayerHeartBuild);
        }else{
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE).hasKey(DataAccesser.PLAYER_EXTENTION_ATTR_STRENGTH_ASFLOAT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE).setFloat(DataAccesser.PLAYER_EXTENTION_ATTR_STRENGTH_ASFLOAT,Body.ATTR_STRENGTH_DEFAULT);
            }
        }
    }




    /**初始化玩家属性标签
     * @param playerNbtTagCompound
     */
    private static NBTTagCompound intitLevelsAndSkillsPlayerExtentions(NBTTagCompound playerNbtTagCompound){
        //生命属性
        initLevelsAndSkillsPlayerExtentionsRootHeart(playerNbtTagCompound);
        initLevelsAndSkillsPlayerExtentionsRootAttr(playerNbtTagCompound);

        return playerNbtTagCompound;
    }
}
