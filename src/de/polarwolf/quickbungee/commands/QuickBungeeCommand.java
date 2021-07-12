package de.polarwolf.quickbungee.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.polarwolf.quickbungee.api.QuickBungeeAPI;
import de.polarwolf.quickbungee.main.Main;

public class QuickBungeeCommand implements CommandExecutor {

	protected final Main main;
	protected final QuickBungeeAPI quickBungeeAPI;
	

	public QuickBungeeCommand(Main main, QuickBungeeAPI quickBungeeAPI) {
		this.main = main;
		this.quickBungeeAPI = quickBungeeAPI;
	}
	
	
	protected void printConsoleInfo(String messageText) {
		main.getLogger().info(messageText);
	}
	

	protected void printError(CommandSender sender, String messageText) {
		sender.sendMessage(messageText);
		if (!(sender instanceof Player)) {
			main.getLogger().warning(messageText);
		}		
	}
	public List<String> listCommands() {
		List<String> names = new ArrayList<>();
		for (SubCommand subCommand : SubCommand.values()) {
			names.add(subCommand.getCommand());
		}
		return names;
	}

	
	public List<String> listServer() {
		return quickBungeeAPI.getServerList();
	}
	
	
	protected void cmdHelp(CommandSender sender) {
		String s = String.join(" ", listCommands());
		sender.sendMessage(Message.HELP.toString() + s);
	}
	
	
	protected void cmdRefresh(CommandSender sender) {
		if (!(sender instanceof Player)) {
			printError(sender, Message.NOT_A_PLAYER.toString());
			return;			
		}
		Player player = (Player)sender;
		quickBungeeAPI.requestRefresh(player);
	}
	
	
	protected void cmdList(CommandSender sender) {
		String s = String.join(" ", listServer());
		if (!s.isEmpty()) {
			s = Message.SERVER_LIST.toString() + s;
		} else {
			s = Message.NO_SERVER.toString();
		}
		sender.sendMessage(s);		
	}
				

	protected void cmdInfo(CommandSender sender) {
		String s = quickBungeeAPI.getServerName();
		if (!s.isEmpty()) {
			s = Message.THIS_SERVER.toString() + s;
		} else {
			s = Message.NO_SERVER.toString();
		}
		sender.sendMessage(s);		
	}
		

	protected void cmdSend(CommandSender sender, Player player, String serverName) {
		if (!quickBungeeAPI.getServerList().contains(serverName)) {
			printError(sender, Message.UNKNOWN_SERVER.toString());
			return;			
		}
		if (quickBungeeAPI.getServerName().equalsIgnoreCase(serverName)) {
			printError(sender, Message.SAME_SERVER.toString());
			return;			
		}
		
		printConsoleInfo(Message.SENDING_1.toString() + player.getName() + Message.SENDING_2.toString() + serverName);
		boolean transferResult = quickBungeeAPI.requestConnect(player, serverName);
		if (!transferResult) {
			printError(sender, Message.ERROR.toString());			
		}
	}


	protected void dispatchCommand(CommandSender sender, SubCommand subCommand, Player player, String serverName) {
		try {
			switch (subCommand) {
				case HELP:		cmdHelp(sender);
								break;
				case LIST:		cmdList(sender);
								break;
				case INFO:		cmdInfo(sender);
								break;
				case SEND:		cmdSend(sender, player, serverName);
								break;
				case REFRESH:	cmdRefresh(sender);
								break;
				default: sender.sendMessage(Message.ERROR.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			printError(sender, e.getMessage());
		}		
	}
	
	
	public SubCommand findSubCommand(String subCommandName) {
		for (SubCommand subCommand : SubCommand.values()) {
			if (subCommand.getCommand().equalsIgnoreCase(subCommandName)) {
				return subCommand;
			}
		}
		return null;
	}
	
	
	protected boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length==0) {
			return false;
		}

		String subCommandName=args[0];
		SubCommand subCommand = findSubCommand(subCommandName);
		if (subCommand == null) {
			printError(sender, Message.UNKNOWN_PARAMETER.toString());
			return true;
		}
		
		Player myPlayer = null;
		String myServerName = "";
		
		if (subCommand.isParsePlayer()) {
			if (args.length < 2) {
				printError(sender, Message.MISSING_PLAYER.toString());
				return true;
			}
			myPlayer = main.getServer().getPlayer(args[1]);
			if (myPlayer == null) {
				printError(sender, Message.UNKNOWN_PLAYER.toString());
				return true;
			}
		} else {
			if (args.length > 1) {
				printError(sender, Message.TOO_MANY_PARAMETERS.toString());
				return true;
			}			
		}
		
		if (subCommand.isParseServer()) {
			if (args.length < 3) {
				printError(sender, Message.MISSING_SERVER.toString());
				return true;
			}
			myServerName = args[2];
		} else {
			if (args.length > 2) {
				printError(sender, Message.TOO_MANY_PARAMETERS.toString());
				return true;
			}			
		}
		
		dispatchCommand (sender, subCommand, myPlayer, myServerName);
					
		return true; 
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			return handleCommand(sender, cmd, label, args);
		} catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(Message.JAVA_EXCEPTOPN.toString());
		}		
		return true; 
	}
	
}
