package me.chillywilly.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.chillywilly.Main;

public class BlindnessCommand implements CommandExecutor {
    Main plugin;
    PotionEffect blindnessEffect;

    public BlindnessCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("blindness").setExecutor(this);
        plugin.getCommand("unblindness").setExecutor(this);
        this.blindnessEffect = PotionEffectType.BLINDNESS.createEffect(PotionEffect.INFINITE_DURATION, 100);
    }

    private int toggleBlindness(Player player) {
        if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) { //Player has blindness
            if (player.getPotionEffect(PotionEffectType.BLINDNESS).getDuration() == PotionEffect.INFINITE_DURATION) { //Player has blindness 100 for 999999 seconds
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                plugin.getLogger().info("Removed Blindness from " + player.getName());
                return 0;
            } else {
                plugin.getLogger().info("Attempted to remove blindness from " + player.getName() + " but they do not have infinite blindness");
                return 3;
            }
        } else {
            player.addPotionEffect(blindnessEffect);
            plugin.getLogger().info("Gave blindness to " + player.getName());
            return 1;
        }
    }

    private int removeBlindness(Player player) {
        if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            if (player.getPotionEffect(PotionEffectType.BLINDNESS).getDuration() == PotionEffect.INFINITE_DURATION) {
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                plugin.getLogger().info("Removed blindness from " + player.getName());
                return 0;
            } else {
                plugin.getLogger().info("Attempted to remove blindness from " + player.getName() + " but they do not have infinite blindness");
                return 3;
            }
        } else {
            plugin.getLogger().info("Unable to remove blindness from " + player.getName() + " target does not have the effect");
            return 1;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) { //Attempting to blind/unblind another player
            if (sender.hasPermission("chillycinema.blind.other")) {
                if (label.equalsIgnoreCase("blind") || label.equalsIgnoreCase("blindness")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    switch (toggleBlindness(target)) {
                        case 0:
                            sender.sendMessage(plugin.getMessage("commands.other.unblind-success").replace("{target}", target.getName()));
                            break;
                        case 1:
                            sender.sendMessage(plugin.getMessage("commands.other.blind-success").replace("{target}", target.getName()));
                            break;
                        case 3:
                            sender.sendMessage(plugin.getMessage("commands.other.incorrect-blindness").replace("{target}", target.getName()));
                            break;
                        default:
                            sender.sendMessage(plugin.getMessage("unknown_error"));
                            break;
                    }
                } else if (label.equalsIgnoreCase("unblindness") || label.equalsIgnoreCase("unblind")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    switch (removeBlindness(target)) {
                        case 0:
                            sender.sendMessage(plugin.getMessage("commands.other.unblind-success").replace("{target}", target.getName()));
                            break;
                        case 1:
                            sender.sendMessage(plugin.getMessage("commands.other.unblind-fail").replace("{target}", target.getName()));
                            break;
                        case 3:
                            sender.sendMessage(plugin.getMessage("commands.other.incorrect-blindness").replace("{target}", target.getName()));
                            break;
                        default:
                            sender.sendMessage(plugin.getMessage("unknown_error"));
                            break;
                    }
                }
                return false;
            } else { //No Permission
                sender.sendMessage(plugin.getMessage("commands.other.no-permission"));
                return true;
            }
        } else { //Player is doing it to themselves
            if (!(sender instanceof Player)) { //Sender is console
                sender.sendMessage(plugin.getMessage("commands.self.console-use"));
                return true;
            }

            Player player = (Player) sender;
            if (!player.hasPermission("chillycinema.blind")) { //No Permission
                sender.sendMessage(plugin.getMessage("commands.self.no-permission"));
                return true;
            }
            
            if (label.equalsIgnoreCase("blindness") || label.equalsIgnoreCase("blind")) { //"toggle" command
                    switch (toggleBlindness(player)) {
                        case 0:
                            sender.sendMessage(plugin.getMessage("commands.self.unblind-success"));
                            break;
                        case 1:
                            sender.sendMessage(plugin.getMessage("commands.self.blind-success"));
                            break;
                        case 3:
                            sender.sendMessage(plugin.getMessage("commands.self.incorrect-blindness"));
                            break;
                        default:
                            sender.sendMessage(plugin.getMessage("unknown_error"));
                            break;
                    }
                } else if (label.equalsIgnoreCase("unblindness") || label.equalsIgnoreCase("unblind")) { //unblind command
                plugin.getLogger().info("Unblind Player Test");
                switch (removeBlindness(player)) {
                    case 0:
                        sender.sendMessage(plugin.getMessage("commands.self.unblind-success"));
                        break;
                    case 1:
                        sender.sendMessage(plugin.getMessage("commands.self.unblind-fail"));
                        break;
                    case 3:
                        sender.sendMessage(plugin.getMessage("commands.self.incorrect-blindness"));
                        break;
                    default:
                        sender.sendMessage(plugin.getMessage("unknown_error"));
                        break;
                }
                }
        }
        return true;
    }
}
