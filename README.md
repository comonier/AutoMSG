# AutoMSG 📢

A lightweight and modern announcement plugin for Minecraft 1.21.1, focused on customization and Discord integration.

## 🚀 Features

*   **Announcement Loop:** Configure periodic messages in chat.
*   **HEX/RGB Color Support:** Vibrant messages with modern gradients and colors.
*   **Discord Integration:** Automatically send announcements to a channel via Webhook.
*   **Villager Sound:** Option to play the Villager trade sound on every announcement (toggleable).
*   **Multi-Language:** Native support for English and Portuguese.
*   **ID System:** Manage announcements (list/remove) easily via commands.
*   **Native Placeholders:** `{player}`, `{online_players}`, `{max_players}`.

## 🛠️ Commands and Permissions


| Command | Description | Permission |
| :--- | :--- | :--- |
| `/am help` | Shows the help menu | `automsg.admin` |
| `/am reload` | Reloads configurations and the timer | `automsg.admin` |
| `/am list` | Lists all announcements with their IDs | `automsg.admin` |
| `/am add <msg>` | Adds a new announcement in real-time | `automsg.admin` |
| `/am remove <id>` | Removes an announcement by numeric ID | `automsg.admin` |

## ⚙️ Default Configuration

```yaml
Settings:
  Interval: 10m
  Language: en
  EnableSound: true
  DiscordWebhook: "YOUR_WEBHOOK_HERE"
