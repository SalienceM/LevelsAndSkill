package com.m31.minecraft.forge.levelsandskill.proxy;

import com.m31.minecraft.forge.levelsandskill.items.ItemRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ItemRegister.clientInit();

    }
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }
}
