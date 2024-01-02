package me.jncwhite.lootgui.menu;

import me.jncwhite.lootgui.utilities.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class PaginatedMenu extends Menu
{
    protected int page = 0;
    protected int maxPerPage = 36;
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility)
    {
        super(playerMenuUtility);
    }

    public void setMenuButtons()
    {
        ItemStack prev = new ItemStack(Material.DARK_OAK_BUTTON);
        ItemMeta prevMeta = prev.getItemMeta();
        prevMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lPrevious"));
        prev.setItemMeta(prevMeta);

        ItemStack next = new ItemStack(Material.DARK_OAK_BUTTON);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lNext"));
        next.setItemMeta(nextMeta);

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lClose"));
        close.setItemMeta(closeMeta);

        inventory.setItem(48, prev);
        inventory.setItem(49, close);
        inventory.setItem(50, next);
    }
}
