package fr.cooli.client.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.CooliMod;
import fr.cooli.client.gui.GuiJobs;
import fr.cooli.client.proxy.SQLConnector;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;

public class JobsManager {
	
	public static void arrange() {
		if(SQLConnector.hasAccount()) {
			Miner.setXP(SQLConnector.getXP("miner"));
			Builder.setXP(SQLConnector.getXP("builder"));
			LumberJack.setXP(SQLConnector.getXP("lumberjack"));
		}
	}
	
}
