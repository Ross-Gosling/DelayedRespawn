package uk.co.cherrygoose.delayedrespawn.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import uk.co.cherrygoose.delayedrespawn.Main;
import uk.co.cherrygoose.delayedrespawn.systems.DeathCooldown;
import uk.co.cherrygoose.delayedrespawn.systems.Utils;

public class DeathCooldownCommand {

	@SuppressWarnings("deprecation")
	public static boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{		
    	// If sender is a player
		if(sender instanceof Player) 
		{
			// Casts sender to Player
			Player player = (Player) sender;

			// If player is OP
			if(player.isOp())
			{
				// If there are arguments
				if(args.length != 0)
				{
					// If first arg is "clear"
					if(args[0].equalsIgnoreCase("clear"))
					{
						// If second arg exists
						if(args.length > 1)
						{
							// Creates list for available players in cooldown
							ArrayList<UUID> coolingPlayerUUIDs = new ArrayList<UUID>();
							// For all players
							for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
							{
								// If player has death cooldown
								if(DeathCooldown.get(offlinePlayer) > 0)
								{
									// Adds player name to list
									coolingPlayerUUIDs.add(offlinePlayer.getUniqueId());
								}
							}
							
							// Logs to console
							Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn clear " + args[1]);
							
							// If arg is a player name
							if(coolingPlayerUUIDs.contains(Bukkit.getServer().getOfflinePlayer(args[1]).getUniqueId()))
							{
								OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(args[1]);
								
								// Sets deathcooldown
								DeathCooldown.set(targetPlayer, 0);
								
								// Sends player feedback
								player.sendMessage(ChatColor.WHITE + targetPlayer.getName() + " cleared of death cooldown");
			
						    	// Logs to console
						    	Bukkit.getLogger().info("["+Main.pluginName+"] " + targetPlayer.getName() + " cleared of death cooldown");
							}
							// Invalid player name
							else
							{
								// Sends player feedback
								player.sendMessage(ChatColor.RED + "Invalid player '" + args[1] + "'");
								return true;
							}
						}
						else 
						{
							// Logs to console
							Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn clear " + args[0]);
							
							// Sends player feedback
							player.sendMessage(ChatColor.RED + "Usage: /delayedrespawn clear <player>");
							return true;
						}
					}
					// If first arg is "set"
					else if(args[0].equalsIgnoreCase("set")) 
					{
						// If second arg exists
						if(args.length > 1)
						{							
							// Logs to console
							Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn set " + args[1]);
							
							try
							{
								// If arg is a positive double
								if(Double.parseDouble(args[1]) >= 0.0)
								{
									// Sets new delay in config
									Main.config().set("Delay", Double.parseDouble(args[1]));

									// Sends player feedback
									player.sendMessage(ChatColor.WHITE + "New respawn delay set: " + Utils.toReadable(Double.parseDouble(args[1])));
				
							    	// Logs to console
							    	Bukkit.getLogger().info("["+Main.pluginName+"] New respawn delay set: " + Double.parseDouble(args[1]) + " " + Utils.sortPlural("hour", Double.parseDouble(args[1])));
								}
								// Invalid duration
								else
								{
									// Sends player feedback
									player.sendMessage(ChatColor.RED + "Invalid delay '" + args[1] + "'");
									return true;
								}
							}
							catch (NumberFormatException e)
							{
								// Sends player feedback
								player.sendMessage(ChatColor.RED + "Invalid number format '" + args[1] + "'");
								return true;
							}
						}
						else 
						{
							// Logs to console
							Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn set " + args[0]);
							
							// Sends player feedback
							player.sendMessage(ChatColor.RED + "Usage: /delayedrespawn set <hours>");
							return true;
						}
					}
					else 
					{
						// Logs to console
						Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn " + args[0]);
						
						// Sends player feedback
						player.sendMessage(ChatColor.RED + "Usage: /delayedrespawn <clear/set>");
						
						return true;
					}
				}
				// Else there are no arguments
				else
				{
					// Logs to console
					Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn");
					return true;
				}
			}
			
			else
			{
				// Logs to console
				Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /delayedrespawn");

				// Sends player feedback
				player.sendMessage(ChatColor.RED + "You must be an Operator to use this command");
				return true;
			}
		}
		
		// If sender is the console
		if(sender instanceof ConsoleCommandSender) 
		{
			// If there are arguments
			if(args.length != 0)
			{
				// If first arg is "clear"
				if(args[0].equalsIgnoreCase("clear"))
				{
					// If second arg exists
					if(args.length > 1)
					{				
						// Logs to console
						Bukkit.getLogger().info("["+Main.pluginName+"] /delayedrespawn clear " + args[1]);
						
						// Creates list for available players in cooldown
						ArrayList<UUID> coolingPlayerUUIDs = new ArrayList<UUID>();
						// For all players
						for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
						{
							// If player has death cooldown
							if(DeathCooldown.get(offlinePlayer) > 0)
							{
								// Adds player name to list
								coolingPlayerUUIDs.add(offlinePlayer.getUniqueId());
							}
						}
						
						// If arg is a player name
						if(coolingPlayerUUIDs.contains(Bukkit.getServer().getOfflinePlayer(args[1]).getUniqueId()))
						{
							OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(args[1]);
							
							// Sets deathcooldown
							DeathCooldown.set(targetPlayer, 0);
							
							// Sends console feedback
							Bukkit.getLogger().info("["+Main.pluginName+"] " + targetPlayer.getName() + " cleared of death cooldown");
						}
						// Invalid player name
						else
						{
							// Sends console feedback
							Bukkit.getLogger().info("["+Main.pluginName+"] " + "Invalid player '" + args[1] + "'");
							return true;
						}
					}
					else 
					{
						// Sends console feedback
						Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /delayedrespawn clear <player>");
						return true;
					}
				}
				// If first arg is "set"
				else if(args[0].equalsIgnoreCase("set")) 
				{
					// If second arg exists
					if(args.length > 1)
					{						
						// Logs to console
						Bukkit.getLogger().info("["+Main.pluginName+"] /delayedrespawn set " + args[1]);
						
						try
						{
							// If arg is a positive double
							if(Double.parseDouble(args[1]) >= 0.0)
							{
								// Sets new delay in config
								Main.config().set("Delay", Double.parseDouble(args[1]));

						    	// Logs to console
						    	Bukkit.getLogger().info("["+Main.pluginName+"] New respawn delay set: " + Double.parseDouble(args[1]) + " " + Utils.sortPlural("hour", Double.parseDouble(args[1])));
							}
							// Invalid duration
							else
							{
								// Sends console feedback
								Bukkit.getLogger().info("["+Main.pluginName+"] Invalid delay '" + args[1] + "'");
								return true;
							}
						}
						catch (NumberFormatException e)
						{
							// Sends console feedback
							Bukkit.getLogger().info("["+Main.pluginName+"] Invalid number format '" + args[1] + "'");
							return true;
						}
					}
					else 
					{
						// Sends player feedback
						Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /delayedrespawn set <hours>");
						return true;
					}
				}
				else 
				{
					// Sends console feedback
					Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /delayedrespawn <clear/set>");
					return true;
				}
			}
			// Else there are no arguments
			else
			{
				// Logs to console
				Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /delayedrespawn <clear/set>");
				return true;
			}
		}
		
		// If sender is a command block
		if(sender instanceof BlockCommandSender) 
		{
			//BlockCommandSender cmdBlock = (BlockCommandSender) sender;
		}
				
		return false;
	}
	
