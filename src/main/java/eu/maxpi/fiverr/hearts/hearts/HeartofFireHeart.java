package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeartofFireHeart extends Heart{

    public HeartofFireHeart() {
        super("heartoffire", Material.POTION);
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event){
        if(!isItem(event.getItem())) return;

        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * (int)(PluginLoader.settings.get("heartoffire-duration") * HeartManager.getHealth(event.getPlayer())), 2));
    }

    @Override
    public void execute(Player p) {

    }
}
