package uk.co.cherrygoose.delayedrespawn.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import uk.co.cherrygoose.delayedrespawn.systems.DeathCooldown;

public class RespawnListener implements Listener 
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent event) 
	{
		// Gets Player
		Player player = event.getPlayer();

		// If player has a death cooldown active
		if(DeathCooldown.get(player) > 0)
		{			
			// Kicks player
			player.kickPlayer("Respawn available in " + DeathCooldown.toReadable(DeathCooldown.get(player)));
		}
	}
}