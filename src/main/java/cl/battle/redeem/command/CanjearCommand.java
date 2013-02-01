package cl.battle.redeem.command;

import cl.battle.redeem.Redeem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CanjearCommand implements CommandExecutor
{
    public Redeem plugin;
    
    public CanjearCommand(Redeem plugin)
    {
        this.plugin = plugin;
    }
    
    
    /*
     * Usos: /canjear item <id> <cantidad>
     *       /canjear kit <nombreKit>
     *       /canjear enchant <id> <cantidad>
     *       /canjear custom nombre <color> <nombreCustom>
     *       /canjear custom lore <color> <lore>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args)
    {
        if (args.length > 1 && (sender instanceof Player))
        {
            
        }
        
        return false;
    }
}
