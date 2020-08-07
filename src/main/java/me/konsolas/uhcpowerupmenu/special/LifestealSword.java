package me.konsolas.uhcpowerupmenu.special;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.Objects;

public class LifestealSword implements Listener {
    private final ItemStack lifestealItem = new ItemStack(Material.GOLDEN_SWORD);
    private final NamespacedKey lifestealKey;

    public LifestealSword(UHCPowerupMenu plugin) {
        lifestealKey = new NamespacedKey(plugin, "lifesteal");

        ItemMeta meta = Objects.requireNonNull(lifestealItem.getItemMeta());
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.setDisplayName(ChatColor.RED + "Lifestealer");
        meta.setLore(Collections.singletonList(ChatColor.RED + "Heals 50% of damage given"));
        meta.getPersistentDataContainer().set(lifestealKey, PersistentDataType.BYTE, (byte) 1);
        lifestealItem.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return lifestealItem.clone();
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK
                || !(event.getDamager() instanceof LivingEntity)) return;
        LivingEntity damager = (LivingEntity) event.getDamager();

        if (damager.getEquipment() != null) {
            ItemStack weapon = damager.getEquipment().getItemInMainHand();
            if (weapon.getItemMeta() != null
                    && weapon.getItemMeta().getPersistentDataContainer().has(lifestealKey, PersistentDataType.BYTE)) {
                damager.setHealth(Math.min(damager.getHealth() + event.getFinalDamage() / 2,
                        Objects.requireNonNull(damager.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()));
            }
        }
    }
}
