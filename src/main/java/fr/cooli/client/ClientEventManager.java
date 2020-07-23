package fr.cooli.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.Manager.Builder;
import fr.cooli.client.Manager.Miner;
import fr.cooli.client.gui.CooliGuiConnecting;
import fr.cooli.client.gui.GuiIngameMenuC;
import fr.cooli.client.gui.GuiMainMenuC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.BlockEvent;


@SideOnly(Side.CLIENT)
public class ClientEventManager {
	
	Logger logger = LogManager.getLogger();
	
	NetHandlerPlayClient p_i45023_1_ = Minecraft.getMinecraft().getNetHandler();
	  
	    Minecraft mc = Minecraft.getMinecraft();  

	    private final OldServerPinger field_146797_f = new OldServerPinger();
	    private GuiScreen field_146798_g;
	    private ServerSelectionList field_146803_h;
	    private ServerList field_146804_i;
	    private GuiButton field_146810_r;
	    private GuiButton field_146809_s;
	    private GuiButton field_146808_t;
	    private boolean field_146807_u;
	    private boolean field_146806_v;
	    private boolean field_146805_w;
	    private boolean field_146813_x;
	    private String field_146812_y;
	    private ServerData field_146811_z;
	    private LanServerDetector.LanServerList field_146799_A;
	    private LanServerDetector.ThreadLanServerFind field_146800_B;
	    private boolean field_146801_C;
	    private static final String __OBFID = "CL_00000814";
	  
	@SubscribeEvent
    public void onGuiOpens(GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu) event.gui = new GuiMainMenuC(); else
          if (event.gui instanceof GuiIngameMenu) event.gui = new GuiIngameMenuC(); else
            if (event.gui instanceof GuiMultiplayer) event.gui = new GuiMainMenuC(); else
        	  if (event.gui instanceof GuiConnecting) event.gui = new CooliGuiConnecting(new GuiMainMenuC(), Minecraft.getMinecraft(), GuiMainMenuC.server);
    }
    
    @SubscribeEvent
    public void onBlockBroke(BlockEvent.BreakEvent e) {
		if(Miner.getLevel() == 0 || Miner.getLevel() == 1 || Miner.getLevel() == 2 || Miner.getLevel() == 3 || Miner.getLevel() == 4 || Miner.getLevel() == 5) {
    	if(e.block == Blocks.stone) {
    		Miner.addxp(1, e.getPlayer());
    	}
    	if(e.block == Blocks.coal_ore) {
    		Miner.addxp(2, e.getPlayer());
    	}
    	if(e.block == Blocks.iron_ore) {
    		Miner.addxp(3, e.getPlayer());
    	}
    	if(e.block == Blocks.gold_ore) {
    		Miner.addxp(4, e.getPlayer());
    	}
    	if(e.block == Blocks.diamond_ore) {
    		Miner.addxp(5, e.getPlayer());
    	}
    	if(e.block == Blocks.emerald_ore) {
    		Miner.addxp(10, e.getPlayer());
    	}
		}
    	
    	if(Miner.getLevel() == 3 || Miner.getLevel() == 4 || Miner.getLevel() == 5) {
    		//minerai bleu // lazerium // coolium
        	if(e.block == Blocks.gold_ore) {
        		Miner.addxp(2, e.getPlayer());
        	}
        	if(e.block == Blocks.iron_ore) {
        		Miner.addxp(3, e.getPlayer());
        	}
        	if(e.block == Blocks.diamond_ore) {
        		Miner.addxp(4, e.getPlayer());
        	}
        	if(e.block == Blocks.emerald_ore) {
        		Miner.addxp(5, e.getPlayer());
        	}
    	}
    	
    	  	
			
    }
    
    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent e) {
		if(Builder.getLevel() == 0 || Builder.getLevel() == 1 || Builder.getLevel() == 2) {
	    	if(e.block == Blocks.stone) {
	    		Builder.addxp(1, e.player);
	    	}
	    	if(e.block == Blocks.coal_ore) {
	    		Builder.addxp(2, e.player);
	    	}
			}
	    	
	    	if(Builder.getLevel() == 3 || Builder.getLevel() == 4) {
	        	if(e.block == Blocks.iron_ore) {
	        		Builder.addxp(3, e.player);
	        	}
	        	if(e.block == Blocks.gold_ore) {
	        		Builder.addxp(4, e.player);
	        	}
	    	}
	    	
	    	if(Builder.getLevel() == 5) {
	        	if(e.block == Blocks.diamond_ore) {
	        		Builder.addxp(4, e.player);
	        	}
	        	
	    	}
    	 	
    }
   
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent.Pre event)
    {
    	int xcord = (int) mc.thePlayer.posX;
        int ycord = (int) mc.thePlayer.posY;
        int zcord = (int) mc.thePlayer.posZ;
        if(event.type == RenderGameOverlayEvent.ElementType.DEBUG)
    {      	
        event.setCanceled(true);
        int angle= MathHelper.floor_double((double) (Minecraft.getMinecraft().thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        String direction = Direction.directions[angle];
        String fpsString = mc.debug.split(",", 2)[0];
        this.drawString(Minecraft.getMinecraft().fontRenderer, "Cooli", 10, 10, 14737632);
        this.drawString(Minecraft.getMinecraft().fontRenderer, fpsString + ", " + "X: " + xcord + ", " + "Y: " + ycord + ", " + "Z: " + zcord, 10, 20, 14737632);
        this.drawString(Minecraft.getMinecraft().fontRenderer, "Biome: " + mc.theWorld.getBiomeGenForCoords(xcord, zcord).biomeName , 10, 30, 14737632);
        this.drawString(Minecraft.getMinecraft().fontRenderer, "Direction: " + direction, 10, 40, 14737632);

    }
        
        
    }
      
    public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
    {
        par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
    }
 
 

}