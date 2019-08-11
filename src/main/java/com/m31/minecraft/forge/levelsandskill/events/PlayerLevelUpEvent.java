package com.m31.minecraft.forge.levelsandskill.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 *
 */
@Cancelable
public class PlayerLevelUpEvent extends PlayerEvent {
    private int oldLevel;
    private int currentLevel;
//    boolean result= MinecraftForge.EVENT_BUS.post(new PlayerLevelUpEvent(getEntityPlayer(),getOldLevel(),getCurrentLevel()));

    public int getOldLevel() {
        return oldLevel;
    }

    public void setOldLevel(int oldLevel) {
        this.oldLevel = oldLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public PlayerLevelUpEvent(EntityPlayer player,int oldLevel,int currentLevel) {
        super(player);
        this.oldLevel=oldLevel;
        this.currentLevel=currentLevel;
    }
}
