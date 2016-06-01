package uk.co.cherrygoose.delayedrespawn.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import uk.co.cherrygoose.delayedrespawn.systems.DeathCooldown;

public class DeathListener implements Listener 
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		// Adds death cooldown to player
		DeathCooldown.add(event.getEntity());
	}
}
