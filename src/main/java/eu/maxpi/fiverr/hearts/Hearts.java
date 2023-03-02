package eu.maxpi.fiverr.hearts;

import eu.maxpi.fiverr.hearts.commands.GetHeartCMD;
import eu.maxpi.fiverr.hearts.hearts.*;
import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;

/**
 * DONE Blood Heart: An item that increases the player's max health and unlocks unique abilities. As the player collects more blood hearts, the strength of these abilities increases, making them valuable for any player looking to enhance their gameplay.
 *
 *
 *
 * DONE Heartbound Sword: A powerful sword that deals extra damage when the player has more than five hearts.
 *
 *
 *
 * Heartbound Armor: A set of armor that gives the player increased resistance to damage when they have more than ten hearts.
 *
 *
 *
 * DONE Heart of Iron: A ring that increases the player's maximum health by 1 for every 5 hearts they have.
 *
 *
 *
 * Heart of Fire: A potion that makes the player immune to fire damage for a short period of time, the effect duration increases for every heart the player has.
 *
 *
 *
 * Heart of Shadows: A cloak that makes the player invisible when they are standing still and have more than 5 hearts
 *
 *
 *
 * Heart of the Storm: A trinket that calls lightning to strike enemies when the player is hit and has more than 10 hearts.
 *
 *
 *
 * Heart of the Phoenix: A totem that revives the player when they die with an extra heart if they have more than 15 hearts.
 *
 *
 *
 * This plugin should be compatible with the lifesteal plugin.
 *
 *
 *
 * The two abilities for the blood heart :
 *
 *
 *
 * Heart Beat: An ability that creates a shockwave that damages enemies. The damage of the shockwave increases with the number of blood hearts the player has. For every blood heart above the 10 required to obtain Heart Beat, the damage becomes stronger.
 *
 *
 *
 * Heart Surge: An ability that increases the player's speed and damage for a short time. The base duration of the effect is 10 seconds, and for every additional blood heart the player has above the 5 required to obtain Heart Surge, the duration increases by 2 seconds.
 */
public final class Hearts extends JavaPlugin {

    private static Hearts instance = null;
    public static Hearts getInstance() { return Hearts.instance; }
    private static void setInstance(Hearts in) { Hearts.instance = in; }

    public static HashMap<String, Heart> hearts = new HashMap<>();

    @Override
    public void onEnable() {
        setInstance(this);

        PluginLoader.load();

        loadHearts();

        loadCommands();
        loadEvents();
        loadTasks();

        Bukkit.getLogger().info("Hearts by fiverr.com/macslolz was enabled successfully!");
    }

    private void loadHearts(){
        new BloodHeart();
        new HeartboundSword();
        new HeartofIronHeart();
        new HeartofFireHeart();
    }

    private void loadCommands(){
        Objects.requireNonNull(getCommand("getheart")).setExecutor(new GetHeartCMD());
    }

    private void loadEvents(){

    }

    private void loadTasks(){
        new BukkitRunnable() {
            @Override
            public void run() {
                HeartManager.calcRelativeHearts();
            }
        }.runTaskTimer(this, 0L, 20L);
    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Hearts by fiverr.com/macslolz was disabled successfully!");
    }
}
