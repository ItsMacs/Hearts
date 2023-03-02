package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.Hearts;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class HeartOfShadowsHeart extends Heart{

    public HashMap<Player, Location> oldLocs = new HashMap<>();

    public HeartOfShadowsHeart() {
        super("heartofshadows", Material.ELYTRA);
    }

    @Override
    public void execute(Player p) {
        Bukkit.getOnlinePlayers().forEach(p1 -> p1.showPlayer(Hearts.getInstance(), p));
        if(!isItem(p.getInventory().getArmorContents()[2])) return;

        if(!oldLocs.containsKey(p)){
            oldLocs.put(p, p.getLocation().clone());
            Bukkit.getOnlinePlayers().forEach(p1 -> p1.showPlayer(Hearts.getInstance(), p));
            return;
        }

        Location l = oldLocs.get(p);
        if(l.getWorld() != p.getLocation().getWorld()) return;
        if(l.distance(p.getLocation()) >= 0.5){
            oldLocs.put(p, p.getLocation().clone());
            Bukkit.getOnlinePlayers().forEach(p1 -> p1.showPlayer(Hearts.getInstance(), p));
            return;
        }

        Bukkit.getOnlinePlayers().forEach(p1 -> p1.hidePlayer(Hearts.getInstance(), p));
        //p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 25, 0, false, false));
    }
}
