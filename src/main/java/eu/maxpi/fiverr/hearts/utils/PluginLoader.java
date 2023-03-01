package eu.maxpi.fiverr.hearts.utils;

import eu.maxpi.fiverr.hearts.Hearts;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class PluginLoader {

    public static HashMap<String, String> lang = new HashMap<>();
    public static HashMap<String, Integer> cmd = new HashMap<>();
    public static HashMap<String, Double> settings = new HashMap<>();

    public static void load(){
        Hearts.getInstance().saveResource("config.yml", false);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Hearts.getInstance().getDataFolder() + "/config.yml"));

        config.getConfigurationSection("lang").getKeys(false).forEach(s -> lang.put(s, ColorTranslator.translate(config.getString("lang." + s))));
        config.getConfigurationSection("custommodel").getKeys(false).forEach(s -> cmd.put(s, config.getInt("custommodel." + s)));
        config.getConfigurationSection("settings").getKeys(false).forEach(s -> settings.put(s, config.getDouble("settings." + s)));
    }

}
