package me.konsolas.uhcpowerupmenu.special;

import me.konsolas.uhcpowerupmenu.UHCPowerupMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.Objects;

public class HealthBow implements Listener {
    private final ItemStack bowItem = new ItemStack(Material.BOW);
    private final NamespacedKey bowKey;

    public HealthBow(UHCPowerupMenu plugin) {
        bowKey = new NamespacedKey(plugin, "bow");

        ItemMeta meta = Objects.requireNonNull(bowItem.getItemMeta());
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, false);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.setDisplayName(ChatColor.AQUA + "Soul Split");
        meta.setLore(Collections.singletonList(ChatColor.RED + "The user adds its HP to the target's HP, then equally shares the combined HP with the target. You only get one shot."));
        meta.getPersistentDataContainer().set(bowKey, PersistentDataType.BYTE, (byte) 2);

        bowItem.setItemMeta(meta);
    }

    public ItemStack getItemStack() {
        return bowItem.clone();
    }

    @EventHandler
    public void onShot(EntityDamageByEntityEvent event) {

        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        if(damager instanceof Arrow) {

            Arrow arrow = (Arrow) damager;
            if ((arrow.getShooter() instanceof Player) && (event.getEntity() instanceof Player) ) {
                Player shooter = (Player) arrow.getShooter();
                Player target = (Player) event.getEntity();

                if ((shooter.getEquipment() != null)) {
                    if(shooter.getEquipment().getItemInMainHand().getItemMeta() != null &&
                            shooter.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE)) {
                        shooter.setHealth(Math.min((((shooter.getHealth() + target.getHealth()) / 2)), shooter.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                        target.setHealth(Math.min((((shooter.getHealth() + target.getHealth()) / 2)), target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));

                        shooter.getInventory().remove(shooter.getEquipment().getItemInMainHand());
                        shooter.sendMessage("You shared health with " + target.getName() + "!");
                        target.sendMessage("You shared health with " + shooter.getName() + "!");

                    } else if(shooter.getEquipment().getItemInOffHand().getItemMeta() != null &&
                            shooter.getEquipment().getItemInOffHand().getItemMeta().getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE)) {
                        shooter.setHealth(Math.min((((shooter.getHealth() + target.getHealth()) / 2)), shooter.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                        target.setHealth(Math.min((((shooter.getHealth() + target.getHealth()) / 2)), target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));

                        shooter.getInventory().remove(shooter.getEquipment().getItemInOffHand());
                        shooter.sendMessage("You shared health with " + target.getName() + "!");
                        target.sendMessage("You shared health with " + shooter.getName() + "!");
                    }

                }
            }


        }

    }




}