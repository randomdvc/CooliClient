package fr.cooli.client.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.Reference;
import fr.cooli.client.Manager.Builder;
import fr.cooli.client.Manager.JobsHandler;
import fr.cooli.client.Manager.JobsManager;
import fr.cooli.client.Manager.LumberJack;
import fr.cooli.client.Manager.Miner;
import fr.cooli.client.proxy.SQLConnector;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;


@SideOnly(Side.CLIENT)
public class GuiJobs extends GuiScreen{
	 		
	int guiWidth = 256;
	  int guiHeight = 158;
  
	  @Override
	  public void drawScreen(int x, int y, float ticks) {		 
		  
		  int guiX = (width - guiWidth) / 2;
		  int guiY = (height - guiHeight) / 2;
		  
		  int centerX = (width / 2) - guiWidth / 2;
	      int centerY = (height / 2) - guiHeight / 2;
		  drawDefaultBackground();
		  GL11.glColor4f(1, 1, 1, 10);
		 
          SQLConnector.createAccount();
		
		  mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/jobs_unified.png"));
          drawTexturedModalRect(guiX + 5, guiY + 5, 0, 0, guiWidth, guiHeight);	  
		 
          mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/job_icons.png"));
          drawTexturedModalRect(guiX + 50, guiY + 30, 140, -1, 30, 20);
          drawTexturedModalRect(guiX + 110, guiY + 30, 20, -1, 20, 20);
          drawTexturedModalRect(guiX + 170, guiY + 30, 120, -1, 20, 20);
         
          drawTexturedModalRect(guiX + 40, guiY + 60, 0, 20, 52, 11);          
          drawTexturedModalRect(guiX + 100, guiY + 60, 0, 20, 52, 11);
         drawTexturedModalRect(guiX + 160, guiY + 60, 0, 20, 52, 11);
         
         if(Miner.getXP() != 0) {
        	 float oneUnit = (float)50 / Miner.getMaxXP();
             int currentwidth = (int)(oneUnit * Miner.getXP());
              mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/job_icons.png"));    
                 drawTexturedModalRect(guiX + 41, guiY + 62, 60, 22, currentwidth, 9);
              }
         
         if(Builder.getXP() != 0) {
        	 float oneUnit = (float)50 / Builder.getMaxXP();
             int currentwidth = (int)(oneUnit * Builder.getXP());
              mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/job_icons.png"));    
                 drawTexturedModalRect(guiX + 101, guiY + 62, 60, 22, currentwidth, 9);
              }
         if(LumberJack.getXP() != 0) {
        	 float oneUnit = (float)50 / LumberJack.getMaxXP();
             int currentwidth = (int)(oneUnit * LumberJack.getXP());
              mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/job_icons.png"));    
                 drawTexturedModalRect(guiX + 161, guiY + 62, 60, 22, currentwidth, 9);
              }         
         
         GL11.glColor4f(1, 1, 1, 10);
         fontRendererObj.drawString("Miner", guiX + 55, guiY + 50, 0x404040);
         fontRendererObj.drawString("Level : " + Miner.getLevel(), guiX + 40, guiY + 75, 0x404040);
         fontRendererObj.drawString("Builder", guiX + 110, guiY + 50, 0x404040);
         fontRendererObj.drawString("Level : " + Builder.getLevel(), guiX + 105, guiY + 75, 0x404040);
         fontRendererObj.drawString("Lumberjack", guiX + 160, guiY + 50, 0x404040);
         fontRendererObj.drawString("Level : " + LumberJack.getLevel(), guiX + 160, guiY + 75, 0x404040);
         
         int b1Xminer = guiX + 40;
         int b1Yminer = guiY + 60;
         int b1Xfinminer = guiX + 92;
         int b1Yfinminer = guiY + 71;
         int b1Xbuilder = guiX + 100;
         int b1Ybuilder = guiY + 60;
         int b1Xfinbuilder = guiX + 152;
         int b1Yfinbuilder = guiY + 71;
         int b1Xlumberjack = guiX + 160;
         int b1Ylumberjack = guiY + 60;
         int b1Xfinlumberjack = b1Xlumberjack + 52;
         int b1Yfinlumberjack = guiY + 71;
         
         if(x >= b1Xminer && y >= b1Yminer && x <= b1Xfinminer && y <= b1Yfinminer) {   
        	 List<String> text = new ArrayList<String>();
             text.add(Miner.getXP() + "/" + Miner.getMaxXP());
             drawHoveringText(text, x, y, fontRendererObj);
         }
         if(x >= b1Xbuilder && y >= b1Ybuilder && x <= b1Xfinbuilder && y <= b1Yfinbuilder) {   
        	 List<String> text = new ArrayList<String>();
             text.add(Builder.getXP() + "/" + Builder.getMaxXP());
             drawHoveringText(text, x, y, fontRendererObj);
         }
         if(x >= b1Xlumberjack && y >= b1Ylumberjack && x <= b1Xfinlumberjack && y <= b1Yfinlumberjack) {   
        	 List<String> text = new ArrayList<String>();
             text.add(LumberJack.getXP() + "/" + LumberJack.getMaxXP());
             drawHoveringText(text, x, y, fontRendererObj);
         }
         	      
          super.drawScreen(x, y, ticks);
	  }
	   
	  
	  @Override
	  public void initGui() {
		  int guiX = (width - guiWidth) / 2;
		  int guiY = (height - guiHeight) / 2;
		  		  
          super.initGui();

	  }
	
	  
	
}
