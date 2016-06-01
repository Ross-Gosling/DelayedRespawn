package uk.co.cherrygoose.delayedrespawn.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import uk.co.cherrygoose.delayedrespawn.systems.DelayedRespawn;

public class KickListener implements Listener 
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) 
	{
		// Gets kicked player
		Player player = event.getPlayer();
		
		// If kicked with deathcooldown
		if(DelayedRespawn.get(player) > 0.0)
		{
	    	// Logs to console
	    	Bukkit.getLogger().info("[DelayedRespawn] " + player.getName() + " kicked (Death Cooldown > 0)");
	    	
	    	// Sets kick message in chat
	    	event.setLeaveMessage(ChatColor.YELLOW + player.getName() + " was kicked for dying.");
		}
	}
}
