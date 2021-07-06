package de.polarwolf.quickbungee.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.polarwolf.quickbungee.api.QuickBungeeAPI;
import de.polarwolf.quickbungee.bstats.Metrics;
import de.polarwolf.quickbungee.commands.QuickBungeeCommand;
import de.polarwolf.quickbungee.commands.QuickBungeeTabCompleter;
import de.polarwolf.quickbungee.listener.PlayerJoinListener;
import de.polarwolf.quickbungee.messagechannel.BungeeChannel;

public class Main extends JavaPlugin {

	public static final String COMMAND_NAME = "quickbungee";
	
	protected BungeeChannel bungeeChannel = null;
	protected PlayerJoinListener playerJoinListener = null; 
	protected QuickBungeeAPI quickBungeeAPI = null;
	protected QuickBungeeCommand quickBungeeCommand = null;
	protected QuickBungeeTabCompleter quickBungeeTabCompleter = null;
	

	@Override
	public void onEnable() {
		
		
		// Create Link to BungeeCord
		bungeeChannel = new BungeeChannel(this);
		bungeeChannel.registerChannel();
		
		// Create Player Lister
		playerJoinListener = new PlayerJoinListener(this, bungeeChannel);
		playerJoinListener.registerListener();
	    
	    // Initialize the API
	    quickBungeeAPI = new QuickBungeeAPI(bungeeChannel);
		QuickBungeeProvider.setAPI(quickBungeeAPI);
		
		// Register Command and TabCompleter
		quickBungeeCommand = new QuickBungeeCommand(this, quickBungeeAPI);
		getCommand(COMMAND_NAME).setExecutor(quickBungeeCommand);
		quickBungeeTabCompleter = new QuickBungeeTabCompleter(quickBungeeCommand);
		getCommand(COMMAND_NAME).setTabCompleter(quickBungeeTabCompleter);
		
		// Enable bStats Metrics
		// Please download the bstats-code direct form their homepage
		// or disable the following instruction
		new Metrics(this, Metrics.PLUGINID_QUICKBUNGEE);
		
		getLogger().info("QuickBungee is ready");

	}

	
	@Override
	public void onDisable() {
		QuickBungeeProvider.setAPI(null);
		bungeeChannel.unregisterChannel();
		playerJoinListener.unregisterListener();
	}
	
}
