package de.polarwolf.quickbungee.api;

import java.util.List;

import org.bukkit.entity.Player;

import de.polarwolf.quickbungee.messagechannel.BungeeChannel;

public class QuickBungeeAPI {
	
	protected final BungeeChannel bungeeChannel;
	

	public QuickBungeeAPI(BungeeChannel bungeeChannel) {
		this.bungeeChannel = bungeeChannel;
	}
	

	public void requestRefresh(Player player) {
		bungeeChannel.requestRefresh(player);
	}
	

	public boolean requestConnect(Player player, String serverName) {
		if (getServerName().equals(serverName) || !getServerList().contains(serverName)) {
			return false;			
		}
		bungeeChannel.requestConnect(player, serverName);
		return true;
	}


	public String getServerName() {
		return bungeeChannel.getServerName();
	}
	
	public List<String> getServerList() {
		return bungeeChannel.getServerList();
	}
	
}
