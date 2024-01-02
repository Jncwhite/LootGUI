package me.jncwhite.lootgui.listeners;

import me.jncwhite.lootgui.LootGUI;
import me.jncwhite.lootgui.utilities.Messages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PlayerDeathListener implements Listener
{
    private final LootGUI plugin;

    public PlayerDeathListener(LootGUI plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        if(event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player && event.getEntity().getKiller().hasPermission("lootgui.collect"))
        {
            Player victim = event.getEntity();
            Player killer = victim.getKiller();

            Configuration config = plugin.getConfig();

            List<ItemStack> loot = plugin.getLoot(killer);
            List<ItemStack> drops = event.getDrops();

            for (ItemStack item : drops)
            {
                ItemMeta meta = item.getItemMeta();
                if (meta.getLore() == null)
                {
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&cDropped by: " + victim.getName()));
                    meta.setLore(lore);
                }
                else meta.getLore().add(ChatColor.translateAlternateColorCodes('&', "&cDropped by: " + victim.getName()));

                item.setItemMeta(meta);
            }

            List<ItemStack> newLoot = new ArrayList<>();

            newLoot.addAll(loot);
            newLoot.addAll(drops);

            plugin.setLoot(killer, newLoot);

            killer.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix") + " " + config.getString("messages.kill-player-message")).replaceAll("%victim%", victim.getName()));
            plugin.saveConfig();
            event.getDrops().clear();
        }
    }
}
