package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BowPowerup extends AbstractPowerup {
    public BowPowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.BOW, true, 3,
                "Soul Split",
                "The user adds its HP to the target's HP, then equally shares the combined HP with the target.", "You only get one shot");
    }
    @Override
    public void apply(Player target) {
        super.apply(target);
        giveItems(target, plugin.getHealthBow().getItemStack());
    }
}