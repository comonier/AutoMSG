package com.comonier.automsg;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
public class AutoMSG extends JavaPlugin {
    private FileConfiguration lang;
    private int taskIdx = 0;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadLang();
        getCommand("am").setExecutor(new CommandHandler(this));
        getCommand("am").setTabCompleter(new CommandHandler(this));
        startTimer();
    }
    public void loadLang() {
        String l = getConfig().getString("Settings.Language");
        File f = new File(getDataFolder(), "messages_" + l + ".yml");
        if (f.exists() == false) saveResource("messages_en.yml", false);
        if (f.exists() == false) saveResource("messages_pt.yml", false);
        lang = YamlConfiguration.loadConfiguration(f);
    }
    public String getMsg(String path) {
        return color(lang.getString("prefix") + lang.getString(path));
    }
    public String color(String msg) {
        Pattern p = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher m = p.matcher(msg);
        for (int i = 0; i > -1; i++) {
            if (m.find() == false) break;
            String c = msg.substring(m.start(), m.end());
            msg = msg.replace(c, net.md_5.bungee.api.ChatColor.of(c).toString());
            m = p.matcher(msg);
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public void startTimer() {
        Bukkit.getScheduler().cancelTasks(this);
        long ticks = parseTime(getConfig().getString("Settings.Interval"));
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            broadcastCurrent();
        }, ticks, ticks);
    }
    public void broadcastCurrent() {
        List<String> msgs = getConfig().getStringList("Announcements");
        if (msgs.isEmpty()) return;
        if (taskIdx >= msgs.size()) taskIdx = 0;
        String raw = msgs.get(taskIdx);
        String plainText = ChatColor.stripColor(color(raw)).replace("{player}", "Global").replace("{online_players}", String.valueOf(Bukkit.getOnlinePlayers().size()));
        sendToDiscord(plainText);
        boolean playSound = getConfig().getBoolean("Settings.EnableSound");
        Bukkit.getOnlinePlayers().forEach(player -> {
            String m = raw.replace("{player}", player.getName())
                         .replace("{online_players}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                         .replace("{max_players}", String.valueOf(Bukkit.getMaxPlayers()));
            player.sendMessage(color(m));
            if (playSound) player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
        });
        taskIdx++;
    }
    public void sendToDiscord(String content) {
        String url = getConfig().getString("Settings.DiscordWebhook");
        if (url == null || url.isEmpty() || url.contains("http") == false) return;
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                String json = "{\"content\": \"" + content + "\"}";
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = json.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                con.getResponseCode();
            } catch (Exception e) { Bukkit.getLogger().warning("Webhook Error: " + e.getMessage()); }
        });
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
}
