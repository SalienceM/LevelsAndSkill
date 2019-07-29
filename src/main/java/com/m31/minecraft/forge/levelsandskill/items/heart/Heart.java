package com.m31.minecraft.forge.levelsandskill.items.heart;

import com.m31.minecraft.forge.levelsandskill.quality.IQuality;
import com.m31.minecraft.forge.levelsandskill.quality.Qualitys;
import com.m31.minecraft.forge.levelsandskill.trait.ITrait;
import com.m31.minecraft.forge.levelsandskill.trait.Traits;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class Heart extends ItemFood {
    public static final float BaseHealth=20f;   //默认血量为20
    public static final float BaseMinHealth=6f;   //最小血量为6，三颗星
    public static final String QUALITY="Quality";

    private Optional<IQuality> heartQuality;
    private Set<Optional<ITrait>> heartTraits;

    public Heart(Optional<IQuality> heartQuality, Optional<ITrait>...args){
        super(0,false);
        this.maxStackSize=64;
        this.heartQuality=heartQuality;
        this.heartTraits=new HashSet<>(Arrays.asList(args));
        this.setAlwaysEdible();
        //
        NBTTagCompound nbt= this.getNBTShareTag(this.getDefaultInstance());
        nbt.setString(QUALITY,heartQuality.get().toString());
//        nbt.se
//        for (Optional<ITrait>)

    }

    @Override
    protected void onFoodEaten(ItemStack p_onFoodEaten_1_, World p_onFoodEaten_2_, EntityPlayer p_onFoodEaten_3_) {
        if(p_onFoodEaten_3_ instanceof EntityPlayerMP){
            EntityPlayerMP   entityPlayerMP=(EntityPlayerMP)p_onFoodEaten_3_;
            float addValue=0f;
            if(heartQuality.isPresent()){
                addValue=heartQuality.get()== Qualitys.HEART_QUALITY_FULL?2f:
                        heartQuality.get()== Qualitys.HEART_QUALITY_HALF?1f
                                :0f;
            }
            //客户端协助实现
            //增加最大血量
            if(!p_onFoodEaten_2_.isRemote) {
                entityPlayerMP.sendMessage(new TextComponentString("well done fresh meat!!!"));
                //血量提高
                DataAccesser.incrPlayerMaxHealth(entityPlayerMP.getEntityData(),addValue,
                        heartTraits.contains(Optional.of(Traits.HEART_TRAITS_TEMP))?true:false
                        );
                //设置最大血量
                DataAccesser.setMaxHealth(entityPlayerMP,DataAccesser.getPlayerMaxHealth(entityPlayerMP.getEntityData()));

                entityPlayerMP.sendMessage(new TextComponentString("now u have max heart>"+p_onFoodEaten_3_.getMaxHealth()));
            }
        }
    }


    @Override
    public void addInformation(ItemStack p_addInformation_1_, @Nullable World p_addInformation_2_, List<String> p_addInformation_3_, ITooltipFlag p_addInformation_4_) {

//        super.addInformation(p_addInformation_1_, p_addInformation_2_, p_addInformation_3_, p_addInformation_4_);
//        if()
//        p_addInformation_3_.add();
    }
}
