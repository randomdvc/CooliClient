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

@SideOnly(Side.CLIENT)
public class Builder {
	
	public static int Level;

	 public static EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		private static int maxxp;
		
		public Builder() {
					 
		}
	
		public static int getMaxXP() {
			if(getLevel() == 0) {
				maxxp = 800;
			} else if(getLevel() == 1) {
				maxxp = 1200;
			}else if(getLevel() == 2) {
				maxxp = 5600;
			}else if(getLevel() == 3) {
				maxxp = 12000;
			}else if(getLevel() == 4) {
				maxxp = 15000;
			}else if(getLevel() == 5) {
				maxxp = 19250;
			}else if(getLevel() == 6) {
				maxxp = 23875;
			}else if(getLevel() == 7) {
				maxxp = 32940;
			}else if(getLevel() == 8) {
				maxxp = 41000;
			}else if(getLevel() == 9) {
				maxxp = 54580;
			}
			return maxxp;
		}
	
		
		public static void addxp(int xp, EntityPlayer p) {
			player = p;
			SQLConnector.addXP("builder", xp);
			if(getXP() == 800) {
				setLevel(1);
			}else if(getXP() == 1200){
				setLevel(2);
			}else if(getXP() == 5600) {
				setLevel(3);
			}else if(getXP() == 12000) {
				setLevel(4); 
			}else if(getXP() == 15000) { 
				setLevel(5);
			}else if(getXP() == 19250) {
				setLevel(6);
			}else if(getXP() == 23875){
				setLevel(7);
			}else if(getXP() == 32940){
				setLevel(8);
			}else if(getXP() == 41000){
				setLevel(9);
			}else if(getXP() == 54580){
				setLevel(10);
			}
			
		}
		
		public static void setXP(int xp) {
			SQLConnector.setXP("builder", xp);
			if(getXP() == 800) {
				setLevel(1);
			}else if(getXP() == 1200){
				setLevel(2);
			}else if(getXP() == 5600) {
				setLevel(3);
			}else if(getXP() == 12000) {
				setLevel(4); 
			}else if(getXP() == 15000) { 
				setLevel(5);
			}else if(getXP() == 19250) {
				setLevel(6);
			}else if(getXP() == 23875){
				setLevel(7);
			}else if(getXP() == 32940){
				setLevel(8);
			}else if(getXP() == 41000){
				setLevel(9);
			}else if(getXP() == 54580){
				setLevel(10);
			}
		}
		
		
		public static int getXP() {
			return SQLConnector.getXP("builder");
		}
		
		public static int getLevel() {
			return Level;
			
		}
		
		public static void addLevel() {
			++Level;
		}
		public static void setLevel(int level) {
			Level = level;
		}
			
}