	public static List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		// If sender is a player
		if(sender instanceof Player) 
		{
			Player player = (Player) sender;
			
			// If has 1 argument
			if(args.length == 1)
			{
				// Declares array for appropriate arguments to return
				ArrayList<String> returnedArgs = new ArrayList<String>();
				
				// Declares array of valid arguments
				ArrayList<String> validArgs = new ArrayList<String>();
				// Defines valid args
				validArgs.add("clear");
				if(player.isOp()) { validArgs.add("set"); }
				
				// If arg is not empty
				if(!args[0].toString().equals(""))
				{
					// For all validArgs
					for (String validArg : validArgs)
					{
						// If validArg starts with argument
						if(validArg.toLowerCase().startsWith(args[0].toLowerCase()))
						{
							// Adds validArg to list
							returnedArgs.add(validArg);
						}
					}
				}
				// If arg is empty
				else
				{
					// For all validArgs
					for (String validArg : validArgs)
					{
						// If validArg is not null
						if(validArg != null)
						{
							// Adds validArg to list
							returnedArgs.add(validArg);
						}
					}
				}
				
				// Sorts list into alphabetical order
				Collections.sort(returnedArgs);
				
				// Returns list
				return returnedArgs;
			}
			// If has "clear" arg and another
			else if (args[0].equalsIgnoreCase("clear") && args.length == 2)
			{
				// Declares array for appropriate player names
				ArrayList<String> returnedPlayerNames = new ArrayList<String>();
				
				// If arg is not empty
				if(!args[1].toString().equals(""))
				{
					// For all players
					for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
					{
						// If player has death cooldown
						if(DeathCooldown.get(offlinePlayer) > 0)
						{
							// If player name starts with argument
							if(offlinePlayer.getName().toLowerCase().startsWith(args[1].toLowerCase()))
							{
								// Adds player name to list
								returnedPlayerNames.add(offlinePlayer.getName());
							}
						}
					}
				}
				// If arg is empty
				else
				{
					// For all players
					for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
					{
						// If player has death cooldown
						if(DeathCooldown.get(offlinePlayer) > 0)
						{
							// Adds player name to list
							returnedPlayerNames.add(offlinePlayer.getName());
						}
					}
				}
				
				// Sorts list into alphabetical order
				Collections.sort(returnedPlayerNames);
				
				// Returns list
				return returnedPlayerNames;
			}
		}
			
		return null;
	}
}
