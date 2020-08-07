package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InvisPowerup extends AbstractPowerup {
    public InvisPowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.WHITE_STAINED_GLASS_PANE, true, 3, "Invisibility", "Invisibility for 1 minute");
    }

    @Override
    public void apply(Player target) {
        super.apply(target);

        target.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 0));
    }
}
