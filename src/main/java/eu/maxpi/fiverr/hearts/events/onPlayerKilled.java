package eu.maxpi.fiverr.hearts.events;

import eu.maxpi.fiverr.hearts.Hearts;
import eu.maxpi.fiverr.hearts.hearts.BloodHeart;
import eu.maxpi.fiverr.hearts.utils.HeartManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerKilled implements Listener {

    @EventHandler
    public void playerKill(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player killer)) return;
        if(!(event.getEntity() instanceof Player killed)) return;

        if(killed.getHealth() - event.getFinalDamage() > 0) return;

        killer.getInventory().addItem(Hearts.hearts.get("bloodheart").item());

        if(Hearts.hearts.get("bloodheart").getAmount(killed) != 0) return;

        HeartManager.setHealth(killed, HeartManager.getRealHealth(killed) - 2D);
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        BloodHeart b = (BloodHeart)Hearts.hearts.get("bloodheart");

        if(b.getAmount(event.getDrops()) <= 1) return;

        event.getEntity().getInventory().addItem(b.item(b.getAmount(event.getDrops()) - 1));

        event.getDrops().removeIf(b::isItem);
        event.getDrops().add(b.item());
    }

}
