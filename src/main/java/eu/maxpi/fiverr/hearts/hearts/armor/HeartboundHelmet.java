package eu.maxpi.fiverr.hearts.hearts.armor;

import eu.maxpi.fiverr.hearts.hearts.Heart;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeartboundHelmet extends Heart {
    public HeartboundHelmet() {
        super("heartboundhelmet", Material.NETHERITE_HELMET);
    }

    @EventHandler
    public void entityAttack(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player p)) return;

        if(!isItem(p.getInventory().getArmorContents()[3])) return;
        event.setDamage(event.getFinalDamage() - (event.getFinalDamage() / 10));
    }

    @Override
    public void execute(Player p) {

    }
}
