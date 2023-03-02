package eu.maxpi.fiverr.hearts.hearts;

import com.lkeehl.tagapi.TagAPI;
import com.lkeehl.tagapi.api.Tag;
import eu.maxpi.fiverr.hearts.Hearts;
import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BloodHeart extends Heart {

    public HashMap<Player, Long> beatCooldown = new HashMap<>();
    public HashMap<Player, Long> surgeCooldown = new HashMap<>();


    public HashMap<Player, Long> showingTimer = new HashMap<>();

    public BloodHeart() {
        super("bloodheart");
    }

    @Override
    public void execute(Player p){
        if(beatCooldown.getOrDefault(p, 0L) <= ZonedDateTime.now().toEpochSecond()) beatCooldown.remove(p);
        if(surgeCooldown.getOrDefault(p, 0L) <= ZonedDateTime.now().toEpochSecond()) surgeCooldown.remove(p);

        if(showingTimer.containsKey(p)) manageTimer(p);
        HeartManager.addRelative(p, getAmount(p) * PluginLoader.settings.get("bloodheart-hearts"));
    }

    @EventHandler
    public void heartBeat(PlayerInteractEvent event){
        if(!event.getAction().name().contains("LEFT")) return;

        if(!isItem(event.getItem())) return;
        if(getAmount(event.getPlayer()) < 10) return;
        if(beatCooldown.containsKey(event.getPlayer())) return;

        double rad = 1.5 * (getAmount(event.getPlayer()));
        Bukkit.getScheduler().runTaskAsynchronously(Hearts.getInstance(), () -> {
            sphere(event.getPlayer().getLocation(), 2.5, Particle.HEART);
            cylinder(event.getPlayer().getLocation(), rad);
        });

        event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), rad, rad, rad, e -> e instanceof Monster || e instanceof Player).forEach(e -> {
            e.setVelocity(e.getLocation().getDirection().normalize().add(new Vector(0, 3, 0)).multiply(-1).multiply(e.getLocation().distance(event.getPlayer().getLocation())));
            ((LivingEntity)e).damage(rad - e.getLocation().distance(event.getPlayer().getLocation()) + ((getAmount(event.getPlayer()) - 10) * 1.5D));
        });

        event.getPlayer().damage(2D);

        beatCooldown.put(event.getPlayer(), ZonedDateTime.now().toEpochSecond() + (long)(double)PluginLoader.settings.get("bloodheart-heartbeat-cooldown"));
        event.getPlayer().sendMessage(PluginLoader.lang.get("heartbeat"));
    }

    public void cylinder(Location loc, double r) {
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        World w = loc.getWorld();
        double rSquared = r * r;
        for (double x = cx - r; x <= cx +r; x += 2) {
            for (double z = cz - r; z <= cz +r; z += 2) {
                if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
                    w.spawnParticle(Particle.REDSTONE, x, cy, z, 1, new Particle.DustOptions(Color.RED, 6));
                }
            }
        }
    }

    public void sphere(Location loc, double r, Particle p){
        Location l = loc.clone();
        for(double phi = 0; phi <= Math.PI; phi += Math.PI / 10) {
            double y = r * Math.cos(phi) + 1.5;
            for(double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 20) {
                double x = r * Math.cos(theta) * Math.sin(phi);
                double z = r * Math.sin(theta) * Math.sin(phi);

                l.add(x, y, z);
                l.getWorld().spawnParticle(p, l, 1, 0F, 0F, 0F, 0.001);
                l.subtract(x, y, z);
            }
        }
    }

    @EventHandler
    public void heartSurge(PlayerInteractEvent event){
        if(!event.getAction().name().contains("RIGHT")) return;

        if(!isItem(event.getItem())) return;
        if(getAmount(event.getPlayer()) < 5) return;
        if(surgeCooldown.containsKey(event.getPlayer())) return;

        int duration = (10 + ((getAmount(event.getPlayer()) - 5) * 2));

        ArmorStand stand = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), ArmorStand.class);
        stand.setInvisible(true);
        stand.setSmall(true);
        stand.setCollidable(false);
        stand.setCustomNameVisible(true);
        stand.setInvulnerable(true);
        stand.setNoDamageTicks(Integer.MAX_VALUE);
        stand.setGravity(false);
        stand.setCustomName("§c§l[" + (duration) + "]");
        event.getPlayer().addPassenger(stand);


        showingTimer.put(event.getPlayer(), ZonedDateTime.now().toEpochSecond() + (duration));

        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 20, 1));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration * 20, 1));

        surgeCooldown.put(event.getPlayer(), ZonedDateTime.now().toEpochSecond() + (long)(double)PluginLoader.settings.get("bloodheart-heartsurge-cooldown"));
        event.getPlayer().sendMessage(PluginLoader.lang.get("heartsurge"));
    }

    public void manageTimer(Player p){
        if(p.getPassengers().size() == 0) return;
        if(!showingTimer.containsKey(p) || showingTimer.get(p) <= ZonedDateTime.now().toEpochSecond()){
            ArmorStand stand = (ArmorStand) p.getPassengers().get(0);
            stand.remove();
            p.getPassengers().forEach(p::removePassenger);
            showingTimer.remove(p);
            return;
        }

        long time = showingTimer.get(p) - ZonedDateTime.now().toEpochSecond();
        ArmorStand stand = (ArmorStand) p.getPassengers().get(0);
        stand.setCustomName("§c§l[" + time + "]");
    }
}
