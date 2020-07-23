package fr.cooli.client.Manager;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.CooliMod;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class JobsHandler {
	
	public static int getBarValue(int xp, int maxxp) {
		int barvalue = (xp/maxxp) * 52;
		return (int)barvalue;
	}
	
}
