# AutoMSG 📢

Professional category-based announcement system for Minecraft. High-performance logic with independent timers, custom audio feedback, and dedicated Discord synchronization for every message type.

## 🚀 Main Features

# Category Architecture
Announcements are organized into dedicated YAML files. Each category operates on its own independent logic, allowing for complex scheduling.
│➜ votes.yml - Vote sites and rewards.
│➜ tips.yml - Gameplay tips and tutorials.
│➜ links.yml - Social media and store links.
│➜ rules.yml - Server regulations.
│➜ motd.yml - Welcome announcements.
│➜ other.yml - Generic notifications.

# Independent Smart Timers
Every category manages its own broadcast cycle. Set rules to appear every hour while gameplay tips appear every 5 minutes. Support for (d)ays, (h)ours, (m)inutes, and (s)econds.

# Dynamic Auto-Prefix System
Each category has a unique prefix that is automatically injected into every message. Update the prefix in one line, and it applies to all announcements in that file instantly.

# Per-Category Audio Feedback
Each message group can trigger a specific sound effect. Customize the SoundName, Volume, and Pitch individually for each file.

# Cross-Version Stability
Compiled with Java 17 for maximum compatibility (1.19 to 1.21+). Our Hex-Color engine is built without external dependencies, ensuring smooth operation on Paper, Spigot, and Purpur.

# Independent Webhook Channels
Configure specific Webhook URLs for each category. Direct vote reminders to a specific channel and server rules to another automatically.

## 🛠️ Commands & Permissions
Permission: automsg.admin

│➜ /am reload
│   Reloads all category files, language translations, and resets all timers.
│➜ /am list <category>
│   Displays all messages from a specific file with their unique IDs.
│➜ /am add <category> <message>
│   Adds a new formatted message to the chosen file in real-time.
│➜ /am remove <category> <id>
│   Deletes a message and automatically reorders the remaining IDs to prevent gaps.

## ⚙️ Configuration Setup

1. Global Settings: Use config.yml to set the system language.
2. Announcements: Edit the specific .yml file for your category in the plugin folder.
3. Tutorials: Each file includes a built-in English guide for Interval, Prefix, and Sound settings.

## 📦 Supported Languages
Change the language in config.yml to instantly translate the command feedback:
│➜ en (English)
│➜ pt (Portuguese)
│➜ es (Spanish)
│➜ ru (Russian)

## 📄 Build Information
│➜ Group ID: com.comonier
│➜ Artifact ID: AutoMSG
│➜ Java Target: 17 (Supports 17 to 25)
