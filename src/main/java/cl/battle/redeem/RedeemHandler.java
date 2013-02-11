package cl.battle.redeem;

import cl.battle.redeem.utils.NBTEditor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
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
        int worth;
        
        try {
            worth = config.getInt(giftPath + option);
        } catch (Exception e) {
            return -1;
        }
        
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
    
    private ItemStack createToken(int amount)
    {
        ItemStack token = new ItemStack(Material.EMERALD);
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
    
    public boolean redeemItem()
    {
        return false;
    }
    
    public boolean redeemEnchant()
    {
        return false;
    }
    
    public boolean redeemExp(Player p, int amount)
    {
        return false;
    }
    
    public boolean redeemKit()
    {
        return false;
    }
    
    public boolean redeemCustomName()
    {
        return false;
    }
    
    public boolean redeemCustomLore()
    {
        return false;
    }
}
