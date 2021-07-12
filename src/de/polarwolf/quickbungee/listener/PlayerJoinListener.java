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
	protected PlayerJoinScheduler playerJoinScheduler = null;
	
	public PlayerJoinListener(Plugin plugin, BungeeChannel bungeeChannel) {
		this.plugin = plugin;
		this.bungeeChannel = bungeeChannel;
	}
	
	
	public void registerListener() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	
	public void unregisterListener() {
		stopScheduler();
		HandlerList.unregisterAll(this);
	}
	
	protected void handlePlayerJoin(PlayerJoinEvent event) {
		if (bungeeChannel.getServerList().isEmpty() || bungeeChannel.getServerName().isEmpty()) {
			plugin.getLogger().info(Message.PREPARING_REFRESH.toString());
			Player player = event.getPlayer();
			startScheduler(player);
		}
	}
	

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		try {
			handlePlayerJoin(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	
	// Called by the Scheduler
	protected boolean isRefeshNeeded() {
		return (bungeeChannel.getServerName().isEmpty() || bungeeChannel.getServerList().isEmpty()); 
	}
	
	// Called by the Scheduler
	protected void doRequestRefresh(Player player) {
		bungeeChannel.requestRefresh(player);
	}
	

	protected void startScheduler(Player player) {
		if (playerJoinScheduler != null) {
			return;
		}
		playerJoinScheduler = new PlayerJoinScheduler(this, player);
		playerJoinScheduler.runTaskTimer(plugin, 20, 20);
		
	}
	

	protected void stopScheduler() {
		if (playerJoinScheduler == null) {
			return;
		}
		playerJoinScheduler.cancel();
		playerJoinScheduler = null;
		
	}

}
