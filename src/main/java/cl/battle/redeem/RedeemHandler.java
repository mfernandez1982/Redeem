package cl.battle.redeem;

import cl.battle.redeem.utils.NBTEditor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class RedeemHandler 
{    
    NBTEditor editor = new NBTEditor();
    FileConfiguration config;
    
    public RedeemHandler(FileConfiguration config)
    {
        this.config = config;
    }
    
    private boolean addExpLevels(Player p, int amount)
    {
        try {
        p.giveExpLevels(amount);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    private boolean addItem(Player p, ItemStack item)
    {
        try {
            p.getInventory().addItem(item);
        } catch(Exception e) {
            return false;
        }
        
        return true;
    }
    
    private List<ItemStack> getPlayerTokens(Player p)
    {
        List<ItemStack> tokens = new ArrayList<ItemStack>();
        
        for (ItemStack i : p.getInventory().getContents())
        {
            if (i.getType() == Material.EMERALD)
            {
                if (i.hasItemMeta())
                {
                    if (i.getItemMeta().getDisplayName().contentEquals("Token de evento"))
                    {
                        tokens.add(i);
                    }
                }
            }
        }
        
        return (tokens.isEmpty() ? null : tokens);
    }
    
    private int getPlayerTokenAmount(Player p)
    {
        return getPlayerTokenAmount(getPlayerTokens(p));
    }
    
    private int getPlayerTokenAmount(List<ItemStack> tokens)
    {
        int amount = 0;
        
        for (ItemStack i : tokens)
        {
            amount += i.getAmount();
        }
        
        return amount;
    }
    
    private int getTokenWorth(String option)
    {
        String giftPath = "redeem.gift.";
        int worth = config.getInt(giftPath + option);;
        
        if (worth == 0)
            return -1;
        
        return worth;
    }
    
    private boolean hasEnoughTokens(Player p, int amount)
    {
        return getPlayerTokenAmount(p) > amount;
    }
    
    private boolean removeTokens(Player p, int amount)
    {
        for (ItemStack i : p.getInventory().getContents())
        {
            if (i.getType() == Material.EMERALD)
            {
                if (i.hasItemMeta())
                {
                    if (i.getItemMeta().getDisplayName().contentEquals(ChatColor.GREEN + "Token de evento"))
                    {
                        if (i.getAmount() > amount)
                        {
                            i.setAmount(i.getAmount() - amount);
                        }
                        else
                        {
                            amount -= i.getAmount();
                            p.getInventory().remove(i);
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    private ItemStack createItem(int id)
    {
        Material type;
        type = Material.getMaterial(id);
        
        if (type != null)
            return createItem(type, 1);
        else
            return null;
    }
    
    private ItemStack createItem(Material id, int amount)
    {
        return new ItemStack(id, amount);
    }
    
    private ItemStack createToken(int amount)
    {
        ItemStack token = createItem(Material.EMERALD, amount);
        ItemMeta meta = token.getItemMeta();
        
        meta.setDisplayName(ChatColor.GREEN + "Token de evento");
        List lore = meta.getLore();
        
        if(lore == null)
            lore = new LinkedList();
        
        lore.add(ChatColor.WHITE + "Usalo para canjear un premio");
        meta.setLore(lore);
        token.setItemMeta(meta);
        token.setAmount(amount);
        
        return token;
    }
    
    // REDEEMS
    
    public boolean redeemItem(Player p, int id)
    {
        ItemStack item = createItem(id);
        
        if (item != null)
        {
            // Importante: Definir los items en minusculas y segun el enum oficial
            int worth = getTokenWorth("item." + item.getType().toString().toLowerCase());
            
            if (worth == -1) 
            {
                if (getPlayerTokenAmount(p) > worth)
                {
                    removeTokens(p, worth);
                    return addItem(p, item);
                } else {
                    p.sendMessage(ChatColor.RED + "No tienes suficientes tokens para este item");
                }
            } else {
                p.sendMessage(ChatColor.RED + "El item que indicaste no esta disponible para canjeo");
            }
        } else {
            p.sendMessage(ChatColor.RED + "El item que indicaste no existe o es invalido");
        }
        
        return false;
    }
    
    public boolean redeemEnchant(Player p, Enchantment e)
    {
        if (e != null)
        {
            int worth = getTokenWorth("enchant");
            
            if (worth == -1) 
            {
                if (getPlayerTokenAmount(p) > worth)
                {
                    removeTokens(p, worth);
                    return editor.addItemEnchant(p, e);
                } else {
                    p.sendMessage(ChatColor.RED + "No tienes suficientes tokens para encantar");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Algo raro paso, avisale a un admin");
            }
        } else {
            p.sendMessage(ChatColor.RED + "El enchant que indicaste no existe o es invalido");
        }
        
        return false;
    }
    
    public boolean redeemExp(Player p, int amount)
    {
        if (amount > 10)
        {
            int worth = getTokenWorth("enchant");
            
            if (worth == -1) 
            {
                if (getPlayerTokenAmount(p) > worth)
                {
                    removeTokens(p, worth);
                    return addExpLevels(p, amount);
                } else {
                    p.sendMessage(ChatColor.RED + "No tienes suficientes tokens para encantar");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Algo raro paso, avisale a un admin");
            }
        } else {
            p.sendMessage(ChatColor.RED + "Lo minimo para canjear son 10 niveles");
        }
        
        return false;
    }
    
    public boolean redeemKit()
    {
        // NOT YET IMPLEMENTED
        return false;
    }
    
    public boolean redeemCustomName(Player p, String name)
    {
        return false;
    }
    
    public boolean redeemCustomLore(Player p, String lore)
    {
        return false;
    }
}
