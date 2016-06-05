package uk.co.cherrygoose.delayedrespawn.systems;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import uk.co.cherrygoose.delayedrespawn.Main;

public class DelayedRespawn 
{
	public static void update(Player player)
	{
		long currentTimeSecs = System.currentTimeMillis() / 1000;
		long currentTimeMinutes = currentTimeSecs / 60;
		
		String line = Main.playerdata().getString(player.getUniqueId() + ".DeathCooldown");
		
		if(line != null)
		{
			String[] splitLine = line.split(":");
			long lastUpdateMinutes = Long.parseLong(splitLine[1]);
						
			long diffMinutes = currentTimeMinutes - lastUpdateMinutes;
			
			double diffHours = (double)diffMinutes / (double)60.0;
			
			double updatedTime = get(player) - diffHours;
			
			if(updatedTime < 0)
				updatedTime = 0.0;
			
			set(player, updatedTime);
		}
		else 
		{
			set(player, 0);
		}
	}
	
	public static void add(OfflinePlayer player)
	{
		// Adds the player to the config with the configured death cooldown period
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
			String line = Main.playerdata().getString(player.getUniqueId() + ".DeathCooldown");
			
			String[] splitLine = line.split(":");
			
			// Return deathcooldown of player from config
			return Double.parseDouble(splitLine[0]);
		}
		catch(NullPointerException e)
		{
			return  0.0;
		}
	}
}