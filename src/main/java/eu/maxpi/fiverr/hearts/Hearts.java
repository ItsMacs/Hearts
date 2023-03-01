package eu.maxpi.fiverr.hearts;

import eu.maxpi.fiverr.hearts.commands.GetHeartCMD;
import eu.maxpi.fiverr.hearts.hearts.BloodHeart;
import eu.maxpi.fiverr.hearts.hearts.Heart;
import eu.maxpi.fiverr.hearts.utils.HeartManager;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.Bukkit;
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
        hearts.put("bloodheart", new BloodHeart());
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
