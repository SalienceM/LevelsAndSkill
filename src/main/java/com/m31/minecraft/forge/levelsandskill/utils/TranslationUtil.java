package com.m31.minecraft.forge.levelsandskill.utils;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;

public class TranslationUtil {

    public static String getModTranslateString(String name){
        return LevelsAndSkill.MOD_ID+"."+name;
    }
    public static String getModTranslateTraitsString(String name){
        return LevelsAndSkill.MOD_ID+".traits."+name;
    }
    public static String getModTranslateKeyBindString(String name){
        return LevelsAndSkill.MOD_ID+".keybind."+name;
    }

}
