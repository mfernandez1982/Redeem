package cl.battle.redeem;

import cl.battle.redeem.command.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Redeem extends JavaPlugin
{
    // Bukkit Plugin stuff
    protected static final Logger logger = Logger.getLogger("Minecraft");
    protected static final String configFile = "config.yml";
    protected FileConfiguration config;
    
    @Override
    public void onLoad()
    {
        
    }
    
    @Override
    public void onEnable()
    {
        // Register commands
        getCommand("canjear").setExecutor(new CanjearCommand(this));
        getCommand("premiar").setExecutor(new PremiarCommand(this));

        logger.log(Level.INFO, String.format("DungeonBX v%s Enabled!", getDescription().getVersion()));
    }
    
    @Override
    public void onDisable()
    {
        
    }
    
    public static Plugin getPlugin()
    {
        return Bukkit.getServer().getPluginManager().getPlugin("Redeem");
    }

    public static boolean isAvailable() 
    {
            Plugin plugin = getPlugin();

            return (plugin instanceof Redeem) && ((Redeem) plugin).fooVariable != null;
    }
    
    private void initConfiguration() 
    {
        if (this.config == null) {
            this.config = this.getConfig();
        }

        this.config.options().copyDefaults(true);
        saveConfig();
    }
    
    private void loadConfig()
    {
        this.initConfiguration();
    }
}