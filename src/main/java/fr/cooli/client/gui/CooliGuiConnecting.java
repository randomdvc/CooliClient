package fr.cooli.client.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.CooliMod;
import fr.cooli.client.Reference;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

@SideOnly(Side.CLIENT)
public class CooliGuiConnecting extends GuiScreen
{
    private static final AtomicInteger field_146372_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private NetworkManager field_146371_g;
    private boolean field_146373_h;
    private final GuiScreen field_146374_i;
    private static final String __OBFID = "CL_00000685";

    public CooliGuiConnecting(GuiScreen p_i1181_1_, Minecraft p_i1181_2_, ServerData p_i1181_3_)
    {
        this.mc = p_i1181_2_;
        this.field_146374_i = p_i1181_1_;
        ServerAddress serveraddress = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
        p_i1181_2_.loadWorld((WorldClient)null);
        p_i1181_2_.setServerData(p_i1181_3_);
        this.func_146367_a(serveraddress.getIP(), serveraddress.getPort());
    }

    public CooliGuiConnecting(GuiScreen p_i1182_1_, Minecraft p_i1182_2_, String p_i1182_3_, int p_i1182_4_)
    {
        this.mc = p_i1182_2_;
        this.field_146374_i = p_i1182_1_;
        p_i1182_2_.loadWorld((WorldClient)null);
        this.func_146367_a(p_i1182_3_, p_i1182_4_);
    }

    private void func_146367_a(final String p_146367_1_, final int p_146367_2_)
    {
        logger.info("Connecting to Cooli");
        (new Thread("Server Connector #" + field_146372_a.incrementAndGet())
        {
            private static final String __OBFID = "CL_00000686";
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {
                    if (CooliGuiConnecting.this.field_146373_h)
                    {
                        return;
                    }

                    inetaddress = InetAddress.getByName(p_146367_1_);
                    CooliGuiConnecting.this.field_146371_g = NetworkManager.provideLanClient(inetaddress, p_146367_2_);
                    CooliGuiConnecting.this.field_146371_g.setNetHandler(new NetHandlerLoginClient(field_146371_g, mc, field_146374_i));
                    CooliGuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00Handshake(5, p_146367_1_, p_146367_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
                    CooliGuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00PacketLoginStart(mc.getSession().func_148256_e()) , new GenericFutureListener[0]);
                }
                catch (UnknownHostException unknownhostexception)
                {
                    if (CooliGuiConnecting.this.field_146373_h)
                    {
                        return;
                    }

                    CooliGuiConnecting.logger.error("Couldn\'t connect to server", unknownhostexception);
                    CooliGuiConnecting.this.mc.displayGuiScreen(new CooliGuiDisconnected(field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (Exception exception)
                {
                    if (CooliGuiConnecting.this.field_146373_h)
                    {
                        return;
                    }

                    CooliGuiConnecting.logger.error("Couldn\'t connect to server", exception);
                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress.toString() + ":" + p_146367_2_;
                        s = s.replaceAll(s1, "");
                    }

                    CooliGuiConnecting.this.mc.displayGuiScreen(new CooliGuiDisconnected(field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
            }
        }).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.field_146371_g != null)
            if (this.field_146371_g.isChannelOpen())
            {
                this.field_146371_g.processReceivedPackets();
            }
            else if (this.field_146371_g.getExitMessage() != null)
            {
                this.field_146371_g.getNetHandler().onDisconnect(this.field_146371_g.getExitMessage());
            }
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
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 50, I18n.format("gui.cancel", new Object[0])));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 0)
        {
            this.field_146373_h = true;

            if (this.field_146371_g != null)
            {
                this.field_146371_g.closeChannel(new ChatComponentText("Aborted"));
            }

            this.mc.displayGuiScreen(this.field_146374_i);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
    	mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/background1.jpg"));
    	 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.func_152125_a(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);

        if (this.field_146371_g == null)
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}