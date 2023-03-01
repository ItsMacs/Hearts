package eu.maxpi.fiverr.hearts.utils;

import eu.maxpi.fiverr.hearts.Hearts;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;

public class HeartManager {

    //Hearts currently added by the plugin
    public static HashMap<Player, Double> relativeHeartsBefore = new HashMap<>();
    public static HashMap<Player, Double> relativeHearts = new HashMap<>();

    public static void calcRelativeHearts(){
        relativeHeartsBefore = new HashMap<>(relativeHearts);
        relativeHearts.clear();

        Bukkit.getOnlinePlayers().forEach(p -> {
            Hearts.hearts.values().forEach(h -> h.execute(p));

            if(Objects.equals(relativeHeartsBefore.getOrDefault(p, 0D), relativeHearts.getOrDefault(p, 0D))) return;

            //oh god
            double res = Math.abs(Math.max(relativeHearts.getOrDefault(p, 0D), relativeHeartsBefore.getOrDefault(p, 0D)) - Math.min(relativeHearts.getOrDefault(p, 0D), relativeHeartsBefore.getOrDefault(p, 0D)));
            res = res * (relativeHearts.getOrDefault(p, 0D) >= relativeHeartsBefore.getOrDefault(p, 0D) ? +1 : -1);

            setHealth(p, getHealth(p) + res);
        });
    }

    public static double getRealHealth(Player p){
        return getHealth(p) - relativeHearts.getOrDefault(p, 0D);
    }

    public static void addRelative(Player p, double d){
        relativeHearts.put(p, d + relativeHearts.getOrDefault(p, 0D));
    }

    public static void setHealth(Player p, double h){
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(h);
    }

    public static double getHealth(Player p){
        return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }

}
