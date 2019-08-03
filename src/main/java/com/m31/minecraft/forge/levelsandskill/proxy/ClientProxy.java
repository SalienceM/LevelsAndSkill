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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy{
    static final String path= LevelsAndSkill.MOD_ID+":textures/gui/gui_data.png";
    static final ResourceLocation gui_texture=new ResourceLocation(path);
    private GuiButton exampleBtn =new GuiButton(233,0,0,"T");
    public static final KeyBinding levelsguiKeyBind=
            new KeyBinding(TranslationUtil.getModTranslateKeyBindString("levels_gui"),
                    Keyboard.KEY_L,TranslationUtil.getModTranslateKeyBindString("title"));

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
            mc.displayGuiScreen(new LevelsGui(mc.currentScreen));
        }
    }

    //监听玩家血条渲染
    @SubscribeEvent
    public void playerHealthRender(RenderGameOverlayEvent.Pre event){
        if(event.getType()== RenderGameOverlayEvent.ElementType.HEALTH){
            event.setCanceled(true);//组织原有渲染
            int width=event.getResolution().getScaledWidth();
            int height=event.getResolution().getScaledHeight();
            Minecraft mc=Minecraft.getMinecraft();
            int currenthp=MathHelper.ceil(mc.player.getHealth());
            int maxhp=MathHelper.ceil(mc.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
//            String hp = String.format("Health: %d/%d",currenthp,maxhp);
            if(null!=mc.currentScreen){
                GlStateManager.color(1f,1f,1f);
                mc.renderEngine.bindTexture(gui_texture);
//            mc.render
                mc.currentScreen.drawTexturedModalRect(5,5,
                        0,0,64,8);
            }
//                        FontRenderer fontRenderer = mc.fontRenderer;
//            fontRenderer.drawStringWithShadow(hp, width / 2 - 91, height - GuiIngameForge.left_height, 0xFFFFFF);
//            //字体渲染器在渲染时会重新绑定到字型纹理上,由于一些"编程失误",HUD在下一步绘制时不会重新绑定纹理,因此需要我们在此手动绑定.
//            mc.renderEngine.bindTexture(Gui.ICONS);
        }
    }




}
