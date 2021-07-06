package de.polarwolf.quickbungee.commands;

public enum SubCommand {

	SEND ("send", true, true),
	LIST ("list", false, false),
	INFO ("info", false, false),
	REFRESH ("refresh", false, false),
	HELP ("help", false, false);
	
	
	private final String command;
	private final boolean parsePlayer; 
	private final boolean parseServer;
	

	private SubCommand(String command, boolean parsePlayer, boolean parseServer) {
		this.command = command;
		this.parsePlayer = parsePlayer;
		this.parseServer = parseServer;
	}


	public String getCommand() {
		return command;
	}


	public boolean isParsePlayer() {
		return parsePlayer;
	}


	public boolean isParseServer() {
		return parseServer;
	}


}