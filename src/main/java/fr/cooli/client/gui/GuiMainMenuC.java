package fr.cooli.client.gui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.CooliMod;
import fr.cooli.client.Reference;
//import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenAddServer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.main.Main;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

@SideOnly(Side.CLIENT)
public class GuiMainMenuC extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger = LogManager.getLogger();
    /** The RNG used by the Main Menu Screen. */
    private static final Random rand = new Random();
    /** Counts the number of screen updates. */
    private float updateCounter;
    /** The splash message. */
    private String splashText;
    private GuiButton buttonResetDemo;
    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer;
    /** Texture allocated for the current viewport of the main menu's panorama background. */
    private DynamicTexture viewportTexture;
    private final Object field_104025_t = new Object();
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    /** An array of all the paths to the panorama pictures. */
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/background.jpg");
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation field_110351_G;
    private static final String __OBFID = "CL_00001154";
    private int scrollingTextPosX;
    private String scrollingText;
    public static ServerData server = new ServerData("ip without port", "ip:port", true);
    public static ServerData localserver = new ServerData("localhost", "localhost:25565", false);
    private static final ThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build()); 

    public GuiMainMenuC()
    {
        this.field_146972_A = field_96138_a;
        this.splashText = "Bienvenue sur Cooli";
        BufferedReader bufferedreader = null;

        this.updateCounter = rand.nextFloat();
        this.field_92025_p = "";
        
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    GuiMainMenuC.this.scrollingText =  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
                }
                catch(Exception e)
                {
                    GuiMainMenuC.this.scrollingText = "Impossible to read the text";
                }
            }
        }.start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.panoramaTimer;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        boolean flag = true;
        int i = this.height / 4 + 48;
  
            this.addSingleplayerMultiplayerButtons(i, 24);       

       this.buttonList.add(new GuiButton(0, this.width / 2 - 100, i + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, i + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, i + 72 + 12));
        Object object = this.field_104025_t;

        synchronized (this.field_104025_t)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int j = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - j) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + j;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {      
    	this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.singleplayer", new Object[0])));
    	this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("menu.multiplayer", new Object[0])));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (p_146284_1_.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (p_146284_1_.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (p_146284_1_.id == 2)
        {   	
            FMLClientHandler.instance().connectToServer(new GuiMultiplayer(this), server);
        }

        if (p_146284_1_.id == 14)
        {
            this.func_140005_i();
        }

        if (p_146284_1_.id == 4)
        {
            this.mc.shutdown();
        }

        if (p_146284_1_.id == 6)
        {
            this.mc.displayGuiScreen(new GuiModList(this));
        }

        if (p_146284_1_.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (p_146284_1_.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    private void func_146791_a(ServerData p_146791_1_)
    {
    }

    private void func_140005_i()
    {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean p_73878_1_, int p_73878_2_)
    {
        if (p_73878_1_ && p_73878_2_ == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (p_73878_2_ == 13)
        {
            if (p_73878_1_)
            {
                try
                {
                    Class oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.field_104024_v)});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }  

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport(0, 0, 256, 256);
       
        this.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float f1 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float f2 = (float)this.height * f1 / 256.0F;
        float f3 = (float)this.width * f1 / 256.0F;
        tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int k = this.width;
        int l = this.height;
        tessellator.addVertexWithUV(0.0D, (double)l, (double)this.zLevel, (double)(0.5F - f2), (double)(0.5F + f3));
        tessellator.addVertexWithUV((double)k, (double)l, (double)this.zLevel, (double)(0.5F - f2), (double)(0.5F - f3));
        tessellator.addVertexWithUV((double)k, 0.0D, (double)this.zLevel, (double)(0.5F + f2), (double)(0.5F - f3));
        tessellator.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(0.5F + f2), (double)(0.5F + f3));
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	
		Display.setTitle("Cooli" + " - " + mc.getSession().getUsername());
		
    	 mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.func_152125_a(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);

        Tessellator tessellator = Tessellator.instance;
        short short1 = 274;
        int k = this.width / 2 - short1 / 2;
        byte b0 = 30;
        //this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        //this.mc.renderEngine.bindTexture(minecraftTitleTextures);
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/logo.png"));
        drawTexturedModalRect(k + 100, b0 - 20, 0, 0, 200, 200);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        tessellator.setColorOpaque_I(-1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float f1 = 1.8F - MathHelper.abs(MathHelper.sin((float)(mc.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        f1 = f1 * 100.0F / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GL11.glScalef(f1, f1, f1);
        this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
        GL11.glPopMatrix();
        //String s = "Cooli";        
            
        this.drawString(this.fontRendererObj, "Cooli", 2, this.height - ( 10 + 0 * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
            
        
        String s1 = "Copyright CooliPvP x Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);

        if (this.field_92025_p != null && this.field_92025_p.length() > 0)
        {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
        }

        
        this.scrollingTextPosX --;
        if(this.scrollingTextPosX < -this.fontRendererObj.getStringWidth(this.server.serverMOTD))
        {
            this.scrollingTextPosX = this.width;
        }
        this.drawRect(0, 0, this.width, 12, 0x77FFFFFF);
        this.fontRendererObj.drawString(this.scrollingText, this.scrollingTextPosX, 2, 0x245791);

        


        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        Object object = this.field_104025_t;

        synchronized (this.field_104025_t)
        {
            if (this.field_92025_p.length() > 0 && p_73864_1_ >= this.field_92022_t && p_73864_1_ <= this.field_92020_v && p_73864_2_ >= this.field_92021_u && p_73864_2_ <= this.field_92019_w)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                guiconfirmopenlink.func_146358_g();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}
