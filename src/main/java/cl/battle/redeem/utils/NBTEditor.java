package cl.battle.redeem.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class NBTEditor 
{    
    public boolean setItemName(Player p, String name)
    {
        ItemStack itemStackInHand = p.getItemInHand();
        
        try{
            ItemMeta itemMeta = itemStackInHand.getItemMeta();
            itemMeta.setDisplayName(name);
            itemStackInHand.setItemMeta(itemMeta);
        } catch (Exception e){
            return false;
        }

        return true;
    }
    
    public boolean setItemLore(Player p, String lore)
    {
        try {
            ItemStack itemStackInHand = p.getItemInHand();
            ItemMeta itemMeta = itemStackInHand.getItemMeta();
            List<String> loreList = new ArrayList<String>();

            if (itemMeta.getLore() != null)
                loreList = itemMeta.getLore();

            loreList.clear();
            loreList.add(lore);
            itemMeta.setLore(loreList);
            itemStackInHand.setItemMeta(itemMeta);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean addItemEnchant(Player p, Enchantment enchant)
    {
        ItemMeta itemMeta = p.getItemInHand().getItemMeta();
        
        try {
            int lvl = enchant.getMaxLevel();
            // Overwrite the previous enchant if exists
            p.getItemInHand().removeEnchantment(enchant);
            itemMeta.addEnchant(enchant, lvl, true);
            p.getItemInHand().setItemMeta(itemMeta);
        } catch (Exception e){
            p.sendMessage(ChatColor.RED + "No se puede aplicar el enchant a este item.");
            return false;
        }
        
        return true;
    }
}
