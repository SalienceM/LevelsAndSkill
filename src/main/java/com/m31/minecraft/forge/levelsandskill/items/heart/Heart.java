package com.m31.minecraft.forge.levelsandskill.items.heart;

import com.google.gson.Gson;
import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.quality.IQuality;
import com.m31.minecraft.forge.levelsandskill.quality.Qualitys;
import com.m31.minecraft.forge.levelsandskill.trait.ITrait;
import com.m31.minecraft.forge.levelsandskill.trait.Traits;
import com.m31.minecraft.forge.levelsandskill.utils.DataAccesser;
import com.m31.minecraft.forge.levelsandskill.utils.Splitor;
import com.m31.minecraft.forge.levelsandskill.utils.TranslationUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Heart extends ItemFood {
    public static int minTraitSize=1;
    public static int maxTraitSize=3;
    public static int combie_size;
    public static Logger logger= LogManager.getLogger(Heart.class);
    public static Map<String,String> translateKeyCache=new ConcurrentHashMap<>();
    static{
        //初始化全组合序列
        for(int i=minTraitSize;i<=maxTraitSize;i++){
            List<String> strings=Splitor.getCombies(i,Traits.heart_traits,Traits.heart_traits_conflict);
            for(String strIndex:strings){
                combie_size++;
            }
        }
        logger.info("all Heart Combies calculate done. size {}",combie_size);
    }
    public static final float BaseHealth=20f;   //默认血量为20
    public static final float BaseHealthRevertSpeed=0f;   //默认血量为20
    public static final float BaseTempHealth=0f;   //默认血量为20
    public static final float BaseMinHealth=6f;   //最小血量为6，三颗星

    private Optional<String> heartQuality;
    private List<Optional<String>> heartTraits;

    public Heart(Optional<String> heartQuality, Optional<String>...args){
        super(0,false);
        this.maxStackSize=64;
        this.heartQuality=heartQuality;
        this.heartTraits=new LinkedList<>(Arrays.asList(args));
        this.setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack p_onFoodEaten_1_, World p_onFoodEaten_2_, EntityPlayer p_onFoodEaten_3_) {
        if(p_onFoodEaten_3_ instanceof EntityPlayerMP){
            EntityPlayerMP   entityPlayerMP=(EntityPlayerMP)p_onFoodEaten_3_;
            float addValue=0f;
            if(heartQuality.isPresent()){
                addValue=heartQuality.get().equals(Qualitys.HEART_QUALITY_FULL)?2f:
                        heartQuality.get().equals(Qualitys.HEART_QUALITY_HALF)?1f
                                :0f;
            }
            //客户端协助实现
            //增加最大血量
            if(!p_onFoodEaten_2_.isRemote) {
                entityPlayerMP.sendMessage(new TextComponentString("well done fresh meat!!!"));
                //血量提高
                boolean ifWeek=DataAccesser.ifHasSubTargetTraitNBTTagCompound(p_onFoodEaten_1_.getTagCompound(),"heart_modify",Traits.HEART_TRAITS_TEMP);
                DataAccesser.incrPlayerMaxHealth(entityPlayerMP.getEntityData(),addValue,
                        ifWeek?true:false
                        );
                //设置最大血量
                DataAccesser.setMaxHealth(entityPlayerMP,DataAccesser.getPlayerMaxHealth(entityPlayerMP.getEntityData()));

                entityPlayerMP.sendMessage(new TextComponentString("now u have max heart>"+p_onFoodEaten_3_.getMaxHealth()));
            }
        }
    }


    @Override
    public void addInformation(ItemStack p_addInformation_1_, @Nullable World p_addInformation_2_, List<String> p_addInformation_3_, ITooltipFlag p_addInformation_4_) {
        super.addInformation(p_addInformation_1_, p_addInformation_2_, p_addInformation_3_, p_addInformation_4_);
        if(null==p_addInformation_1_.getTagCompound())return;
        NBTTagCompound nbtTagCompound=p_addInformation_1_.getTagCompound().getCompoundTag("heart_modify");
        for(String str:nbtTagCompound.getKeySet()){
//            I18n.translateToLocal(this.getTranslationKey(p_getUnlocalizedNameInefficiently_1_));
            String key=TranslationUtil.getModTranslateTraitsString(nbtTagCompound.getString(str).toLowerCase());
            if(translateKeyCache.containsKey(key)){
                p_addInformation_3_.add(translateKeyCache.get(key));
            }else{
                String content= new TextComponentTranslation(key).getFormattedText();
                p_addInformation_3_.add(content);
                translateKeyCache.put(key,content);
            }
        }
    }
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
    //新建一批同一id但是不同meta的物品，例如原版染料和染色羊毛
        if (this.isInCreativeTab(tab)){
            ItemStack itemStackOrigin=new ItemStack(this,1,0);
            items.add(itemStackOrigin);
            int index=1;
            for(int i=minTraitSize;i<=maxTraitSize;i++){
                List<String> traitCombies=Splitor.getCombies(i,Traits.heart_traits,Traits.heart_traits_conflict);
                for(String traitCombie:traitCombies){
                    ItemStack itemStackTemp=new ItemStack(this,1,index);
                    itemStackTemp.setTagCompound(DataAccesser.buildTargetTraitNBTTagCompound("heart_modify",
                            traitCombie.split(",")));
                    items.add(itemStackTemp);
                    index++;
                }
            }





        }
//            for (int i = 0; i < 16; ++i)
//                items.add(new ItemStack(this,))
//                items.add(new ItemStack(this, 1, i));

    }

}
