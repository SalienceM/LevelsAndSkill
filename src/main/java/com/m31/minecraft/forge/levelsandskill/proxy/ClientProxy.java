package com.m31.minecraft.forge.levelsandskill.proxy;

import com.m31.minecraft.forge.levelsandskill.LevelsAndSkill;
import com.m31.minecraft.forge.levelsandskill.gui.LevelsGui;
import com.m31.minecraft.forge.levelsandskill.gui.SimpleGui;
import com.m31.minecraft.forge.levelsandskill.items.ItemRegister;
import com.m31.minecraft.forge.levelsandskill.utils.TranslationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientProxy extends CommonProxy{
    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_data.png";
//    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_skill.png";
    static final ResourceLocation gui_texture=new ResourceLocation(path);
    private GuiButton exampleBtn =new GuiButton(233,0,0,"T");
    public static final KeyBinding levelsguiKeyBind=
            new KeyBinding(TranslationUtil.getModTranslateKeyBindString("levels_gui"),
                    Keyboard.KEY_L,TranslationUtil.getModTranslateKeyBindString("title"));
    private ExecutorService executorService= Executors.newFixedThreadPool(10);



    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ItemRegister.clientInit();

    }
    public void init(FMLInitializationEvent event)
    {
        //注册本类中的监听事件到forge系统中
        MinecraftForge.EVENT_BUS.register(this);
        //注册按键事件监听 InputEvent.KeyInputEvent event
        // FMLCommonHandler.instance().bus().register(this);
        //注册界面快捷绑定
        ClientRegistry.registerKeyBinding(levelsguiKeyBind);
        super.init(event);
    }

    //监听gui渲染事件
    @SubscribeEvent
    public void guiScreenShow(GuiScreenEvent.InitGuiEvent.Post event){
//        if(event.getGui() instanceof GuiInventory)
        if(event.getGui() instanceof GuiMainMenu){
            GuiScreen screen=event.getGui();
//            for(GuiButton btn:event.getButtonList()){
//                if(4==btn.id){
//                    btn.x= (int) (screen.width*0.75);
//                }else if(0==btn.id){
//                    btn.y= (int) (screen.height*.7);
//                    btn.x= (int) (screen.width*.75);
//                }
//            }
            exampleBtn.x= 0;
            exampleBtn.y= 0;
            exampleBtn.width=20;
            event.getButtonList().add(exampleBtn);
        }
    }

    //监听按钮点击事件
    @SubscribeEvent
    public void guiClickButton(GuiScreenEvent.ActionPerformedEvent.Post event){
        if(exampleBtn ==event.getButton()){
            Minecraft mc=Minecraft.getMinecraft();
            mc.displayGuiScreen(new SimpleGui(mc.currentScreen));
        }
    }

    //监听键盘输入事件
    @SubscribeEvent
    public void keyListener(InputEvent.KeyInputEvent event){
        if(levelsguiKeyBind.isPressed()){
            Minecraft mc=Minecraft.getMinecraft();
            mc.displayGuiScreen(new LevelsGui(mc.currentScreen,mc.player));
        }
    }

//    监听玩家血条渲染
    @SubscribeEvent
    public void playerHealthRender(RenderGameOverlayEvent.Pre event){
        if(event.getType()==RenderGameOverlayEvent.ElementType.FOOD){
            Minecraft.getMinecraft().renderEngine.bindTexture(Gui.ICONS);
            return;
        } else if(event.getType()== RenderGameOverlayEvent.ElementType.HEALTH){
                                event.setCanceled(true);//组织原有渲染
                    int width=event.getResolution().getScaledWidth();
                    int height=event.getResolution().getScaledHeight();
                    int currenthp=MathHelper.ceil(Minecraft.getMinecraft().player.getHealth());
                    int maxhp=MathHelper.ceil(Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
                        GlStateManager.color(1f,1f,1f);
                        Minecraft.getMinecraft().getTextureManager().bindTexture(gui_texture);
                        //绘制血量
//                         血量百分比
                        float hpper=(float) currenthp/maxhp;
                        int hplength=MathHelper.ceil(92*hpper);
                        //不同百分比展示不同贴图
                        if(hpper>=0.75){
                            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(width/2-92,height - GuiIngameForge.left_height-10,
                                    0,8,hplength,8);
                        }else if(hpper>=0.5){
                            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(width/2-92,height - GuiIngameForge.left_height-10,
                                    0,16,hplength,8);
                        }else if(hpper>=0.25){
                            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(width/2-92,height - GuiIngameForge.left_height-10,
                                    0,24,hplength,8);
                        }else { //<=0.25
                            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(width/2-92,height - GuiIngameForge.left_height-10,
                                    0,32,hplength,8);
                        }

                        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(width/2-92,height - GuiIngameForge.left_height-10,
                                0,0,92,8);
                        String hp = "Health: "+MathHelper.ceil(hpper*100)+"% "+String.format("%d/%d",currenthp,maxhp);
                        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
                        fontRenderer.drawStringWithShadow(hp, width / 2 - 91, height - GuiIngameForge.left_height-20, 0xFFFFFF);
            Minecraft.getMinecraft().renderEngine.bindTexture(Gui.ICONS);
        }
    }




}
