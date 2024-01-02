package me.jncwhite.lootgui.utilities;

import org.bukkit.entity.Player;

public class PlayerMenuUtility
{
    private Player owner;
    private Player playerToViewLootOf;

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public Player getPlayerToViewLootOf()
    {
        return playerToViewLootOf;
    }

    public void setPlayerToViewLootOf(Player playerToViewLootOf)
    {
        this.playerToViewLootOf = playerToViewLootOf;
    }

    public PlayerMenuUtility(Player owner)
    {
        this.owner = owner;
    }
}
