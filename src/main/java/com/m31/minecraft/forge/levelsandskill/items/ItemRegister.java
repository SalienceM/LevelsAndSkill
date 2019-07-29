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
    public static Item halfHeart=new Heart(Optional.of(Qualitys.HEART_QUALITY_HALF))
            .setTranslationKey(TranslationUtil.getModTranslateString("half_heart"));

    public static void init(){
        ForgeRegistries.ITEMS.register(normalFullHeart.setRegistryName("normal_full_heart").setCreativeTab(LevelsAndSkill.levelsAndSkillTab));
        ForgeRegistries.ITEMS.register(halfHeart.setRegistryName("half_heart").setCreativeTab(LevelsAndSkill.levelsAndSkillTab));

    }
    public static void clientInit(){
        ModelLoader.setCustomModelResourceLocation(normalFullHeart,0,new ModelResourceLocation(normalFullHeart.getRegistryName(),INVENTORY));
        ModelLoader.setCustomModelResourceLocation(halfHeart,0,new ModelResourceLocation(halfHeart.getRegistryName(),INVENTORY));
    }



}
