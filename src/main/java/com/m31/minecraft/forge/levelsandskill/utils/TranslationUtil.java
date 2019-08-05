package com.m31.minecraft.forge.levelsandskill.utils;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TranslationUtil {
    public static Map<String,String> translateKeyCache=new ConcurrentHashMap<>();
    public static String getModTranslateString(String name){
        return LevelsAndSkill.MOD_ID+"."+name;
    }
    public static String getModTranslateAttrName(String attribute){
        return LevelsAndSkill.MOD_ID+".attr."+attribute;
    }
    public static String getModTranslateTraitsString(String name){
        return LevelsAndSkill.MOD_ID+".traits."+name;
    }
    public static String getModTranslateKeyBindString(String name){
        return LevelsAndSkill.MOD_ID+".keybind."+name;
    }
    public static String getFullModTranslateAttrName(String attrName){
        String targetKey=getModTranslateAttrName(attrName);
        return getTranslateKey(targetKey);
    }

    public static String getTranslateKey(String key){
        if(translateKeyCache.containsKey(key)){
            return translateKeyCache.get(key);
        }else{
            String content= new TextComponentTranslation(key).getFormattedText();
            translateKeyCache.put(key,content);
            return content;
        }
    }





}
