package eu.maxpi.fiverr.hearts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hearts extends JavaPlugin {

    private static Hearts instance = null;
    public static Hearts getInstance() { return Hearts.instance; }
    private static void setInstance(Hearts in) { Hearts.instance = in; }

    @Override
    public void onEnable() {
        setInstance(this);

        loadCommands();
        loadEvents();
        loadTasks();

        Bukkit.getLogger().info("Hearts by fiverr.com/macslolz was enabled successfully!");
    }


    private void loadCommands(){

    }

    private void loadEvents(){

    }

    private void loadTasks(){

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Hearts by fiverr.com/macslolz was disabled successfully!");
    }
}
