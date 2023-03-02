package eu.maxpi.fiverr.hearts.hearts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class HeartofFireHeart extends Heart{

    public HeartofFireHeart() {
        super("heartoffire", Material.POTION);
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event){
        if(!isItem(event.getItem())) return;


    }

    @Override
    public void execute(Player p) {

    }
}
