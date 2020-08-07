package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BuffsPowerup extends AbstractPowerup {
    public BuffsPowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.BREWING_STAND, false, -1, "Buffs", // -1 for custom cost calculation
                "Speed II for 30 seconds",
                "Strength II for 30 seconds",
                "Resistance II for 30 seconds",
                ChatColor.RED + "Halves current health");
    }

    @Override
    public void apply(Player target) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
        target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
        target.setHealthScale(target.getHealth() / 2);
        target.sendMessage(ChatColor.BOLD + "You feel like you can break the world in two...with your bare hands!");
    }
}
