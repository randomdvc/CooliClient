package fr.cooli.client;

import java.awt.color.ProfileDataException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.lwjgl.opengl.Display;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fr.cooli.client.network.PacketNavigator;
import fr.cooli.client.network.PacketNavigatorHandler;
import fr.cooli.client.proxy.ClientProxy;
import fr.cooli.client.proxy.SQLConnector;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Session;
import scala.reflect.internal.Trees.This;

@Mod(modid = Reference.MOD_ID, name  = Reference.MOD_NAME, version = Reference.VERSION)

public class CooliMod {
            
    public SQLConnector sql;
    
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static ClientProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {	
		
		
		proxy.RegisterRenderers();

		//need mysql driver

		this.sql = new SQLConnector("jdbc:mysql://", "host", "database", "username", "password");
		sql.connect();
							
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		
		
	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		
	}
	
	
}
