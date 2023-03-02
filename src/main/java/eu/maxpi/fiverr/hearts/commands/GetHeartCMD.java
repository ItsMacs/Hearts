package eu.maxpi.fiverr.hearts.commands;

import eu.maxpi.fiverr.hearts.Hearts;
import eu.maxpi.fiverr.hearts.utils.PluginLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class GetHeartCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("hearts.heart")){
            sender.sendMessage(PluginLoader.lang.get("no-permission"));
            return true;
        }

        if(!Hearts.hearts.containsKey(args[0].toLowerCase())) {
            sender.sendMessage(PluginLoader.lang.get("no-heart"));
            return true;
        }

        ((Player)sender).getInventory().addItem(Hearts.hearts.get(args[0].toLowerCase()).item());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            return Hearts.hearts.keySet().stream().toList();
        }
        return Hearts.hearts.keySet().stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
    }
}
