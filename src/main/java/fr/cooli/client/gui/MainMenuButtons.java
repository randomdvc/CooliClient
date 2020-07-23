package fr.cooli.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MainMenuButtons extends GuiButton{

	protected static final ResourceLocation customTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/bouton.png");
	
	 
	 
	public MainMenuButtons(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_,
			String p_i1021_6_) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
	}
	
	  public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
	    {
	        if (this.visible)
	        {
	            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
	            //p_146112_1_.getTextureManager().bindTexture(customTexture);
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
	            int k = this.getHoverState(this.field_146123_n);
	            GL11.glEnable(GL11.GL_BLEND);
	            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            //this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
	            //this.drawTexturedModalRect(this.xPosition + 10, this.yPosition,15, 20, this.width - 20, this.height + 5);
	            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
	            int l = 14737632;
 
	            if (packedFGColour != 0)
	            {
	                l = packedFGColour;
	            }
	            else if (!this.enabled)
	            {
	                l = 10526880;
	            }
	            else if (this.field_146123_n)
	            {
	                l = 16777120;
	            }
 
	            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
	        }
	    }
}