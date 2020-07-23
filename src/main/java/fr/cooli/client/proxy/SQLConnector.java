package fr.cooli.client.proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.cooli.client.CooliMod;
import fr.cooli.client.Manager.Builder;
import fr.cooli.client.Manager.LumberJack;
import fr.cooli.client.Manager.Miner;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Session;

@SideOnly(Side.CLIENT)
public class SQLConnector {

	private static Connection connection;
	private String urlbase, host, database, user, pass;
	
	public SQLConnector(String urlbase, String host, String database, String user, String pass) {
		
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
		
	}

	public void connect() {
		if(!isConnected()) {
		try {
			connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
			System.out.println("Connected ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
  }
	
	public static void createAccount() {
		if(!hasAccount()) {
			try {
				PreparedStatement q  = connection.prepareStatement("INSERT INTO jobs(uuid,miner,builder,lumberjack) VALUES (?,?,?,?)");
				q.setString(1, Minecraft.getMinecraft().getSession().getPlayerID().toString());
				q.setInt(2, 0);
				q.setInt(3, 0);
				q.setInt(4, 0);
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean hasAccount() {	
		try {
			PreparedStatement q = connection.prepareStatement("SELECT uuid FROM jobs WHERE uuid = ?");
			q.setString(1, Minecraft.getMinecraft().getSession().getPlayerID().toString());
			ResultSet resultat = q.executeQuery();
			boolean hasAccount = resultat.next();
			q.close();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static int getXP(String job) {
		try {
			PreparedStatement q = connection.prepareStatement("SELECT " + job + " FROM jobs WHERE uuid = ?");
			q.setString(1, Minecraft.getMinecraft().getSession().getPlayerID().toString());
			
			int xp = 0;
			ResultSet rs = q.executeQuery();
			
			while(rs.next()) {
				xp = rs.getInt(job);
			}
			
			q.close();
			
			return xp;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public static void addXP(String job, int xp) {
		
		int actualxp = getXP(job);
		int newxp = actualxp + xp;
		
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE " + "jobs" + " SET " + job + "= ? WHERE uuid = ?");
			q.setInt(1, newxp);
			q.setString(2, Minecraft.getMinecraft().getSession().getPlayerID().toString());
			q.executeUpdate();
			q.close();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
    public static void setXP(String job, int xp) {
				
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE " + "jobs" + " SET " + job + "= ? WHERE uuid = ?");
			q.setInt(1, xp);
			q.setString(2, Minecraft.getMinecraft().getSession().getPlayerID().toString());
			q.executeUpdate();
			q.close();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void removeXP(String job, int xp) {
		
		int actualxp = getXP(job);
		int newxp = actualxp - xp;
		
		if(newxp >= 0) {
			return;
		}
		
		try {
			PreparedStatement q = connection.prepareStatement("UPDATE " + "jobs" + " SET " + job + "= ? WHERE uuid = ?");
			q.setInt(1, newxp);
			q.setString(2, Minecraft.getMinecraft().getSession().getPlayerID().toString());
			q.executeUpdate();
			q.close();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connection != null;
		
	}
	
}
