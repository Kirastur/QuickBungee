package de.polarwolf.quickbungee.commands;

public enum Message {

	OK ("OK"),
	ERROR ("ERROR"),
	HELP ("Valid commands are: "),
	NOT_A_PLAYER ("This command can only be run by a player!"),
	SERVER_LIST ("Configured servers are: "),
	NO_SERVER ("The serverlist is not yet loaded. Please use /quickbungee refresh"),
	UNKNOWN_PARAMETER ("Unknown parameter"),
	TOO_MANY_PARAMETERS ("Too many parameters"),
	MISSING_PLAYER ("Player name is missing"),
	UNKNOWN_PLAYER ("Unknown player"),
	MISSING_SERVER ("Server name is missing"),
	UNKNOWN_SERVER ("Unknown server"),
	SAME_SERVER ("Player is already conected to this server"),
	SENDING_1 ("Sending player "),
	SENDING_2 (" to "),
	THIS_SERVER ("This server is "),
	
	PREPARING_REFRESH ("Preparing refresh"),
	REFRESH_REQUESTED ("Refresh requested from "),
	SERVERNAME_REFRESHED ("Servername refreshed: "),
	SERVERLIST_REFRESHED ("Serverlist refreshed");
		


	private final String messageText;
	

	private Message(String messageText) {
		this.messageText = messageText;
	}

	
	@Override
	public String toString() {
		return messageText;
	}
}
