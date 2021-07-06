package de.polarwolf.quickbungee.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import de.polarwolf.quickbungee.commands.Message;
import de.polarwolf.quickbungee.messagechannel.BungeeChannel;

public class PlayerJoinListener implements Listener {
	
	protected final Plugin plugin;
	protected final BungeeChannel bungeeChannel;
	
	public PlayerJoinListener(Plugin plugin, BungeeChannel bungeeChannel) {
		this.plugin = plugin;
		this.bungeeChannel = bungeeChannel;
	}
	
	
	public void registerListener() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	
	public void unregisterListener() {
		HandlerList.unregisterAll(this);
	}
		

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if (bungeeChannel.getServerList().isEmpty() || bungeeChannel.getServerName().isEmpty()) {
			plugin.getLogger().info(Message.PREPARING_REFRESH.toString());
			Player player = event.getPlayer();
			PlayerJoinScheduler myScheduler = new PlayerJoinScheduler(bungeeChannel, player);
			myScheduler.runTaskTimer(plugin, 20, 20);
		}
	}	

}
