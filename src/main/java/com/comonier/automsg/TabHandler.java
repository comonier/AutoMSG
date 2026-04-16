package com.comonier.automsg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class TabHandler implements TabCompleter {
    private final AutoMSG plugin;
    public TabHandler(AutoMSG plugin) { this.plugin = plugin; }
    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subs = Arrays.asList("reload", "list", "add", "remove");
            return subs.stream().filter(f -> f.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (sub.equals("list") || sub.equals("add") || sub.equals("remove")) {
                return new ArrayList<>(plugin.getCategories().keySet()).stream()
                        .filter(f -> f.startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
