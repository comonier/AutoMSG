package com.comonier.automsg;
import org.bukkit.Bukkit;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class DiscordWebhook {
    private final String url;
    public DiscordWebhook(String url) { this.url = url; }
    public void send(String content) {
        if (url == null || url.isEmpty() || url.contains("http") == false) return;
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setDoOutput(true);
            String safeContent = content.replace("\"", "\\\"").replace("\n", "\\n");
            String json = "{\"content\": \"" + safeContent + "\"}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            con.getResponseCode();
            con.disconnect();
        } catch (Exception e) {
            Bukkit.getLogger().warning("[AutoMSG] Webhook Error: " + e.getMessage());
        }
    }
}
