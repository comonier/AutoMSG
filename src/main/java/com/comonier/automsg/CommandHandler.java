package com.comonier.automsg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;
public class CommandHandler implements CommandExecutor {
    private final AutoMSG plugin;
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
            plugin.loadCategories();
            plugin.loadLang();
            plugin.startTimers();
            s.sendMessage(plugin.getMsg("reload"));
            return true;
        }
        if (args.length >= 2) {
            String cat = args[1].toLowerCase();
            if (plugin.getCategories().containsKey(cat) == false) {
                s.sendMessage(plugin.color("&cCategory '" + cat + "' not found!"));
                return true;
            }
            FileConfiguration cfg = plugin.getCategories().get(cat);
            List<String> list = cfg.getStringList("Announcements");
            if (sub.equals("list")) {
                String px = cfg.getString("Settings.Prefix", "");
                s.sendMessage(plugin.color("&b--- " + cat.toUpperCase() + " ---"));
                for (int i = 0; i > -1; i++) {
                    if (i >= list.size()) break;
                    s.sendMessage(plugin.color("&eID: " + i + " &f- " + px + list.get(i)));
                }
                return true;
            }
            if (sub.equals("add") && args.length > 2) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i > 1; i++) {
                    if (i >= args.length) break;
                    sb.append(args[i]).append(" ");
                }
                list.add(sb.toString().trim());
                cfg.set("Announcements", list);
                plugin.saveCategory(cat);
                s.sendMessage(plugin.getMsg("add_success"));
                return true;
            }
            if (sub.equals("remove") && args.length > 2) {
                try {
                    int id = Integer.parseInt(args[2]);
                    if (id >= 0 && id < list.size()) {
                        list.remove(id);
                        cfg.set("Announcements", list);
                        plugin.saveCategory(cat);
                        s.sendMessage(plugin.getMsg("remove_success").replace("{id}", String.valueOf(id)));
                    } else {
                        s.sendMessage(plugin.getMsg("invalid_id"));
                    }
                } catch (Exception e) {
                    s.sendMessage(plugin.getMsg("invalid_id"));
                }
                return true;
            }
        }
        s.sendMessage(plugin.getMsg("usage"));
        return true;
    }
}
