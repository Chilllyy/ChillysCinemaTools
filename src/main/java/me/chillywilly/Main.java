package me.chillywilly;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.chillywilly.commands.BlindnessCommand;
import me.chillywilly.commands.HidePlayerCommand;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public static String prefix;

	@Override
    public void onEnable() {
		new BlindnessCommand(this);
		new HidePlayerCommand(this);
		saveResource("messages.yml", false);
    }

	public String chat(String msg) { //translate color codes
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public String getMessage(String path) { //gets message from messages.yml adds prefix and color codes
		File file = new File(getDataFolder() + "/messages.yml");
		FileConfiguration messages = YamlConfiguration.loadConfiguration(file);
		String msg = messages.getString(path);
		String prefix = messages.getString("prefix");

		return chat(prefix + " " + msg);
	}
}