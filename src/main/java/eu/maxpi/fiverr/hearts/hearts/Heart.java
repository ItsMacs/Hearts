package eu.maxpi.fiverr.hearts.hearts;

import de.tr7zw.nbtapi.NBTItem;
import eu.maxpi.fiverr.hearts.utils.ColorTranslator;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Heart {

    public String internalName;
    public String name;
    public String lore;

    public int customModelData;

    public Heart(String internalName){
        this.internalName = internalName;
        this.name = PluginLoader.lang.get(internalName + "-name");
        this.lore = PluginLoader.lang.get(internalName + "-lore");
        this.customModelData = PluginLoader.cmd.get(internalName);
    }

    public abstract void execute(Player p);

    public int getAmount(Player p){
        int amt = 0;
        for(ItemStack i : p.getInventory()){
            if(!isItem(i)) continue;

            amt += i.getAmount();
        }

        return amt;
    }

    public ItemStack item(){
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore.split("\n")));
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);

        NBTItem i = new NBTItem(item);
        i.setString("heart", internalName);
        return i.getItem();
    }

    public boolean isItem(ItemStack i){
        if(i == null) return false;

        NBTItem item = new NBTItem(i);
        return item.hasKey("heart") && item.getString("heart").equalsIgnoreCase(internalName);
    }

}
