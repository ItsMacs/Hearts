package eu.maxpi.fiverr.hearts.events;

import eu.maxpi.fiverr.hearts.utils.HeartManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerQuit implements Listener {

    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        HeartManager.setHealth(event.getPlayer(), HeartManager.getRealHealth(event.getPlayer()));
        HeartManager.relativeHearts.remove(event.getPlayer());
        HeartManager.relativeHeartsBefore.remove(event.getPlayer());
    }

}
