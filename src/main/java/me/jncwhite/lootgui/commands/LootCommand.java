package me.jncwhite.lootgui.commands;

import me.jncwhite.lootgui.LootGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class LootCommand implements CommandExecutor
{
    private final LootGUI plugin;

    public LootCommand(LootGUI plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Configuration config = plugin.getConfig();
        ConfigurationSection messages = config.getConfigurationSection("messages");
        String prefix = ChatColor.translateAlternateColorCodes('&', messages.getString("prefix")) + " ";

        if ((args.length == 0) || (args.length == 1 && args[0].equalsIgnoreCase("help")))
        {
            for (String line : config.getStringList("messages.help"))
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        else if (args[0].equalsIgnoreCase("clear"))
        {
            if (args.length == 1)
            {
                if (sender.hasPermission("lootgui.clear"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(prefix +
                                ChatColor.translateAlternateColorCodes('&', messages.getString("console-can-not-do-that"))
                        );
                    }
                    else
                    {
                        Player player = (Player) sender;

                        if (hasLoot(player))
                        {
                            plugin.clearLoot(player);

                            player.sendMessage(prefix +
                                    ChatColor.translateAlternateColorCodes('&', messages.getString("loot-cleared-self"))
                            );
                        }
                        else
                        {
                            player.sendMessage(prefix +
                                    ChatColor.translateAlternateColorCodes('&', messages.getString("loot-cleared-self-fail"))
                            );
                        }

                    }
                }
                else
                {
                    sender.sendMessage(prefix +
                            ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission"))
                    );
                }

            }
            else if (args.length == 2)
            {
                if (sender.hasPermission("lootgui.admin.clear"))
                {
                    Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();

                    if(target != null)
                    {
                        if (hasLoot(target))
                        {
                            plugin.clearLoot(target);

                            sender.sendMessage(prefix +
                                    ChatColor.translateAlternateColorCodes('&', messages.getString("loot-cleared-other-sender"))
                                            .replaceAll("%player%", target.getName())
                            );

                            target.sendMessage(prefix +
                                    ChatColor.translateAlternateColorCodes('&', messages.getString("loot-cleared-other-target"))
                            );
                        }
                        else
                        {
                            sender.sendMessage(prefix +
                                    ChatColor.translateAlternateColorCodes('&', messages.getString("loot-cleared-other-sender-fail"))
                                            .replaceAll("%player%", target.getName())
                            );
                        }
                    }
                    else
                    {
                        sender.sendMessage(prefix +
                                ChatColor.translateAlternateColorCodes('&', messages.getString("player-not-found"))
                                        .replaceAll("%player%", args[1])
                        );
                    }
                }
                else
                {
                    sender.sendMessage(prefix +
                            ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission"))
                    );
                }
            }
        }

        return true;
    }

    private boolean hasLoot(Player player)
    {
        if ((!plugin.getLoot(player).isEmpty()) || (plugin.getConfig().getConfigurationSection("players").isSet(String.valueOf(player.getUniqueId())) && plugin.getConfig().getConfigurationSection("players." + player.getUniqueId()).getKeys(false).size() > 0)) return true;
        else return false;
    }
}
