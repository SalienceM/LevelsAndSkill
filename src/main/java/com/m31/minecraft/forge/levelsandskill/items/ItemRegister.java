package com.m31.minecraft.forge.levelsandskill.items;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.items.heart.Heart;
import com.m31.minecraft.forge.levelsandskill.quality.Qualitys;
import com.m31.minecraft.forge.levelsandskill.utils.TranslationUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Optional;


/**物品注册器
 *
 */
public class ItemRegister {
    public static final String INVENTORY="inventory";
    public static Item normalFullHeart=new Heart(Optional.of(Qualitys.HEART_QUALITY_FULL))
            .setTranslationKey(TranslationUtil.getModTranslateString("heart"));

    public static void init(){
        ForgeRegistries.ITEMS.register(normalFullHeart.setRegistryName("normal_full_heart").setCreativeTab(LevelsAndSkill.levelsAndSkillTab));

    }
    public static void clientInit(){
        //HEART regiser
        for(int i=0;i<Heart.combie_size+1;i++){
            ModelLoader.setCustomModelResourceLocation(normalFullHeart,i,new ModelResourceLocation(normalFullHeart.getRegistryName()+"_"+i,INVENTORY));
        }
    }



}
