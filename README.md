# AutoMSG 📢

Professional category-based announcement system for Minecraft. High-performance logic with independent timers, custom audio feedback, and dedicated Discord synchronization for every message type.

## 🚀 Main Features

# Category Architecture
Announcements are organized into dedicated YAML files. Each category operates on its own independent logic, allowing for complex scheduling.<br>
│➜ votes.yml - Vote sites and rewards.<br>
│➜ tips.yml - Gameplay tips and tutorials.<br>
│➜ links.yml - Social media and store links.<br>
│➜ rules.yml - Server regulations.<br>
│➜ motd.yml - Welcome announcements.<br>
│➜ other.yml - Generic notifications.<br>

# Independent Smart Timers
Every category manages its own broadcast cycle. Set rules to appear every hour while gameplay tips appear every 5 minutes. Support for (d)ays, (h)ours, (m)inutes, and (s)econds.<br>

# Dynamic Auto-Prefix System
Each category has a unique prefix that is automatically injected into every message. Update the prefix in one line, and it applies to all announcements in that file instantly.<br>

# Per-Category Audio Feedback
Each message group can trigger a specific sound effect. Customize the SoundName, Volume, and Pitch individually for each file.<br>

# Cross-Version Stability
Compiled with Java 17 for maximum compatibility (1.19 to 1.21+). Our Hex-Color engine is built without external dependencies, ensuring smooth operation on Paper, Spigot, and Purpur.<br>

# Independent Webhook Channels
Configure specific Webhook URLs for each category. Direct vote reminders to a specific channel and server rules to another automatically.<br>

## 🛠️ Commands & Permissions
Permission: automsg.admin<br>

│➜ /am reload<br>
│   Reloads all category files, language translations, and resets all timers.<br>
│➜ /am list <category><br>
│   Displays all messages from a specific file with their unique IDs.<br>
│➜ /am add <category> <message><br>
│   Adds a new formatted message to the chosen file in real-time.<br>
│➜ /am remove <category> <id><br>
│   Deletes a message and automatically reorders the remaining IDs to prevent gaps.<br>

## ⚙️ Configuration Setup

1. Global Settings: Use config.yml to set the system language.<br>
2. Announcements: Edit the specific .yml file for your category in the plugin folder.<br>
3. Tutorials: Each file includes a built-in English guide for Interval, Prefix, and Sound settings.<br>

## 📦 Supported Languages
Change the language in config.yml to instantly translate the command feedback:<br>
│➜ en (English)<br>
│➜ pt (Portuguese)<br>
│➜ es (Spanish)<br>
│➜ ru (Russian)<br>

## 📄 Build Information
│➜ Group ID: com.comonier<br>
│➜ Artifact ID: AutoMSG<br>
│➜ Java Target: 17 (Supports 17 to 25)<br>
