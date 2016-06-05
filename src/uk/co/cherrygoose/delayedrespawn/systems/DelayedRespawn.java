package uk.co.cherrygoose.delayedrespawn.systems;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import uk.co.cherrygoose.delayedrespawn.Main;

public class DelayedRespawn 
{
	public static void update(Player player)
	{
		// Defines current system time in minutes
		long currentTimeMinutes = (System.currentTimeMillis() / 1000) / 60;
		
		// Gets line from player data
		String line = Main.playerdata().getString(player.getUniqueId() + ".DeathCooldown");
		
		// If line is not null
		if(line != null)
		{
			// Declares String[] for split line
			String[] splitLine = line.split(":");
			// Define lastUpdate
			long lastUpdateMinutes = Long.parseLong(splitLine[1]);
						
			long diffMinutes = currentTimeMinutes - lastUpdateMinutes;
			
			double diffHours = (double)diffMinutes / (double)60.0;
			
			double updatedTime = get(player) - diffHours;
			
			// If updateTime is less than 0
			if(updatedTime < 0)
			{
				// Binds value to 0
				updatedTime = 0.0;
			}
			
			// Sets player deathcooldown with updated value
			set(player, updatedTime);
		}
		else 
		{
			// Sets player deathcooldown with default value
			set(player, 0);
		}
	}
	
	public static void add(OfflinePlayer player)
	{
		// Adds the player with the configured death cooldown period
		set(player, new Double(Main.config().getDouble("Delay")));
	}
	
	public static void set(OfflinePlayer player, double hours)
	{
		// Sets death cooldown of player in config
		Main.playerdata().set(player.getUniqueId() + ".DeathCooldown", hours + ":" + (System.currentTimeMillis()/1000)/60);
	}
	
	public static double get(OfflinePlayer player)
	{		
		// If deathCooldown exists
		try
		{
			// Gets line of data stored
			String line = Main.playerdata().getString(player.getUniqueId() + ".DeathCooldown");
			
			// Splits string into components
			String[] splitLine = line.split(":");
			
			// Return deathcooldown of player from stored data
			return Double.parseDouble(splitLine[0]);
		}
		catch(NullPointerException e)
		{
			// Return default value
			return 0.0;
		}
	}
}