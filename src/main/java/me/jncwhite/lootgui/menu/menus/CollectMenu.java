package me.jncwhite.lootgui.menu.menus;

import me.jncwhite.lootgui.LootGUI;
import me.jncwhite.lootgui.menu.PaginatedMenu;
import me.jncwhite.lootgui.utilities.Messages;
import me.jncwhite.lootgui.utilities.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class CollectMenu extends PaginatedMenu
{
    private final LootGUI plugin;

    public CollectMenu(PlayerMenuUtility playerMenuUtility, LootGUI plugin)
    {
        super(playerMenuUtility);
        this.plugin = plugin;
    }

    @Override
    public String getMenuName()
    {
        return translateAlternateColorCodes('&', plugin.getConfig().getString("gui.collect-menu-name"));
    }

    @Override
    public int getSlots()
    {
        return plugin.getConfig().getInt("gui.collect-menu-size");
    }

    @Override
    public void handleMenu(InventoryClickEvent event)
    {
        Player player = playerMenuUtility.getOwner();
        ItemStack item = event.getCurrentItem();
        Configuration config = plugin.getConfig();
        List<ItemStack> loot = plugin.getLoot(player);

        if ((item.getType() == Material.getMaterial(config.getString("gui.previous-material"))) && (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', config.getString("gui.previous-name")))))
        {
            if (page == 0) {
                Messages.FIRST_PAGE.send(player);
            } else {
                page -= 1;
                super.open();
            }
        }

        else if ((item.getType() == Material.getMaterial(config.getString("gui.next-material"))) && (item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', config.getString("gui.next-name")))))
        {
            if (!((index + 1) >= loot.size()))
            {
                page+=1;
                super.open();
            }
            else Messages.LAST_PAGE.send(player);
        }

        else if (item.getType() == Material.getMaterial(config.getString("gui.close-material")) && (item.getItemMeta().getDisplayName()).equals(ChatColor.translateAlternateColorCodes('&', config.getString("gui.close-name"))))
        {
            event.getWhoClicked().closeInventory();
        }
        else
        {
            event.getClickedInventory().setItem(event.getSlot(), null);
            loot.remove(item);

            plugin.setLoot(player, loot);

            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            lore.remove(lore.size()-1);
            meta.setLore(lore);
            item.setItemMeta(meta);

            player.getInventory().addItem(item);
        }

        event.setCancelled(true);
    }

    @Override
    public void setMenuItems()
    {
        setMenuButtons();

        Player player = playerMenuUtility.getOwner();
        List<ItemStack> loot = plugin.getLoot(player);

        if (loot != null && !loot.isEmpty())
        {
            for (int i = 0; i < super.maxPerPage; i++)
            {
                index = super.maxPerPage * page + i;
                if (index >= loot.size()) break;
                if (loot.get(index) != null)
                {
                    ItemStack item = loot.get(index);
                    inventory.addItem(item);
                }
            }
        }
    }
}
