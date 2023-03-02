package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class HeartofTheStormHeart extends Heart {

    public HashMap<Player, Long> cooldown = new HashMap<>();
    public HeartofTheStormHeart() {
        super("heartofthestorm");
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player p)) return;
        if(cooldown.getOrDefault(p, 0L) > ZonedDateTime.now().toEpochSecond()) return;

        if(getAmount(p) == 0) return;
        if(HeartManager.getHealth(p) > 20D) return;

        cooldown.put(p, ZonedDateTime.now().toEpochSecond() + (long)(double) PluginLoader.settings.get("heartofthestorm-cooldown"));
        p.getWorld().strikeLightning(event.getDamager().getLocation());
    }

    @Override
    public void execute(Player p) {
        if(cooldown.getOrDefault(p, 0L) <= ZonedDateTime.now().toEpochSecond()) cooldown.remove(p);
    }
}
