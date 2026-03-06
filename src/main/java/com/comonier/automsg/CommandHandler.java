package com.comonier.automsg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class CommandHandler implements CommandExecutor, TabCompleter {
    private AutoMSG plugin;
    public CommandHandler(AutoMSG plugin) { this.plugin = plugin; }
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            s.sendMessage(plugin.getMsg("usage"));
            return true;
        }
        String sub = args[0].toLowerCase();
        if (sub.equals("reload")) {
            plugin.reloadConfig();
            plugin.loadLang();
            plugin.startTimer();
            s.sendMessage(plugin.getMsg("reload"));
        } else if (sub.equals("help")) {
            s.sendMessage(plugin.getMsg("usage"));
        } else if (sub.equals("list")) {
            s.sendMessage(plugin.color(plugin.getMsg("list_header")));
            List<String> list = plugin.getConfig().getStringList("Announcements");
            for (int i = 0; i > -1; i++) {
                if (i >= list.size()) break;
                s.sendMessage(plugin.color("&eID: " + i + " &f- " + list.get(i)));
            }
        } else if (sub.equals("add") && args.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i > 0; i++) {
                if (i >= args.length) break;
                sb.append(args[i]).append(" ");
            }
            List<String> list = plugin.getConfig().getStringList("Announcements");
            list.add(sb.toString().trim());
            plugin.getConfig().set("Announcements", list);
            plugin.saveConfig();
            s.sendMessage(plugin.getMsg("add_success"));
        } else if (sub.equals("remove") && args.length > 1) {
            try {
                int id = Integer.parseInt(args[1]);
                List<String> list = plugin.getConfig().getStringList("Announcements");
                if (id >= 0 && id < list.size()) {
                    list.remove(id);
                    plugin.getConfig().set("Announcements", list);
                    plugin.saveConfig();
                    s.sendMessage(plugin.getMsg("remove_success").replace("{id}", String.valueOf(id)));
                } else { s.sendMessage(plugin.getMsg("invalid_id")); }
            } catch (Exception e) { s.sendMessage(plugin.getMsg("invalid_id")); }
        } else {
            s.sendMessage(plugin.getMsg("usage"));
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("reload", "help", "add", "list", "remove");
        return new ArrayList<>();
    }
}
