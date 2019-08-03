package com.m31.minecraft.forge.levelsandskill.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class LevelsServerContainer extends Container {
    public LevelsServerContainer(){
        super();
    }
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return new ItemStack(Items.BOOK).isItemEqual(entityPlayer.getHeldItemMainhand());
    }
}
