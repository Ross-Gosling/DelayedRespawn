package commands;

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

import systems.DeathCooldown;
import uk.co.cherrygoose.delayedrespawn.Main;

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
					// If first arg is "set"
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
							Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /deathcooldown clear " + args[1]);
							
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
							Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /deathcooldown clear " + args[0]);
							
							// Sends player feedback
							player.sendMessage(ChatColor.RED + "Usage: /deathcooldown clear <player>");
							return true;
						}
					}
					else 
					{
						// Logs to console
						Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /colour " + args[0]);
						
						// Sends player feedback
						player.sendMessage(ChatColor.RED + "Usage: /deathcooldown clear <player>");
						return true;
					}
				}
				// Else there are no arguments
				else
				{
					// Logs to console
					Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /deathcooldown");
					return true;
				}
			}
			else
			{
				// Logs to console
				Bukkit.getLogger().info("["+Main.pluginName+"] " + player.getName() + ": /deathcooldown");

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
				// If first arg is "set"
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
						Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /deathcooldown clear <player>");
						return true;
					}
				}
				else 
				{
					// Sends console feedback
					Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /deathcooldown clear <player>");
					return true;
				}
			}
			// Else there are no arguments
			else
			{
				// Logs to console
				Bukkit.getLogger().info("["+Main.pluginName+"] " + "Usage: /deathcooldown clear <player>");
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
		// If has 1 argument
		if(args.length == 1)
		{
			// Declares array for appropriate arguments to return
			ArrayList<String> returnedArgs = new ArrayList<String>();
			
			// Declares array of valid arguments
			ArrayList<String> validArgs = new ArrayList<String>();
			// Defines valid args
			validArgs.add("clear");
						
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
		// If has "set" arg and another
		else if (args[0].equalsIgnoreCase("set") && args.length == 2)
		{
			// Declares array for appropriate player names
			ArrayList<String> returnedPlayerNames = new ArrayList<String>();
			
			returnedPlayerNames.add("AIDS");
			return returnedPlayerNames;
			
			// If arg is not empty
			/*if(!args[1].toString().equals(""))
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
			return returnedPlayerNames;*/
		}
		
		return null;
	}
}
