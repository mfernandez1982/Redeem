package cl.battle.redeem.command;

import cl.battle.redeem.Redeem;
import cl.battle.redeem.RedeemHandler;
import cl.battle.redeem.utils.NBTEditor;
import cl.battle.redeem.utils.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;


public class CanjearCommand implements CommandExecutor
{
    private Redeem plugin;
    private NBTEditor editor;
    private RedeemHandler rh;
    private static String COLOR_PREFIX = "c:";
    
    public CanjearCommand(Redeem plugin)
    {
        this.plugin = plugin;
        this.editor = new NBTEditor();
        this.rh = new RedeemHandler(plugin.config);
    }
    
    /*
     * Usos: /canjear item <id>
     *       /canjear kit <nombreKit>
     *       /canjear enchant <nombre>
     *       /canjear custom nombre <c:color> <nombreCustom>
     *       /canjear custom lore <c:color> <lore>
     *       /canjear experiencia <levels>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args)
    {
        if (args.length > 1 && (sender instanceof Player))
        {
            Player p = (Player)sender;
            
            if (args[0].equals("item"))
            {
                if (args[1] != null)
                {
                    int id = Integer.parseInt(args[1]);
                } else {
                    p.sendMessage(ChatColor.RED + "No indicaste la id del item.");
                    return false;
                } 
            }
            
            if (args[0].equals("experiencia"))
            {
                if (args[1] != null)
                {
                    int amount = Integer.parseInt(args[1]);
                    return rh.redeemExp(p, amount);
                } else {
                    p.sendMessage(ChatColor.RED + "No indicaste la cantidad de niveles.");
                    return false;
                }
            }
            
            if (args[0].equals("enchant"))
            {
                if (args[1] != null)
                {
                    String enchant = args[1];
                    try {
                    Enchantment e = Enchantment.getByName(enchant.toUpperCase());
                    return editor.addItemEnchant(p, e);
                    } catch (Exception e) {
                        p.sendMessage(ChatColor.RED + "El encantamiento no existe.");
                        return false;
                    }
                } 
            }
            
            if (args[0].equals("custom"))
            {
                if (args[1].equals("nombre"))
                {
                    if (args[2] != null)
                    {
                        String color = "WHITE";
                        if(StringUtil.startsWithIgnoreCase(COLOR_PREFIX, args[2])){
                            color = args[2].substring(2).toUpperCase();
                            args[2] = null;
                        }

                        try{
                            String newName = (ChatColor.valueOf(color) + StringUtil.assembleString(args));
                            return editor.setItemName(p, newName);
                        }catch (Exception e){
                            p.sendMessage(ChatColor.RED + "Ese color no existe!");
                            return false;
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "No indicaste un nombre de item!");
                    }
                }
                
                if (args[1].equals("lore"))
                {
                    if (args[2] != null)
                    {
                        String color = "DARK_PURPLE";
                        if(StringUtil.startsWithIgnoreCase(COLOR_PREFIX, args[2])){
                            color = args[2].substring(2).toUpperCase();
                            args[2] = null;
                        }

                        try {
                            String lore = ChatColor.valueOf(color) + (ChatColor.ITALIC + StringUtil.assembleString(args));
                            return editor.setItemLore(p, lore);
                        }catch (Exception ex){
                            p.sendMessage(ChatColor.RED + "Ese color no existe.");
                            return false;
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "No indicaste un texto.");
                        return false;
                    }
                }
            }
        }
        
        return false;
    }
}
