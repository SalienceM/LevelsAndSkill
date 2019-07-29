package com.m31.minecraft.forge.levelsandskill.trait;

public class Traits implements ITrait{
    public static final String[] heart_traits={
            "t0","t1",
    "t2","t3"};
    public static final String HEART_TRAITS_ROTTEN="t0";
    public static final String HEART_TRAITS_SHINING="t1";
    public static final String HEART_TRAITS_NATURE="t2";
    public static final String HEART_TRAITS_TEMP="t3";

    @Override
    public String getTraitName() {
        return null;
    }

    @Override
    public int getTraitIdentify() {
        return 0;
    }
}
