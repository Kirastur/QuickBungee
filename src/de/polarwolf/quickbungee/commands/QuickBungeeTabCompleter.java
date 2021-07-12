package de.polarwolf.quickbungee.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class QuickBungeeTabCompleter implements TabCompleter {
	
	protected final QuickBungeeCommand quickBungeeCommand;
	
	public QuickBungeeTabCompleter(QuickBungeeCommand quickBungeeCommand) {
		this.quickBungeeCommand = quickBungeeCommand;
	}
		

	protected List<String> handleTabComplete(String[] args) {
		if (args.length == 0) {
			return new ArrayList<>();			
		}

		if (args.length==1) {
			return quickBungeeCommand.listCommands();
		}
		
		String subCommandName = args[0];
		SubCommand subCommand = quickBungeeCommand.findSubCommand(subCommandName);
		if (subCommand == null) {
			return new ArrayList<>();			
		}
		
		if ((args.length == 2) && subCommand.isParsePlayer()) {
			return null; // This automatically lists players
		}
		
		if ((args.length == 3) && subCommand.isParseServer()) {
			return quickBungeeCommand.listServer();
		}
		
		return new ArrayList<>();			
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			return handleTabComplete(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();			
	}	

}
