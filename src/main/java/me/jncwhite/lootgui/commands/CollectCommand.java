package me.jncwhite.lootgui.commands;

import me.jncwhite.lootgui.LootGUI;
import me.jncwhite.lootgui.menu.menus.CollectMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CollectCommand implements CommandExecutor
{
    private final LootGUI plugin;

    public CollectCommand(LootGUI plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Configuration config = plugin.getConfig();
        ConfigurationSection messages = config.getConfigurationSection("messages");
        String prefix = ChatColor.translateAlternateColorCodes('&', messages.getString("prefix")) + " ";

        if (!(sender instanceof Player))
        {
            sender.sendMessage(prefix +
                    ChatColor.translateAlternateColorCodes('&', messages.getString("console-can-not-do-that")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lootgui.collect"))
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', config.getString("messages.no-permission")));
        else
            new CollectMenu(LootGUI.getPlayerMenuUtility(player), plugin).open();

        return true;
    }
}
