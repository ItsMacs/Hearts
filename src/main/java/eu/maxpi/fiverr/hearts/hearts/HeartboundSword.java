package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeartboundSword extends Heart {

    public HeartboundSword() {
        super("heartboundsword", Material.NETHERITE_SWORD);
    }

    @EventHandler
    public void entityAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player p)) return;

        if(!isItem(p.getInventory().getItemInMainHand())) return;
        if(HeartManager.getRealHealth(p) < 10D) return;

        event.setDamage(event.getFinalDamage() * PluginLoader.settings.get("heartboundsword-damage-increase"));
    }

    @Override
    public void execute(Player p) {

    }
}
