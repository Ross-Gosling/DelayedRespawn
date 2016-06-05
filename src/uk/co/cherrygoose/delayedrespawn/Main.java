package uk.co.cherrygoose.delayedrespawn;

import java.io.FileNotFoundException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.cherrygoose.delayedrespawn.commands.DelayedRespawnCommand;
import uk.co.cherrygoose.delayedrespawn.events.DeathListener;
import uk.co.cherrygoose.delayedrespawn.events.KickListener;
import uk.co.cherrygoose.delayedrespawn.events.LoginListener;
import uk.co.cherrygoose.delayedrespawn.events.RespawnListener;

public class Main extends JavaPlugin 
{
	// Plugin for referencing in use
    private static Plugin plugin;
    public static String pluginName;
	// YamlConfiguration for player data storage
	private static YamlConfiguration playerDataFile = new YamlConfiguration();
	
    @Override
    public void onEnable() 
    {
    	// Assigns plugin to variable
		plugin = this;
		pluginName = plugin.getDescription().getName();  

		// Creates a config
		plugin.saveDefaultConfig();

		// Loads playerData yaml file
		playerDataFile = loadYml("plugins/" + pluginName + "/playerdata.yml");
		
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
		// Saves player data
		saveYml(playerDataFile, "plugins/" + pluginName + "/playerdata.yml");

    	// Logs to console
    	Bukkit.getLogger().info("["+pluginName+"] Disabled");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {
    	// If "deathcooldown" command
		if(cmd.getName().equalsIgnoreCase("delayedrespawn"))
		{
			return DelayedRespawnCommand.onCommand(sender, cmd, commandLabel, args);
		}
		
    	return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    	// If "deathcooldown" command
		if(cmd.getName().equalsIgnoreCase("delayedrespawn"))
		{
			return DelayedRespawnCommand.onTabComplete(sender, cmd, commandLabel, args);
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
    public static YamlConfiguration playerdata()
    {
    	// Returns yaml
    	return playerDataFile;
    }    
    
    public static YamlConfiguration loadYml(String sDirectory)
    {  
    	// Declares new YamlConfig
        YamlConfiguration yml = new YamlConfiguration();
        
        try
        {
        	// Loads the ymlfile
            yml.load(sDirectory);
        }
        catch(FileNotFoundException e)
        {
            try
            {
            	// Creates a new yml file if one doesn't exist
                yml.save(sDirectory);
            }
            catch(Exception e2)
            {
            	// Prints error
            	e.printStackTrace();
            }
        }
        catch(Exception e)
        {
        	// Prints error
        	e.printStackTrace();
        }
        
        // Returns yml file
        return yml;
    }

    public static boolean saveYml(YamlConfiguration ymlConfig, String sDirectory)
    {
        try
        {
        	// Saves yml file
        	ymlConfig.save(sDirectory);
        	
        	// Returns true: file saved
            return true;
        }
        catch(Exception e)
        {
        	// Prints error
        	e.printStackTrace();
        }
        
        // Returns false: couldn't save file
        return false;
    }
}
