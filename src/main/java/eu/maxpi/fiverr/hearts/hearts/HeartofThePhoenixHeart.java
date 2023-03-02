package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.utils.HeartManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeartofThePhoenixHeart extends Heart {

    public HeartofThePhoenixHeart() {
        super("heartofthephoenix", Material.TOTEM_OF_UNDYING);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onTotem(EntityResurrectEvent event){
        if(event.isCancelled()) return;
        if(!(event.getEntity() instanceof Player p)) return;

        if(getAmount(p) == 0) return;
        if(HeartManager.getHealth(p) < 30D) return;

        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2));
    }

    @Override
    public void execute(Player p) {

    }
}
