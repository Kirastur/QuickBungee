package de.polarwolf.quickbungee.listener;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinScheduler extends BukkitRunnable {
	
	protected static final int MAX_TRY = 15;
	
	protected final PlayerJoinListener playerJoinListener;
	protected final Player player;
	protected int count = 0;
	
	
	public PlayerJoinScheduler(PlayerJoinListener playerJoinListener, Player player) {
		this.playerJoinListener = playerJoinListener;
		this.player = player;
	}
	
	protected void handleRun() {
		if ((count < MAX_TRY) && playerJoinListener.isRefeshNeeded()) {
			playerJoinListener.doRequestRefresh(player);
			count = count +1;
		} else {
			playerJoinListener.stopScheduler();
		}
		
	}
	
	@Override
	public void run() {
		try {
			handleRun();			
		} catch (Exception e) {
			e.printStackTrace();
			cancel();
		}
	}

}
