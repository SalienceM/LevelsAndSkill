package com.m31.minecraft.forge.levelsandskill.events;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesserIniter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = LevelsAndSkill.MOD_ID)
public class PlayerEventBus {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        LogManager.getLogger(PlayerEventBus.class).info("玩家登陆,初始化属性要素");
        //初始化模组属性
        DataAccesserIniter.initRoot(((EntityPlayerMP)event.player).getEntityData(), (EntityPlayerMP) event.player);
    }

    /**玩家重生
     *
     * @param event
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        LogManager.getLogger(PlayerEventBus.class).info(new Gson().toJson(event.player.getEntityData()));
        DataAccesser.setMaxHealth((EntityPlayerMP)event.player,DataAccesser.getPlayerMaxHealthRespawn(((EntityPlayerMP)event.player).getEntityData()));
        event.player.setHealth(DataAccesser.getPlayerMaxHealthRespawn(((EntityPlayerMP)event.player).getEntityData()));
    }

    //玩家Ticket事件
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerAttrChange(TickEvent.PlayerTickEvent event){
        //====================================
        Optional<EntityPlayerMP> playerMP=DataAccesser.getPlayerMpFromEntityPlayer(event.player,true);
        if(playerMP.isPresent()){
            if(playerMP.get().isEntityAlive()){
                //最大血量
                DataAccesser.setMaxHealth(playerMP.get(),DataAccesser.getPlayerMaxHealth(playerMP.get().getEntityData()));





                //1==================更新当前血量，仅仅MP更新
                float tempHealthAfter=event.player.getHealth()+DataAccesser.getPlayerHealthRevertSpeed(((EntityPlayerMP)event.player).getEntityData());
                float maxCurrent=DataAccesser.getPlayerMaxHealth(playerMP.get().getEntityData());
                if(tempHealthAfter>=maxCurrent){
                    event.player.setHealth(maxCurrent);
                }else{
                    event.player.setHealth(tempHealthAfter);
                }
            }
        }
        //1===========================
    }
}
