package com.m31.minecraft.forge.levelsandskill.proxy;

import com.m31.minecraft.forge.levelsandskill.gui.container.LevelsGuiLoader;
import com.m31.minecraft.forge.levelsandskill.items.ItemRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class CommonProxy {
    public Logger logger;

    public void preInit(FMLPreInitializationEvent event)
    {
        logger=event.getModLog();
        logger.info("doing CommonProxy preInit\n");
        ItemRegister.init();

    }
    public void init(FMLInitializationEvent event)
    {
        new LevelsGuiLoader();
        logger.info("doing CommonProxy Init\n");

    }



}
