package de.polarwolf.quickbungee.messagechannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.polarwolf.quickbungee.commands.Message;

public class BungeeChannel implements PluginMessageListener {
	
	protected static final String BUNGEECORD = "BungeeCord";
	protected static final String GETSERVER = "GetServer";
	protected static final String GETSERVERS = "GetServers";
	protected static final String CONNECT = "Connect";
	
	protected final Plugin plugin;
	protected ArrayList<String> serverList = new ArrayList<>();
	protected String serverName = "";
	
	public BungeeChannel(Plugin plugin) {
		this.plugin = plugin;
	}
	

	public void registerChannel() {
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, BUNGEECORD);
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, BUNGEECORD, this);
	}
	
	
	public void unregisterChannel() {
		plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin);
		plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin);
	}
	
	
	public String getServerName() {
		return serverName;
	}


	public List<String> getServerList() {
		return new ArrayList<>(serverList); 
	}


	protected void requestServerName(Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(GETSERVER);
		player.sendPluginMessage(plugin, BUNGEECORD, out.toByteArray());
	}
	

	protected void requestServerList(Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(GETSERVERS);
		player.sendPluginMessage(plugin, BUNGEECORD, out.toByteArray());
	}
	
	
	public void requestConnect(Player player, String serverName) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(CONNECT);
		out.writeUTF(serverName);
		player.sendPluginMessage(plugin, BUNGEECORD, out.toByteArray());
		
	}
	
	
	public void requestRefresh(Player player) {
		plugin.getLogger().info(Message.REFRESH_REQUESTED.toString() + player.getName());
		requestServerName(player);
		requestServerList(player);
	}

	
	protected void handlePluginMessageReceived(String channel, byte[] message) {
		if (!channel.equals(BUNGEECORD)) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals(GETSERVER)) {
			serverName = in.readUTF();
			plugin.getLogger().info(Message.SERVERNAME_REFRESHED.toString() + serverName);
		}
		if (subchannel.equals(GETSERVERS)) {
			String[] serverArray = in.readUTF().split(", ");
			serverList.clear();
			serverList.addAll(Arrays.asList(serverArray));
			plugin.getLogger().info(Message.SERVERLIST_REFRESHED.toString());
		}
	}


	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		try {
			handlePluginMessageReceived(channel, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
