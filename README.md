# QuickBungee Overview
This is a plugin for bukkit/spigot minecraft server. 

# General Purpose
This little plugin is written for a very specific purpose: I want to initiate a BungeeCord "send" from the minecraft server (Spigot, Paper etc.) using commands. I know there are good full-size plugins available with their own initiators (e.g. BungeeCord send when clicking on a sign or talking to NPCs) but they are oversized for my personal need. So I took the example code from the Spigot/BungeeCord website and wraped a little command handler around it. Nothing more, but simple and effective.

# Usage Exampe
I had setup a quest using BetonQuest and LibSequence, and for one part of the quest the player hat to be sent to another server. So I defined a BetonQuest Event of type "command" where i call this litte plugin to send the player to the other server.

# First steps
1. Place this plugin into the Spigot/Pater etc. plugin directory
2. Log into the server with a player using the BungeeCord proxy
3. Enter "/quickbungee list" on the server console to see a list of possible target servers
4. Enter "/quickbungee send <PlayerName> <TargetServer>"  on the server console to send the player to another server

# Other Recommendations
If this plugin is not what your are searching for, please have a look at:
* BungeeTeleportManager https://www.spigotmc.org/resources/bungeeteleportmanager.80677
* Warps, Portals and more https://www.spigotmc.org/resources/warps-portals-and-more-warp-teleport-system-1-8-1-16.29595
* BetonQuest https://www.spigotmc.org/resources/betonquest.2117/
* LibSequence https://www.spigotmc.org/resources/libsequence.90664/

# Documentation
Please see the Github Wiki.

# Support
For support please contact us as discord https://discord.gg/MBJjqUHQHR

# Final notice
Please don't blame me this is a simple plugin
