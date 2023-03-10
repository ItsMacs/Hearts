package eu.maxpi.fiverr.hearts.hearts;

import de.tr7zw.nbtapi.NBTItem;
import eu.maxpi.fiverr.hearts.Hearts;
import eu.maxpi.fiverr.hearts.utils.ColorTranslator;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public abstract class Heart implements Listener {

    public String internalName;
    public String name;
    public String lore;

    public int customModelData;
    public Material material = Material.RED_DYE;

    public Heart(String internalName){
        this.internalName = internalName;
        this.name = PluginLoader.lang.get(internalName + "-name");
        this.lore = PluginLoader.lang.get(internalName + "-lore");
        this.customModelData = PluginLoader.cmd.get(internalName);

        Hearts.hearts.put(internalName, this);
        Bukkit.getPluginManager().registerEvents(this, Hearts.getInstance());
    }

    public Heart(String internalName, Material mat){
        this.internalName = internalName;
        this.name = PluginLoader.lang.get(internalName + "-name");
        this.lore = PluginLoader.lang.get(internalName + "-lore");
        this.customModelData = PluginLoader.cmd.get(internalName);
        this.material = mat;

        Hearts.hearts.put(internalName, this);
        Bukkit.getPluginManager().registerEvents(this, Hearts.getInstance());
    }

    public void tryExecute(Player p){
        if(getAmount(p) == 0) return;
        execute(p);
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

    public int getAmount(List<ItemStack> p){
        int amt = 0;
        for(ItemStack i : p){
            if(!isItem(i)) continue;

            amt += i.getAmount();
        }

        return amt;
    }

    public ItemStack item(){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore.split("\n")));
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);

        NBTItem i = new NBTItem(item);
        i.setString("heart", internalName);
        return i.getItem();
    }

    public ItemStack item(int amount){
        ItemStack i = item();
        i.setAmount(amount);
        return i;
    }

    public boolean isItem(ItemStack i){
        if(i == null) return false;
        if(i.getType() == Material.AIR) return false;

        NBTItem item = new NBTItem(i);
        return item.hasKey("heart") && item.getString("heart").equalsIgnoreCase(internalName);
    }

}
