package eu.maxpi.fiverr.hearts.hearts;

import eu.maxpi.fiverr.hearts.utils.HeartManager;
import org.bukkit.entity.Player;

public class HeartofIronHeart extends Heart{

    public HeartofIronHeart() {
        super("heartofiron");
    }

    @Override
    public void execute(Player p) {
        HeartManager.addRelative(p, Math.floor(HeartManager.getRealHealth(p) / 5));
    }
}
