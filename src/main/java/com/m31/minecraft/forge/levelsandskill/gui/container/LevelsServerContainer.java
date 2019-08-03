package com.m31.minecraft.forge.levelsandskill.gui.container;

import com.m31.minecraft.forge.levelsandskill.items.ItemRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LevelsServerContainer extends Container {
    public LevelsServerContainer(){
        super();
    }
    public LevelsServerContainer(EntityPlayer player){
        super();
        drawItemSlot(player);
    }
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return new ItemStack(ItemRegister.skillBook).isItemEqual(entityPlayer.getHeldItemMainhand());
    }


    private void drawItemSlot(EntityPlayer player){
        for( int i=0;i<9;i++){
            this.addSlotToContainer(new Slot(player.inventory,i,8+i*18,109));
        }
    }

}
