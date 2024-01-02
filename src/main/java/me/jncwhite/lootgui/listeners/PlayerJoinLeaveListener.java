package me.jncwhite.lootgui.listeners;

import me.jncwhite.lootgui.LootGUI;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinLeaveListener implements Listener
{
    private LootGUI plugin;

    public PlayerJoinLeaveListener(LootGUI plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        ConfigurationSection playerSection = plugin.getConfig().getConfigurationSection("players." + player.getUniqueId());
        List<ItemStack> loot = new ArrayList<>();

        if (playerSection != null)
        {
            for (String key : playerSection.getKeys(false))
            {
                ItemStack item = playerSection.getItemStack(key);
                if (item != null) loot.add(item);
            }
        }

        plugin.setLoot(player, loot);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Configuration config = plugin.getConfig();
        List<ItemStack> loot = plugin.getLoot(player);

        config.set("players." + player.getUniqueId(), null);

        ConfigurationSection playerSection = config.createSection("players." + player.getUniqueId());

        int count = 1;
        for (ItemStack item : loot)
        {
            playerSection.set(String.valueOf(count), item);
            count++;
        }

        plugin.saveConfig();
    }
}
