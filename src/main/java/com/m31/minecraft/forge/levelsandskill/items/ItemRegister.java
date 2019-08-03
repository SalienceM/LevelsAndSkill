package com.m31.minecraft.forge.levelsandskill.items;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.items.heart.Heart;
import com.m31.minecraft.forge.levelsandskill.items.skills.SkillBook;
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
    public static Item skillBook=new SkillBook().setTranslationKey(TranslationUtil.getModTranslateString("skill_book"));

    public static void init(){
        ForgeRegistries.ITEMS.register(normalFullHeart.setRegistryName("normal_full_heart").setCreativeTab(LevelsAndSkill.levelsAndSkillTab));
        ForgeRegistries.ITEMS.register(skillBook.setRegistryName("skill_book").setCreativeTab(LevelsAndSkill.levelsAndSkillTab));

    }
    public static void clientInit(){
        //HEART regiser
        for(int i=0;i<Heart.combie_size+1;i++){
            ModelLoader.setCustomModelResourceLocation(normalFullHeart,i,new ModelResourceLocation(normalFullHeart.getRegistryName()+"_"+i,INVENTORY));
        }
        //skillBook register
        ModelLoader.setCustomModelResourceLocation(skillBook,0,new ModelResourceLocation(skillBook.getRegistryName(),INVENTORY));

    }



}
