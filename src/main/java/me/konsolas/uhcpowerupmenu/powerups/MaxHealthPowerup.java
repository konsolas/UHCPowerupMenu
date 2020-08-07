package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MaxHealthPowerup extends AbstractPowerup {
    public MaxHealthPowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.APPLE, true, 10, "Increase max health",
                "Increases max hearts by 1 (up to a maximum of 20)");
    }

    @Override
    public void apply(Player target) {
        AttributeInstance maxHealthAttr = Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH));
        if (maxHealthAttr.getValue() >= 40) {
            target.sendMessage("You already have the maximum possible health");
        } else {
            super.apply(target);
            maxHealthAttr.setBaseValue(maxHealthAttr.getBaseValue() + 2);
        }
    }
}
