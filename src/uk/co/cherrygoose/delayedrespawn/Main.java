package uk.co.cherrygoose.delayedrespawn;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.cherrygoose.delayedrespawn.commands.DeathCooldownCommand;
import uk.co.cherrygoose.delayedrespawn.events.DeathListener;
import uk.co.cherrygoose.delayedrespawn.events.KickListener;
import uk.co.cherrygoose.delayedrespawn.events.LoginListener;
import uk.co.cherrygoose.delayedrespawn.events.RespawnListener;

public class Main extends JavaPlugin 
{
	// Plugin for referencing in use
    private static Plugin plugin;
    public static String pluginName;
    
    @Override
    public void onEnable() 
    {
    	// Assigns plugin to variable
		plugin = this;
		pluginName = plugin.getDescription().getName();    	
		
		// Creates a config
		plugin.saveDefaultConfig();
	    
		// Registers all event listeners
		Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new KickListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new LoginListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new RespawnListener(), plugin);

    	// Logs to console
    	Bukkit.getLogger().info("["+pluginName+"] Enabled");
    }

    @Override
    public void onDisable() 
    {
    	// Saves config
    	plugin().saveConfig();
    	
    	// Logs to console
    	Bukkit.getLogger().info("["+pluginName+"] Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {
    	// If "deathcooldown" command
		if(cmd.getName().equalsIgnoreCase("delayedrespawn"))
		{
			return DeathCooldownCommand.onCommand(sender, cmd, commandLabel, args);
		}
		
    	return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    	// If "deathcooldown" command
		if(cmd.getName().equalsIgnoreCase("delayedrespawn"))
		{
			return DeathCooldownCommand.onTabComplete(sender, cmd, commandLabel, args);
		}
		
		return null;
    }
    
    public static FileConfiguration config()
    {
    	// Returns config
    	return plugin.getConfig();
    }
    public static Plugin plugin()
    {
    	// Returns plugin
    	return plugin;
    }
}
