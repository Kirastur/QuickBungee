package de.polarwolf.quickbungee.main;

import de.polarwolf.quickbungee.api.QuickBungeeAPI;

public class QuickBungeeProvider {

	private static QuickBungeeAPI quickBungeeAPI;
	
	private QuickBungeeProvider() {
	}

	protected static void setAPI (QuickBungeeAPI newAPI) {
		quickBungeeAPI=newAPI;
	}
		    
	public static QuickBungeeAPI getAPI() {
		return quickBungeeAPI;
	}
	
}
