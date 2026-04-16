package com.comonier.automsg;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
public class AutoMSG extends JavaPlugin {
    private FileConfiguration lang;
    private final Map<String, FileConfiguration> categories = new HashMap<>();
    private final Map<String, Integer> indexes = new HashMap<>();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadCategories();
        loadLang();
        getCommand("am").setExecutor(new CommandHandler(this));
        getCommand("am").setTabCompleter(new TabHandler(this));
        startTimers();
        String currentVersion = getDescription().getVersion();
        getLogger().info("Plugin enabled! Current version: " + currentVersion);
    }
    public void loadCategories() {
        categories.clear();
        String[] files = {"votes.yml", "tips.yml", "links.yml", "rules.yml", "motd.yml", "other.yml"};
        for (String name : files) {
            File f = new File(getDataFolder(), name);
            if (f.exists() == false) saveResource(name, false);
            categories.put(name.replace(".yml", ""), YamlConfiguration.loadConfiguration(f));
        }
    }
    public void loadLang() {
        String l = getConfig().getString("Settings.Language", "en");
        File f = new File(getDataFolder(), "messages_" + l + ".yml");
        if (f.exists() == false) {
            saveResource("messages_en.yml", false);
            saveResource("messages_pt.yml", false);
            saveResource("messages_es.yml", false);
            saveResource("messages_ru.yml", false);
        }
        lang = YamlConfiguration.loadConfiguration(f);
    }
    public void startTimers() {
        Bukkit.getScheduler().cancelTasks(this);
        for (String key : categories.keySet()) {
            FileConfiguration cfg = categories.get(key);
            if (cfg.getBoolean("Settings.Enabled", true) == false) continue;
            long ticks = parseTime(cfg.getString("Settings.Interval", "10m"));
            indexes.put(key, 0);
            Bukkit.getScheduler().runTaskTimer(this, () -> broadcastCategory(key), ticks, ticks);
        }
    }
    public void broadcastCategory(String key) {
        FileConfiguration cfg = categories.get(key);
        List<String> msgs = cfg.getStringList("Announcements");
        if (msgs.isEmpty()) return;
        int idx = indexes.getOrDefault(key, 0);
        if (idx >= msgs.size()) idx = 0;
        String prefix = cfg.getString("Settings.Prefix", "");
        String raw = prefix + msgs.get(idx);
        boolean playSound = cfg.getBoolean("Settings.EnableSound", true);
        String sName = cfg.getString("Settings.SoundName", "ENTITY_VILLAGER_YES");
        float vol = (float) cfg.getDouble("Settings.SoundVolume", 1.0);
        float pit = (float) cfg.getDouble("Settings.SoundPitch", 1.0);
        Bukkit.getOnlinePlayers().forEach(p -> {
            String m = raw.replace("{player}", p.getName()).replace("{online_players}", String.valueOf(Bukkit.getOnlinePlayers().size()));
            p.sendMessage(color(m));
            if (playSound) {
                try {
                    p.playSound(p.getLocation(), Sound.valueOf(sName.toUpperCase()), vol, pit);
                } catch (Exception ignored) {}
            }
        });
        String url = cfg.getString("Settings.DiscordWebhook", "");
        if (url.isEmpty() == false) {
            String plain = ChatColor.stripColor(color(raw));
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> new DiscordWebhook(url).send(plain));
        }
        indexes.put(key, idx + 1);
    }
    public String color(String msg) {
        Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = hexPattern.matcher(msg);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String hex = matcher.group();
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.substring(1).toCharArray()) {
                replacement.append('§').append(c);
            }
            matcher.appendReplacement(sb, replacement.toString());
        }
        matcher.appendTail(sb);
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }
    public String getMsg(String path) {
        return color(lang.getString("prefix") + lang.getString(path));
    }
    private long parseTime(String s) {
        try {
            long v = Long.parseLong(s.replaceAll("[^0-9]", ""));
            if (s.contains("d")) return v * 1728000L;
            if (s.contains("h")) return v * 72000L;
            if (s.contains("m")) return v * 1200L;
            return v * 20L;
        } catch (Exception e) { return 12000L; }
    }
    public Map<String, FileConfiguration> getCategories() { return categories; }
    public void saveCategory(String key) {
        try {
            categories.get(key).save(new File(getDataFolder(), key + ".yml"));
        } catch (Exception ignored) {}
    }
}
