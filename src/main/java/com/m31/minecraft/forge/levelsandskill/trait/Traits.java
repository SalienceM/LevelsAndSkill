package com.m31.minecraft.forge.levelsandskill.trait;

public class Traits implements ITrait{
    public static final String[] heart_traits={
            "HEART_TRAITS_ROTTEN","HEART_TRAITS_SHINING",
    "HEART_TRAITS_NATURE","HEART_TRAITS_TEMP"};
    public static final String[] heart_traits_conflict={
            "HEART_TRAITS_ROTTEN,HEART_TRAITS_SHINING"
    };

    public static final String HEART_TRAITS_ROTTEN="HEART_TRAITS_ROTTEN";
    public static final String HEART_TRAITS_SHINING="HEART_TRAITS_SHINING";
    public static final String HEART_TRAITS_NATURE="HEART_TRAITS_NATURE";
    public static final String HEART_TRAITS_TEMP="HEART_TRAITS_TEMP";

    @Override
    public String getTraitName() {
        return null;
    }

    @Override
    public int getTraitIdentify() {
        return 0;
    }
}
