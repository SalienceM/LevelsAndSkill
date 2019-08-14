package com.m31.minecraft.forge.levelsandskill.utils;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.items.Body;
import com.m31.minecraft.forge.levelsandskill.items.heart.Heart;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;


public class DataAccesserIniter {
    public static void initRoot(NBTTagCompound nbtTagCompound, EntityPlayerMP player){
        LogManager.getLogger(DataAccesserIniter.class).info(new Gson().toJson(nbtTagCompound));
        NBTTagCompound persistent=nbtTagCompound.getCompoundTag(EntityPlayerMP.PERSISTED_NBT_TAG);
        if(persistent.isEmpty()){
            nbtTagCompound.setTag(EntityPlayerMP.PERSISTED_NBT_TAG,persistent);
        }
        NBTTagCompound levelsandskill=persistent.getCompoundTag(DataAccesser.PLAYER_EXTENTION);
            persistent.setTag(DataAccesser.PLAYER_EXTENTION,intitLevelsAndSkillsPlayerExtentions(levelsandskill,player));
            //==========================基本初始化完成后、进行属性填充
            //1、



    }
    /**根属性
     *
     * @param playerNbtTagCompound
     * @param player
     */
    private static void initLevelsAndSkillsPlayerExtentionsRootHeart(NBTTagCompound playerNbtTagCompound, EntityPlayerMP player){
        if(playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).isEmpty()){
            NBTTagCompound nbtTagCompoundPlayerHeartBuild=new NBTTagCompound();
            //maxHeart
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_HEART_STATIC_ASFLOAT, Heart.BaseHealth);
            //maxTemp
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,0f);
            //revertHeart
            nbtTagCompoundPlayerHeartBuild.setFloat(DataAccesser.PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT,0f);
            playerNbtTagCompound.setTag(DataAccesser.PLAYER_EXTENTION_HEART,nbtTagCompoundPlayerHeartBuild);
        }else{
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).hasKey(DataAccesser.PLAYER_EXTENTION_HEART_STATIC_ASFLOAT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).setFloat(DataAccesser.PLAYER_EXTENTION_HEART_STATIC_ASFLOAT,Heart.BaseHealth);
            }
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).hasKey(DataAccesser.PLAYER_EXTENTION_HEART_TEMP_ASFLOAT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).setFloat(DataAccesser.PLAYER_EXTENTION_HEART_TEMP_ASFLOAT,0f);
            }
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).hasKey(DataAccesser.PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_HEART).setFloat(DataAccesser.PLAYER_EXTENTION_HEART_REVERT_SPEED_ASFLOAT,0f);
            }

        }
    }

    /**根属性
     *
     * @param playerNbtTagCompound
     * @param player
     */
    private static void initLevelsAndSkillsPlayerExtentionsRootAttr(NBTTagCompound playerNbtTagCompound, EntityPlayerMP player){
        if(playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE).isEmpty()){
            NBTTagCompound nbtTagCompoundPlayerHeartBuild=new NBTTagCompound();
            //attr
            for(DataAccesser.ATTR_TYPE attr_type: DataAccesser.ATTR_TYPE.values()){
                nbtTagCompoundPlayerHeartBuild.setInteger(attr_type.getUntranslateName(), Body.ATTR_DEFAULT);
            }
            playerNbtTagCompound.setTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE,nbtTagCompoundPlayerHeartBuild);
        }else{
            for(DataAccesser.ATTR_TYPE attr_type: DataAccesser.ATTR_TYPE.values()){
                if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE).hasKey(attr_type.getUntranslateName())){
                    playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_ATTRIBUTE).setInteger(attr_type.getUntranslateName(),Body.ATTR_DEFAULT);
                }
            }
        }
    }
    private static void initLevelsAndSkillsPlayerExtentionsRootLevel(NBTTagCompound playerNbtTagCompound, EntityPlayerMP player){
        if(playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).isEmpty()){
            NBTTagCompound nbtTagCompoundPlayerHeartBuild=new NBTTagCompound();
            //LEVEL POINT TOTAL
            nbtTagCompoundPlayerHeartBuild.setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_POINT_ASINT, Body.LEVELPOINT_DEFAULT);
            //LEVEL
            nbtTagCompoundPlayerHeartBuild.setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_ASINT, player.experienceLevel);
            //LEVELMAX
            nbtTagCompoundPlayerHeartBuild.setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_MAX_ASINT, player.experienceLevel);
            //LEVELCurrentLevelCAP
            nbtTagCompoundPlayerHeartBuild.setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_CAP_ASINT, DataAccesser.getPlayerLevelCapcity(player.experienceLevel,Body.LEVELS_BASECAP,Body.LEVELS_CAP_GROWTH));
            //level point current
            nbtTagCompoundPlayerHeartBuild.setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_EXP_CURRENT_ASINT, Body.LEVELEXP_CURRENT_DEFAULT);
            playerNbtTagCompound.setTag(DataAccesser.PLAYER_EXTENTION_LEVEL,nbtTagCompoundPlayerHeartBuild);
        }else{
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).hasKey(DataAccesser.PLAYER_EXTENTION_LEVEL_POINT_ASINT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_POINT_ASINT,Body.LEVELPOINT_DEFAULT);
            }
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).hasKey(DataAccesser.PLAYER_EXTENTION_LEVEL_ASINT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_ASINT,player.experienceLevel);
            }
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).hasKey(DataAccesser.PLAYER_EXTENTION_LEVEL_MAX_ASINT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_MAX_ASINT,player.experienceLevel);
            }
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).hasKey(DataAccesser.PLAYER_EXTENTION_LEVEL_CAP_ASINT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_CAP_ASINT,DataAccesser.getPlayerLevelCapcity(player.experienceLevel,Body.LEVELS_BASECAP,Body.LEVELS_CAP_GROWTH));
            }
            if(!playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).hasKey(DataAccesser.PLAYER_EXTENTION_LEVEL_EXP_CURRENT_ASINT)){
                playerNbtTagCompound.getCompoundTag(DataAccesser.PLAYER_EXTENTION_LEVEL).setInteger(DataAccesser.PLAYER_EXTENTION_LEVEL_EXP_CURRENT_ASINT,Body.LEVELEXP_CURRENT_DEFAULT);
            }
        }
    }




    /**初始化玩家属性标签
     * @param playerNbtTagCompound
     * @param player
     */
    private static NBTTagCompound intitLevelsAndSkillsPlayerExtentions(NBTTagCompound playerNbtTagCompound, EntityPlayerMP player){
        //生命属性
        initLevelsAndSkillsPlayerExtentionsRootHeart(playerNbtTagCompound,player);
        //升级要素
        initLevelsAndSkillsPlayerExtentionsRootLevel(playerNbtTagCompound,player);
        //属性
        initLevelsAndSkillsPlayerExtentionsRootAttr(playerNbtTagCompound,player);

        return playerNbtTagCompound;
    }
}
