package com.m31.minecraft.forge.levelsandskill.container;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class LevelsGuiLoader implements IGuiHandler {
    public static final int GUI=1;

    /**注册mod
     */
    public LevelsGuiLoader(){
        NetworkRegistry.INSTANCE.registerGuiHandler(LevelsAndSkill.instance,this);
    }



    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        switch (i){
            case GUI:
                return new LevelsServerContainer(entityPlayer);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        switch (i){
            case GUI:
                return new LevelsClientContainer(new LevelsServerContainer(entityPlayer));
            default:
                return null;
        }
    }
}
