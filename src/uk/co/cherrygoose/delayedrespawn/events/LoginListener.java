package uk.co.cherrygoose.delayedrespawn.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import uk.co.cherrygoose.delayedrespawn.systems.DelayedRespawn;
import uk.co.cherrygoose.delayedrespawn.systems.Utils;

public class LoginListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		// Declares Player
		Player player = event.getPlayer();
				
		// Updates the player death cooldown
		DelayedRespawn.update(player);
		
		// Player doesn't have death cooldown
		if (DelayedRespawn.get(player) == 0.0) 
		{
			// Allows player login
			event.allow();
		}
		
		// Player does have a cooldown
		else 
		{
			// Disallows connection and displays the remaining cooldown
			event.disallow(null, "Respawn available in " + Utils.toReadable(DelayedRespawn.get(player)) + ".");

	    	// Logs to console
	    	Bukkit.getLogger().info("[DelayedRespawn] " + player.getName() + " connection disallowed (Remaining cooldown: " + Utils.toReadable(DelayedRespawn.get(player)) + ")");
		}
	}
}
