package cl.battle.redeem.command;

import cl.battle.redeem.Redeem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class PremiarCommand implements CommandExecutor
{
    public Redeem plugin;
    
    public PremiarCommand(Redeem plugin)
    {
        this.plugin = plugin;
    }
    
    // Uso: /premiar <player> <cantidad>
    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args)
    {
        if (args.length > 1)
        {
            
        }
        
        return false;
    }
}
