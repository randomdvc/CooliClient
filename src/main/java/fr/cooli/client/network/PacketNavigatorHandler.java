package fr.cooli.client.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class PacketNavigatorHandler implements IMessageHandler<PacketNavigator, IMessage>{

	@Override
	public IMessage onMessage(PacketNavigator message, MessageContext ctx) {
		  EntityPlayerMP playermp = ctx.getServerHandler().playerEntity;
       MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "mv tp " + playermp.getDisplayName() + " " + PacketNavigator.world);  
	        return null;
	       }
	
}
