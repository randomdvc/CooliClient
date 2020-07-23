package fr.cooli.client.proxy;

import java.io.File;

import org.lwjgl.opengl.Display;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.ClientEventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy{
	
	@Override
	public void RegisterRenderers() {
		MinecraftForge.EVENT_BUS.register(new ClientEventManager());	
		
	}

	

}
