package me.chillywilly.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.chillywilly.Main;

public class HidePlayerCommand implements CommandExecutor {
    Main plugin;

    public HidePlayerCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("hideplayers").setExecutor(this);
        plugin.getCommand("unhideplayers").setExecutor(this);

        plugin.getLogger().info("Init");
    }

    private void hideAllFromPlayer(Player player) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(plugin, other);
            plugin.getLogger().info("Successfully hid all players from " + player.getName());
        }
        player.sendMessage(plugin.getMessage("commands.self.hide-all-success"));
    }

    private void showAllToPlayer(Player player) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            player.showPlayer(plugin, other);
        }
        player.sendMessage(plugin.getMessage("commands.self.show-all-success"));
    }

    private void hideAllFromPlayer(Player player, CommandSender sender) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(plugin, other);
            plugin.getLogger().info("Successfully hid all players from " + player.getName());
        }
        sender.sendMessage(plugin.getMessage("commands.other.hide-all-success"));
    }

    private void showAllToPlayer(Player player, CommandSender executor) {
        for (Player other : Bukkit.getOnlinePlayers()) {
            player.showPlayer(plugin, other);
        }
        executor.sendMessage(plugin.getMessage("commands.other.show-all-success"));
    }    


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.getLogger().info("command");
        if (args.length >= 1) { //Other
            if (!sender.hasPermission("chillycinema.hideplayer.other")) { //Check permission
                sender.sendMessage("commands.other.no-permission");
                return true;
            }

            Player player = Bukkit.getPlayer(args[0]);

            if (label.equalsIgnoreCase("hideplayers")) {
                plugin.getLogger().info("Hiding all players from " + player.getName());
                hideAllFromPlayer(player, sender);
                return true;
            }

            if (label.equalsIgnoreCase("unhideplayers") || label.equalsIgnoreCase("showplayers")) {
                plugin.getLogger().info("Showing all players to " + player.getName());
                showAllToPlayer(player, sender);
                return true;
            }

        } else { //To themselves
            if (!(sender instanceof Player)) { //Check if console is doing the command
                sender.sendMessage(plugin.getMessage("commands.self.console-use"));
                return true;
            }
            Player player = (Player) sender;

            if (!player.hasPermission("chillycinema.hideplayer")) { //Check permission
                player.sendMessage(plugin.getMessage("commands.self.no-permission"));
                return true;
            }

            if (label.equalsIgnoreCase("hideplayers")) { //Hide Players
                plugin.getLogger().info("Hiding all players from " + player.getName());
                hideAllFromPlayer(player);
                return true;
            }

            if (label.equalsIgnoreCase("unhideplayers") || label.equalsIgnoreCase("showplayers")) { //Unhide Players
                plugin.getLogger().info("Showing all players to " + player.getName());
                showAllToPlayer(player);
                return true;
            }
        }
        return false;
    }
}
