package com.m31.minecraft.forge.levelsandskill.items.skills;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.container.LevelsGuiLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkillBook extends Item {
    public static Logger logger= LogManager.getLogger(SkillBook.class);



    @Override
    public ActionResult<ItemStack> onItemRightClick(World p_onItemRightClick_1_, EntityPlayer p_onItemRightClick_2_, EnumHand p_onItemRightClick_3_) {
        if(!p_onItemRightClick_1_.isRemote){
            BlockPos pos = p_onItemRightClick_2_.getPosition();
            int id = LevelsGuiLoader.GUI;
            p_onItemRightClick_2_.openGui(LevelsAndSkill.instance, id, p_onItemRightClick_1_, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.onItemRightClick(p_onItemRightClick_1_, p_onItemRightClick_2_, p_onItemRightClick_3_);
    }
}
