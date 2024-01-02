package me.jncwhite.lootgui;

import me.jncwhite.lootgui.commands.CollectCommand;
import me.jncwhite.lootgui.commands.LootCommand;
import me.jncwhite.lootgui.listeners.MenuListener;
import me.jncwhite.lootgui.listeners.PlayerDeathListener;
import me.jncwhite.lootgui.listeners.PlayerJoinLeaveListener;
import me.jncwhite.lootgui.utilities.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class LootGUI extends JavaPlugin
{
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static final HashMap<Player, List<ItemStack>> lootMap = new HashMap<>();

    @Override
    public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(this), this);

        getCommand("collect").setExecutor(new CollectCommand(this));
        getCommand("loot").setExecutor(new LootCommand(this));
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player player)
    {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(player))
        {
            return playerMenuUtilityMap.get(player);
        }
        else
        {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);

            return playerMenuUtility;
        }
    }

    public List<ItemStack> getLoot(Player player)
    {
        if (lootMap.containsKey(player)) return lootMap.get(player);
        else return new ArrayList<>();
    }

    public void setLoot(Player player, List<ItemStack> loot)
    {
        lootMap.put(player, loot);
    }

    public void clearLoot(Player player)
    {
        setLoot(player, new ArrayList<>());
        getConfig().set("players." + player.getUniqueId(), null);
    }
}
