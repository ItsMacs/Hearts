package eu.maxpi.fiverr.hearts;

import com.lkeehl.tagapi.TagAPI;
import eu.maxpi.fiverr.hearts.commands.GetHeartCMD;
import eu.maxpi.fiverr.hearts.events.onPlayerKilled;
import eu.maxpi.fiverr.hearts.events.onPlayerQuit;
import eu.maxpi.fiverr.hearts.hearts.*;
import eu.maxpi.fiverr.hearts.hearts.armor.HeartboundBoots;
import eu.maxpi.fiverr.hearts.hearts.armor.HeartboundChestplate;
import eu.maxpi.fiverr.hearts.hearts.armor.HeartboundHelmet;
import eu.maxpi.fiverr.hearts.hearts.armor.HeartboundLeggings;
import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;

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
        new HeartOfShadowsHeart();
        new HeartofTheStormHeart();
        new HeartofThePhoenixHeart();

        new HeartboundBoots();
        new HeartboundChestplate();
        new HeartboundLeggings();
        new HeartboundHelmet();
    }

    private void loadCommands(){
        Objects.requireNonNull(getCommand("getheart")).setExecutor(new GetHeartCMD());
        Objects.requireNonNull(getCommand("getheart")).setTabCompleter(new GetHeartCMD());
    }

    private void loadEvents(){
        Bukkit.getPluginManager().registerEvents(new onPlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new onPlayerKilled(), this);
    }

    private void loadTasks(){
        new BukkitRunnable() {
            @Override
            public void run() {
                HeartManager.calcRelativeHearts();
            }
        }.runTaskTimer(this, 0L, 20L);

        BloodHeart b = (BloodHeart)hearts.get("bloodheart");
        new BukkitRunnable() {
            @Override
            public void run() {
                b.showingTimer.keySet().forEach(p -> b.sphere(p.getLocation(), 2, Particle.BUBBLE_POP));
            }
        }.runTaskTimerAsynchronously(this, 0L, 2L);
    }


    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            HeartManager.setHealth(p, HeartManager.getRealHealth(p));
        });

        Bukkit.getLogger().info("Hearts by fiverr.com/macslolz was disabled successfully!");
    }
}
