package me.jncwhite.lootgui.utilities;

import me.jncwhite.lootgui.LootGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public enum Messages
{
    PREFIX("prefix"),
    KILL_PLAYER("kill-player-message"),
    FIRST_PAGE("already-on-first-page"),
    LAST_PAGE("already-on-last-page"),
    NO_PERMISSION("no-permission"),
    HELP("help");

    private final String path;
    private static HashMap<String, String> messages = new HashMap<>();

    Messages(final String path)
    {
        this.path = path;
    }

    public void send(CommandSender sender)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.PREFIX + " " + messages.get(path)));
    }

    static
    {
        LootGUI plugin = LootGUI.getPlugin(LootGUI.class);

        for (String key : plugin.getConfig().getConfigurationSection("messages").getKeys(true))
        {
            messages.put(key, plugin.getConfig().getString("messages." + key));
        }
    }


}
