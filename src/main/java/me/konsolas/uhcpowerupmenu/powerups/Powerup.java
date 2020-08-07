package me.konsolas.uhcpowerupmenu.powerups;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Powerup {
    /**
     * Get the representation of this powerup to be shown in the inventory GUI
     * @return itemstack representing this powerup
     */
    ItemStack getIcon();

    /**
     * Activate this powerup for the given {@code target} player.
     * @param target player to activate the powerup for
     */
    void apply(Player target);
}
