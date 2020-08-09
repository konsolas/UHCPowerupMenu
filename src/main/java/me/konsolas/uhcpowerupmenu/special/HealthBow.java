package me.konsolas.uhcpowerupmenu.special;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Arrays;
import java.util.Objects;

public class HealthBow implements Listener {
    private final ItemStack bowItem = new ItemStack(Material.BOW);
    private final NamespacedKey bowKey;

    public HealthBow(UHCPowerupMenu plugin) {
        bowKey = new NamespacedKey(plugin, "healthBow");

        ItemMeta meta = Objects.requireNonNull(bowItem.getItemMeta());
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setDisplayName(ChatColor.AQUA + "Soul Split");
        meta.setLore(Arrays.asList(ChatColor.RED + "This bow adds its HP to the target's HP,",
                "then equally shares the combined HP with the target.", "You only get one shot."));
        meta.getPersistentDataContainer().set(bowKey, PersistentDataType.BYTE, (byte) 2);

        bowItem.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return bowItem.clone();
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if (bow != null && bow.getItemMeta() != null
            && bow.getItemMeta().getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE)) {
            EntityEquipment equipment = Objects.requireNonNull(event.getEntity().getEquipment());
            if (equipment.getItemInMainHand().equals(bow)) equipment.setItemInMainHand(null);
            else if (equipment.getItemInOffHand().equals(bow)) equipment.setItemInOffHand(null);

            event.getProjectile().getPersistentDataContainer().set(bowKey, PersistentDataType.BYTE, (byte) 1);
        }
    }

    @EventHandler
    public void onShot(EntityDamageByEntityEvent event) {
        if(event.getDamager().getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE)) {
            ProjectileSource source = ((Arrow) event.getDamager()).getShooter();
            if ((source instanceof LivingEntity) && (event.getEntity() instanceof LivingEntity)) {
                LivingEntity shooter = (LivingEntity) source;
                LivingEntity target = (LivingEntity) event.getEntity();

                double sharedHealth = (shooter.getHealth() + target.getHealth()) / 2.0;
                shooter.setHealth(Math.min(sharedHealth, Objects.requireNonNull(shooter.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()));
                target.setHealth(Math.min(sharedHealth, Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()));

                shooter.sendMessage("You shared health with " + target.getName() + "!");
                target.sendMessage("You shared health with " + shooter.getName() + "!");
            }
        }
    }
}