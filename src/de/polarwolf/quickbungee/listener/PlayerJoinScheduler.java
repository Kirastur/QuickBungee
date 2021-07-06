package de.polarwolf.quickbungee.listener;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.polarwolf.quickbungee.messagechannel.BungeeChannel;

public class PlayerJoinScheduler extends BukkitRunnable {
	
	protected static final int MAX_TRY = 15;
	
	protected final BungeeChannel bungeeChannel;
	protected final Player player;
	protected int count = 0;
	
	
	public PlayerJoinScheduler(BungeeChannel bungeeChannel, Player player) {
		this.bungeeChannel = bungeeChannel;
		this.player = player;
	}
	
	@Override
	public void run() {
		if ((count < MAX_TRY) && (bungeeChannel.getServerName().isEmpty() || bungeeChannel.getServerList().isEmpty())) {
			bungeeChannel.requestRefresh(player);
			count = count +1;
		}
		else this.cancel();
	}

}
