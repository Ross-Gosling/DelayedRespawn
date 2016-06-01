package events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import systems.DeathCooldown;
import uk.co.cherrygoose.delayedrespawn.Main;

public class DeathListener implements Listener 
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
    	// Logs to console
    	Bukkit.getLogger().info("["+Main.pluginName+"] onPlayerDeath called");
		
		// Adds death cooldown to player
		DeathCooldown.add(event.getEntity());
	}
}
