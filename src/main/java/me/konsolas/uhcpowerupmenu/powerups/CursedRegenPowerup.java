package me.konsolas.uhcpowerupmenu.powerups;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CursedRegenPowerup extends AbstractPowerup {
    public CursedRegenPowerup(UHCPowerupMenu plugin) {
        super(plugin, Material.POTION, true, 0,
                ChatColor.MAGIC + "Cursed" + ChatColor.GOLD + "Regeneration",
                "Regenerates health but completely cripples you");
    }

    @Override
    public void apply(Player target) {
        super.apply(target);

        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 3));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 3));
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
        target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 2));

        target.setFoodLevel(0);
        target.sendMessage(ChatColor.RED + "Was it worth it?");
    }
}
