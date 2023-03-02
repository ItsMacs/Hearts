package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.Hearts;
import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.time.ZonedDateTime;
import java.util.HashMap;

public class BloodHeart extends Heart {

    public static HashMap<Player, Long> beatCooldown = new HashMap<>();
    public static HashMap<Player, Long> surgeCooldown = new HashMap<>();

    public BloodHeart() {
        super("bloodheart");
    }

    @Override
    public void execute(Player p){
        if(beatCooldown.getOrDefault(p, 0L) <= ZonedDateTime.now().toEpochSecond()) beatCooldown.remove(p);
        if(surgeCooldown.getOrDefault(p, 0L) <= ZonedDateTime.now().toEpochSecond()) surgeCooldown.remove(p);

        HeartManager.addRelative(p, getAmount(p) * PluginLoader.settings.get("bloodheart-hearts"));
    }

    @EventHandler
    public void heartBeat(PlayerInteractEvent event){
        if(!event.getAction().name().contains("LEFT")) return;

        if(!isItem(event.getItem())) return;
        if(beatCooldown.containsKey(event.getPlayer())) return;

        double rad = 1.5 * getAmount(event.getPlayer());
        event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), rad, rad, rad, e -> e instanceof Monster || e instanceof Player).forEach(e -> {
            e.setVelocity(e.getLocation().getDirection().normalize().add(new Vector(0, 3, 0)).multiply(-1).multiply(e.getLocation().distance(event.getPlayer().getLocation())));
            ((LivingEntity)e).damage(rad - e.getLocation().distance(event.getPlayer().getLocation()));
        });

        event.getPlayer().damage(2D);

        beatCooldown.put(event.getPlayer(), ZonedDateTime.now().toEpochSecond() + (long)(double)PluginLoader.settings.get("bloodheart-heartbeat-cooldown"));
        event.getPlayer().sendMessage(PluginLoader.lang.get("heartbeat"));
    }

    @EventHandler
    public void heartSurge(PlayerInteractEvent event){
        if(!event.getAction().name().contains("RIGHT")) return;

        if(!isItem(event.getItem())) return;
        if(surgeCooldown.containsKey(event.getPlayer())) return;

        double rad = 1.5 * getAmount(event.getPlayer());
        event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), rad, rad, rad, e -> e instanceof Monster || e instanceof Player).forEach(e -> {
            e.setVelocity(e.getLocation().getDirection().normalize().multiply(-1).multiply(e.getLocation().distance(event.getPlayer().getLocation())));
            ((LivingEntity)e).damage(rad - e.getLocation().distance(event.getPlayer().getLocation()));
        });

        event.getPlayer().damage(4D);

        surgeCooldown.put(event.getPlayer(), ZonedDateTime.now().toEpochSecond() + (long)(double)PluginLoader.settings.get("bloodheart-heartsurge-cooldown"));
    }
}
