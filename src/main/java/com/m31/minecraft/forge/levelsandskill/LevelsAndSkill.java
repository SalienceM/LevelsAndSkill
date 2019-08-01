package com.m31.minecraft.forge.levelsandskill;

import com.m31.minecraft.forge.levelsandskill.items.heart.Heart;
import com.m31.minecraft.forge.levelsandskill.proxy.CommonProxy;
import com.m31.minecraft.forge.levelsandskill.quality.Qualitys;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.m31.minecraft.forge.levelsandskill.LevelsAndSkill.*;

@Mod(modid =MOD_ID,
name = MOD_NAME,
version = MOD_VERSION)
public class LevelsAndSkill {
    public static final String MOD_ID="levelsandskill";
    public static final String MOD_NAME="LevelsAndSkill";
    public static final String MOD_VERSION="1.0-snapshot";

    /**forge 创建出的类实例
     *
     */
    @Mod.Instance(MOD_ID)
    public static LevelsAndSkill instance;

    //日志框架
    private static Logger logger;


    /**客户端、服务端代理
     *
     */
    @SidedProxy(clientSide = "com.m31.minecraft.forge.levelsandskill.proxy.ClientProxy",
            serverSide = "com.m31.minecraft.forge.levelsandskill.proxy.CommonProxy")
    public static CommonProxy proxy;


    public static CreativeTabs levelsAndSkillTab=new CreativeTabs("LevelsAndSkill") {
        @MethodsReturnNonnullByDefault
        public ItemStack createIcon() {
            //创造标签使用的图标、林业就是蜜蜂
            return new ItemStack(net.minecraft.init.Items.APPLE);
        }
    };




//    ==================================游戏阶段监听==================================

    /**预初始化
     *1、所有的mod内物品注册需要在这里完成
     * ·    1-1、有2种可行的物品注册方法
     *              a、经典：GameRegistry.registerTileEntity
     *              b、新版：RegistryEvent.Register
     *
     *
     * @param event
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event){
        this.logger=event.getModLog();
        proxy.preInit(event);
    }

    /**初始化
     * 1、合成表注册在这里完成
     *
     * @param event
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init(event);
    }

    /**初始化后监听
     *
     * @param event
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event){

    }


    /**方块声明器
     *  类中的方块必须以 public static final在此声明注册
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks{

    }


    /**物品声明器
     *  类中的物品必须以 public static final在此声明注册
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items{

    }

//    /**事件注册与监听、用于自定义
//     *
//     */
//    @Mod.EventBusSubscriber
//    public static class ObjectRegistryHandler{
//        public static void addItems(RegistryEvent.Register<Item> event){
//            event.getRegistry().register(new Heart(Optional.of(Qualitys.HEART_QUALITY_FULL)).setRegistryName("normal_heart_1"));
//            logger.info("normal_heart_1 init done");
//
//            //通过RegistryEvent.Register<T>注册物品，每个你编写的物品都要通过这种方式注册
//           /*
//             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
//             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
//           */
//        }
//
//        public static void addBlocks(RegistryEvent.Register<Block> event){
//            //通过RegistryEvent.Register<T>注册方块，每个你编写的方块都要通过这种方式注册
//          /*
//             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
//          */
//        }
//
//        /**Register<T>具有10种泛型类型参数：<Item>,<Block>，<Potion>（药水），<Biome>（生物群系），<SoundEvent>
//         （声音事件），<PotionType>（药水类型），<Enchantment>（附魔），<VillagerProfession>（村民职业），<EntityEntry>
//         （实体），<IRecipe>（合成表），你可以在ForgeRegistries类中看到这些泛型参数，也可以不使用泛型参数，直接用
//         RegisterEvent监听所有事件*/
//    }




































}
