package cl.battle.redeem.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class NBTEditor 
{
    private static String COLOR_PREFIX = "c:";
    
    public boolean setItemName(Player p, String name, String newColor)
    {
        String color = "WHITE";
        ItemStack itemStackInHand = p.getItemInHand();
        if (newColor != null)
            color = newColor.toUpperCase();
        
        try{
            String newItemName = (ChatColor.valueOf(color) + name);
            ItemMeta itemMeta = itemStackInHand.getItemMeta();
            itemMeta.setDisplayName(newItemName);
            itemStackInHand.setItemMeta(itemMeta);
        } catch (Exception e){
            // Color doesn't matches
            return false;
        }

        return true;
    }
    
    public boolean setItemLore(Player p, String lore, String newColor)
    {
        String color = "DARK_PURPLE";
        ItemStack itemStackInHand = p.getItemInHand();
        if (newColor != null)
            color = newColor.toUpperCase();

        ItemMeta itemMeta = itemStackInHand.getItemMeta();
        List<String> loreList = new ArrayList<String>();
        if (!(itemMeta.getLore() == null)){
            loreList = itemMeta.getLore();
        }
        try {
            loreList.add(ChatColor.valueOf(color) +  (ChatColor.ITALIC + lore));
        } catch (Exception ex) {
            // Color doesn't exists
            return false;
        }

        itemMeta.setLore(loreList);
        itemStackInHand.setItemMeta(itemMeta);
        
        return true;
    }
}
