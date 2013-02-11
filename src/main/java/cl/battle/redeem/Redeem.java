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
    public FileConfiguration config;
    
    @Override
    public void onLoad()
    {
        initConfiguration();
    }
    
    @Override
    public void onEnable()
    {
        // Register commands
        getCommand("canjear").setExecutor(new CanjearCommand(this));
        getCommand("premiar").setExecutor(new PremiarCommand(this));

        logger.log(Level.INFO, String.format("Redeem v%s Enabled!", getDescription().getVersion()));
    }
    
    @Override
    public void onDisable()
    {
        logger.log(Level.INFO, String.format("Redeem Disabled!", getDescription().getVersion()));
    }
    
    public static Plugin getPlugin()
    {
        return Bukkit.getServer().getPluginManager().getPlugin("Redeem");
    }
    
    private void initConfiguration() 
    {
        // TODO: Leer desde archivo si existe, si no copiar default
        this.config = this.getConfig();
        
        if (this.config == null)
            this.config.options().copyDefaults(true);
        
        this.saveConfig();
    }
}