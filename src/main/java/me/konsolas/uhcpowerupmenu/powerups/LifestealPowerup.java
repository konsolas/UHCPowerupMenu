package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LifestealPowerup extends AbstractPowerup {
    public LifestealPowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.GOLDEN_SWORD, true, 10,
                "Lifesteal", "A sword that heals part the damage given");
    }

    @Override
    public void apply(Player target) {
        super.apply(target);
        giveItems(target, plugin.getLifestealSword().getItemStack());
    }
}
