package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HastePowerup extends AbstractPowerup {
    public HastePowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.BEACON, false, 4, "Haste", "Haste for 5 minutes");
    }

    @Override
    public void apply(Player target) {
        super.apply(target);
        target.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 6000, 1));
    }
}
