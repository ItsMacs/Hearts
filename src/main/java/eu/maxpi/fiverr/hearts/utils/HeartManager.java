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
            Hearts.hearts.values().forEach(h -> h.tryExecute(p));

            double relativeBefore = relativeHeartsBefore.getOrDefault(p, 0D);
            double relativeNow = relativeHearts.getOrDefault(p, 0D);
            if(Objects.equals(relativeBefore, relativeNow)) return;

            //oh god
            double res = Math.abs(Math.max(relativeNow, relativeBefore) - Math.min(relativeNow, relativeBefore));
            res = res * (relativeNow >= relativeBefore ? +1 : -1);

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
