package me.jncwhite.lootgui.listeners;

import me.jncwhite.lootgui.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener
{
    @EventHandler
    public void onMenuClick(InventoryClickEvent event)
    {
        if (event.getClickedInventory() != null)
        {
            Player player = (Player) event.getWhoClicked();
            InventoryHolder holder = event.getClickedInventory().getHolder();

            if (event.getView().getTopInventory().getHolder() instanceof Menu && event.isShiftClick())
            {
                event.setCancelled(true);
            }

            if (holder instanceof Menu)
            {
                event.setCancelled(true);

                if (event.getCurrentItem() == null) return;

                Menu menu = (Menu) holder;
                menu.handleMenu(event);
            }
        }
    }
}
